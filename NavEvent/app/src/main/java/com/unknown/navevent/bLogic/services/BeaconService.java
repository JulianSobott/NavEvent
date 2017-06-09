package com.unknown.navevent.bLogic.services;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.litesuits.bluetooth.LiteBleGattCallback;
import com.litesuits.bluetooth.LiteBluetooth;
import com.litesuits.bluetooth.conn.BleCallback;
import com.litesuits.bluetooth.conn.BleCharactCallback;
import com.litesuits.bluetooth.conn.LiteBleConnector;
import com.litesuits.bluetooth.exception.BleException;
import com.litesuits.bluetooth.exception.hanlder.DefaultBleExceptionHandler;
import com.litesuits.bluetooth.log.BleLog;
import com.litesuits.bluetooth.utils.BluetoothUtil;
import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.BeaconUpdateEvent;
import com.unknown.navevent.bLogic.events.ServiceInterfaceEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BeaconService extends Service implements BeaconConsumer, RangeNotifier {
	private static final String TAG = "BeaconService";


	/////////////////////////////////////////////////////////
	// Data
	/////////////////////////////////////////////////////////

	//Beacon receiver
	private BeaconManager internBeaconMgr;

	//Beacon writing
	private LiteBluetooth liteBluetooth;
	private DefaultBleExceptionHandler bleExceptionHandler;
	String UUID_SERVICE_PROXIMITY = "F001A0A0-7509-4C31-A905-1A27D39C003C";//UUID to configure the beacon proximity data
	String UUID_SERVICE_MAJOR = "F001A0A2-7509-4C31-A905-1A27D39C003C";//UUID to configure the beacon major id
	String UUID_SERVICE_MINOR = "F001A0A3-7509-4C31-A905-1A27D39C003C";//UUID to configure the beacon minor id

	//State data
	private boolean isBluetoothSupported = true;
	private boolean isBluetoothActivated = false;
	private boolean hasBeaconPermissions = false;
	private boolean isBeaconListening = true;//beacon-receiver is deactivated

	//Current beacons in region
	public List<BeaconIR> beacons = new ArrayList<>();
	private BeaconIR nearestBeacon;


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate() {
		super.onCreate();

		EventBus.getDefault().register(this);

		EventBus.getDefault().post(new ServiceInterfaceEvent(ServiceInterfaceEvent.EVENT_BEACON_SERVICE_STARTED));

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		stopBeaconManager();
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.ASYNC)
	public void onMessageEvent(final BeaconServiceEvent event) {
		if (event.message == BeaconServiceEvent.EVENT_START_LISTENING) {
			Log.i(TAG, "onMessageEvent: START_LISTENING");
			startBeaconManager();
		} else if (event.message == BeaconServiceEvent.EVENT_STOP_LISTENING) {
			Log.i(TAG, "onMessageEvent: START_LISTENING");
			stopBeaconManager();
		} else if (event.message == BeaconServiceEvent.EVENT_STOP_SELF) {
			stopSelf();
		} else if (event.message == BeaconServiceEvent.EVENT_CONFIG_DEVICE) {
			if (nearestBeacon != null) {
				final String mac = nearestBeacon.mac;

				if (liteBluetooth == null) {
					liteBluetooth = new LiteBluetooth(this);
				}
				bleExceptionHandler = new DefaultBleExceptionHandler(this);

				liteBluetooth.scanAndConnect(mac, false, new LiteBleGattCallback() {

					@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
					@Override
					public void onConnectSuccess(BluetoothGatt gatt, int status) {
						// discover services !
						gatt.discoverServices();
					}

					@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
					@Override
					public void onServicesDiscovered(BluetoothGatt gatt, int status) {
						BluetoothUtil.printServices(gatt);
						//Toast.makeText(BeaconService.this, mac + " Services discovered SUCCESS", Toast.LENGTH_SHORT).show();

						//Convert the id to Little Endian
						ByteBuffer bb = ByteBuffer.allocate(4);
						bb.order(ByteOrder.LITTLE_ENDIAN);
						bb.putInt(event.writeBeaconData.writeMajor);
						bb.flip();

						Looper.prepare();//todo check this method

						//Write data
						LiteBleConnector connector = liteBluetooth.newBleConnector();
						connector.withUUIDString(UUID_SERVICE_PROXIMITY, UUID_SERVICE_MAJOR, null)
								.writeCharacteristic(new byte[]{bb.get(), bb.get()}, new BleCharactCallback() {
									@Override
									public void onSuccess(BluetoothGattCharacteristic characteristic) {
										BleLog.i(TAG, "Write Success, DATA: " + Arrays.toString(characteristic.getValue()));

										/*todo
										*  - notify user that has to restart the beacon (or figure out how to reboot the beacon).
										*  - Add loading-animation while is setting up the beacon
										*  - Retry configuration multiple times after failure
										*  - disconnect from beacon
										*  - Add information about needed android version for the user
										*  - notify user if configuration failed
										*/

										/*Looper.prepare();

										LiteBleConnector connector = liteBluetooth.newBleConnector();
										connector.withUUIDString(UUID_SERVICE_PROXIMITY, UUID_SERVICE_MAJOR, null)
												.writeCharacteristic(new byte[]{0x00}, new BleCharactCallback() {
													@Override
													public void onSuccess(BluetoothGattCharacteristic characteristic) {
														BleLog.i(TAG, "Write Success, DATA: " + Arrays.toString(characteristic.getValue()));
													}

													@Override
													public void onFailure(BleException e) {
														BleLog.i(TAG, "Write failure: " + e);
														bleExceptionHandler.handleException(e);
													}
												});*/
									}

									@Override
									public void onFailure(BleException e) {
										BleLog.i(TAG, "Write failure: " + e);
										bleExceptionHandler.handleException(e);
									}
								});
					}

					@Override
					public void onConnectFailure(BleException e) {
						Toast.makeText(BeaconService.this, mac + " Services discovered FAILURE (" + e.getDescription() + ")", Toast.LENGTH_SHORT).show();
					}
				});


			}
		}
	}


	/////////////////////////////////////////////////////////
	// Beacon methods
	/////////////////////////////////////////////////////////

	private void startBeaconManager() {
		verifyBluetooth();
		if (isBluetoothActivated) {
			if (!hasBeaconPermissions) {
				handleBeaconPermissions();
				if (hasBeaconPermissions) startListening();
			} else {
				startListening();
			}
		}
	}

	private void stopBeaconManager() {
		if (isBeaconListening) {
			stopListening();
		}
	}

	//Returns true if bluetooth is enabled
	private void verifyBluetooth() {
		try {
			if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_BLE_NOT_SUPPORTED));
			}

			if (org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this).checkAvailability()) {
				isBluetoothSupported = true;
				isBluetoothActivated = true;
			} else {
				isBluetoothSupported = true;
				isBluetoothActivated = false;
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_BLUETOOTH_DEACTIVATED));
			}
		} catch (RuntimeException e) {
			isBluetoothSupported = false;
			isBluetoothActivated = false;
			EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_BLUETOOTH_NOT_SUPPORTED));
		}

	}

	//Check if location access is granted
	private void handleBeaconPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			//Android M+ permission check
			if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //Permission denied
				hasBeaconPermissions = false;
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_ASK_PERMISSION));
			} else hasBeaconPermissions = true;//Permission granted
		} else hasBeaconPermissions = true;//Permission should be granted if android version < 6
	}

	private void startListening() {
		internBeaconMgr = BeaconManager.getInstanceForApplication(this);
		internBeaconMgr.getBeaconParsers().add(new BeaconParser().
				setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));//Specific layout for iBeacons
		internBeaconMgr.bind(this);
		isBeaconListening = true;

		EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_LISTENER_STARTED));
	}

	private void stopListening() {
		if (internBeaconMgr != null)
			internBeaconMgr.unbind(this);
		isBeaconListening = false;
	}

	//Callback function for BeaconManager on start
	public void onBeaconServiceConnect() {
		internBeaconMgr.addRangeNotifier(this);

		try {
			internBeaconMgr.startRangingBeaconsInRegion(new Region(getString(R.string.beaconRegionUID), null, null, null));
		} catch (RemoteException e) {
			Log.e(TAG, "onBeaconServiceConnect: startRangingBeaconsInRegion failed");
		}
	}

	//Callback function for BeaconManager to update beacons
	@Override
	public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
		double nearest = -1;

		this.beacons.clear();
		for (Beacon beacon : beacons) {
			BeaconIR b = new BeaconIR();
			b.majorID = beacon.getId2().toInt();
			b.minorID = beacon.getId3().toInt();
			b.distance = beacon.getDistance();
			b.mac = beacon.getBluetoothAddress();
			this.beacons.add(b);

			if (nearest == -1 || b.distance < nearest) {
				nearest = b.distance;
				nearestBeacon = b;
			}
		}

		EventBus.getDefault().post(new BeaconUpdateEvent(BeaconUpdateEvent.EVENT_BEACON_UPDATE, this.beacons));
	}

}
