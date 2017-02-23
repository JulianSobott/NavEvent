package com.unknown.navevent.bLogic;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Handles receiving of beacon signals and bluetooth
public class BeaconService extends Service implements BeaconConsumer, RangeNotifier {
	private static final String TAG = "BeaconService";

	//BeaconManagement
	private BeaconManager internBeaconMgr;

	private boolean isBluetoothSupported = true;
	private boolean isBluetoothActivated = false;
	private boolean hasBeaconPermissions = false;
	private boolean isBeaconListening = true;//beacon-receiver is deactivated



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
		EventBus.getDefault().unregister(this);
		stopBeaconManager();
		super.onDestroy();
	}


	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onMessageEvent(BeaconServiceEvent event) {
		if( event.message == BeaconServiceEvent.EVENT_START_LISTENING) {
			Log.d(TAG, "onMessageEvent: EVENT_START_LISTENING");
			startBeaconManager();
		}
		else if( event.message == BeaconServiceEvent.EVENT_STOP_LISTENING) {
			Log.d(TAG, "onMessageEvent: EVENT_STOP_LISTENING");
			stopBeaconManager();
		}
		else if( event.message == BeaconServiceEvent.EVENT_STOP_SELF) {
			stopSelf();
		}
	}

	private void startBeaconManager() {
		verifyBluetooth();
		if( isBluetoothActivated ) {
			if( !hasBeaconPermissions ) {
				handleBeaconPermissions();
				if( hasBeaconPermissions ) startListening();
			}
			else {
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_LISTENER_STARTED));
				startListening();
			}
		}
	}
	private void stopBeaconManager() {
		if( isBeaconListening ) {
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
			}
			else
			{
				isBluetoothSupported = true;
				isBluetoothActivated = false;
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_BLUETOOTH_DEACTIVATED));
			}
		}
		catch (RuntimeException e) {
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
			}
			else hasBeaconPermissions = true;//Permission granted
		}
		else hasBeaconPermissions = true;//Permission should be granted if android version < 6
	}

	private void startListening()  {
		internBeaconMgr = BeaconManager.getInstanceForApplication(this);
		internBeaconMgr.getBeaconParsers().add(new BeaconParser().
				setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));//Specific layout for iBeacons
		internBeaconMgr.bind(this);
		isBeaconListening = true;
	}
	private void stopListening() {
		if( internBeaconMgr != null )
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

		List<BeaconIR> updatedBeacons = new ArrayList<>();//Copy whole list to avoid concurrency problems
		for (Beacon beacon : beacons) {
			BeaconIR b = new BeaconIR();
			b.majorID = beacon.getId2().toInt();
			b.minorID = beacon.getId3().toInt();
			b.distance = beacon.getDistance();
			updatedBeacons.add(b);
		}

		EventBus.getDefault().post(new BeaconUpdateEvent(BeaconUpdateEvent.EVENT_BEACON_UPDATE, updatedBeacons));
	}

}
