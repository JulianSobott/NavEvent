package com.unknown.navevent.bLogic;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.BeaconUpdateEvent;
import com.unknown.navevent.bLogic.events.LogicIfcBaseEvent;
import com.unknown.navevent.bLogic.events.MainActivityLogicEvent;

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

public class BeaconService extends Service implements BeaconConsumer, RangeNotifier {

	private Thread mStartThread = null;


	//BeaconManagement
	private BeaconManager internBeaconMgr;

	private boolean isBluetoothSupported = true;
	private boolean isBluetoothActivated = false;
	private boolean hasBeaconPermissions = false;
	private boolean isBeaconListening = true;//beacon-receiver is deactivated

	public List<BeaconIR> beacons = new ArrayList<BeaconIR>();


	@Override
	public void onCreate() {
		super.onCreate();

		EventBus.getDefault().register(this);

		EventBus.getDefault().post(new LogicIfcBaseEvent(LogicIfcBaseEvent.EVENT_SERVICE_STARTED));
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
			Log.d("BeaconService", "onMessageEvent: START_LISTENING");
			startBeaconManager();
		}
		else if( event.message == BeaconServiceEvent.EVENT_STOP_LISTENING) {
			Log.d("BeaconService", "onMessageEvent: START_LISTENING");
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
	public void verifyBluetooth() {
		try {
			// Determine whether BLE is supported on the device. Then
			// you can selectively disable BLE-related features. todo
			if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
				EventBus.getDefault().post(new MainActivityLogicEvent(MainActivityLogicEvent.EVENT_BLE_NOT_SUPPORTED));
			}

			if (org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this).checkAvailability()) {
				isBluetoothSupported = true;
				isBluetoothActivated = true;
			}
			else
			{
				isBluetoothSupported = true;
				isBluetoothActivated = false;
				EventBus.getDefault().post(new MainActivityLogicEvent(MainActivityLogicEvent.EVENT_BLUETOOTH_DEACTIVATED));
			}
		}
		catch (RuntimeException e) {
			isBluetoothSupported = false;
			isBluetoothActivated = false;
			EventBus.getDefault().post(new MainActivityLogicEvent(MainActivityLogicEvent.EVENT_BLUETOOTH_NOT_SUPPORTED));
		}

	}
	public void handleBeaconPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			//Android 6(M) permission check
			if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //Permission denied
				hasBeaconPermissions = false;
				EventBus.getDefault().post(new MainActivityLogicEvent(MainActivityLogicEvent.EVENT_ASK_PERMISSION));
			}
			else hasBeaconPermissions = true;//Permission granted
		}
		else hasBeaconPermissions = true;//Permissions should be granted if android version < 6
	}

	public void startListening()  {
		internBeaconMgr = BeaconManager.getInstanceForApplication(this);
		internBeaconMgr.getBeaconParsers().add(new BeaconParser().
				setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));//Specific layout for iBeacons
		internBeaconMgr.bind(this);
		isBeaconListening = true;
	}
	public void stopListening() {
		if( internBeaconMgr != null )
			internBeaconMgr.unbind(this);
		isBeaconListening = false;
	}
	public void onBeaconServiceConnect() {
		internBeaconMgr.addRangeNotifier(this);

		try {
			internBeaconMgr.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
		} catch (RemoteException e) {   }
	}

	@Override
	public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
		this.beacons.clear();
		for (Beacon beacon : beacons) {
			BeaconIR b = new BeaconIR();
			b.majorID = beacon.getId2().toInt();
			b.minorID = beacon.getId3().toInt();
			b.distance = beacon.getDistance();
			this.beacons.add(b);
		}

		EventBus.getDefault().post(new BeaconUpdateEvent(BeaconUpdateEvent.EVENT_BEACON_UPDATE, this.beacons));
	}

}
