package com.unknown.navevent.bLogic;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.MapsEvent;
import com.unknown.navevent.bLogic.events.ServiceInterfaceEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

//Enables interaction with the BeaconService & MapService (including startup, etc.)
class ServiceInterface {
	private static final String TAG = "ServiceInterface";

	private static int beaconServiceBindCount = 0;//Handles service-access from multiple components(e. g. Activities)
	private static int mapServiceBindCount = 0;//Handles service-access from multiple components(e. g. Activities)

	private Context mContext;


	//Beacon data
	public enum BeaconAvailabilityState {
		starting,//Start searching for beacons
		found,//One or more beacons found
		nothingFound//No beacons found
	}
	public BeaconAvailabilityState beaconAvailabilityState = BeaconAvailabilityState.starting;

	//Map data
	public MapIR currentMap;//Current loaded map
	public List<MapIR> availableLocalMaps;//Already downloaded maps


	void onCreate(Context context) {
		mContext = context;
		EventBus.getDefault().register(this);
		beaconServiceBindCount++;
		if( beaconServiceBindCount == 1) mContext.startService(new Intent(mContext, BeaconService.class));
		mapServiceBindCount++;
		if( mapServiceBindCount == 1) mContext.startService(new Intent(mContext, MapService.class));


		//Register for broadcast on bluetooth-events
		mContext.registerReceiver(btReceive, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

	}
	void onDestroy() {
		beaconServiceBindCount--;
		if( beaconServiceBindCount == 0 ) EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_STOP_SELF));//todo: check if interrupts other activities
		mapServiceBindCount--;
		if( mapServiceBindCount == 0 ) EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_STOP_SELF));//todo: check if interrupts other activities

		EventBus.getDefault().unregister(this);
		mContext.unregisterReceiver(btReceive);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceInterfaceEvent event) {
		if (event.message == ServiceInterfaceEvent.EVENT_BEACON_SERVICE_STARTED) {
			Log.d(TAG, "onMessageEvent(ServiceInterface): EVENT_BEACON_SERVICE_STARTED");

			EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
		}
		else if (event.message == ServiceInterfaceEvent.EVENT_MAP_SERVICE_STARTED) {
			Log.d(TAG, "onMessageEvent(ServiceInterface): EVENT_MAP_SERVICE_STARTED");

			EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_RELOAD_LOCAL_MAPS));
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(MapsEvent event) {
		if (event.message == MapsEvent.EVENT_LOCAL_RELOADED) {
			Log.d(TAG, "onMessageEvent(Maps): EVENT_LOCAL_RELOADED");

			availableLocalMaps = event.maps;

			if( beaconAvailabilityState != BeaconAvailabilityState.starting &&
					!availableLocalMaps.contains(currentMap) ) {//Current map removed and not in staring mode
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_CURRENT_MAP_UNLOADED));
			}
			if( event.maps.isEmpty() ) {
				//EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_NO_LOCAL_MAPS_AVAILABLE));
			}
		}
	}


	//Receiver checks if bluetooth was de-/activated
	private final BroadcastReceiver btReceive = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
				switch(state) {
					case BluetoothAdapter.STATE_OFF:
						Toast.makeText(mContext, R.string.bluetoothTurnedOffWarning, Toast.LENGTH_LONG).show();
						break;
					case BluetoothAdapter.STATE_TURNING_OFF:
						EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_STOP_LISTENING));
						break;
					case BluetoothAdapter.STATE_ON:
						Log.d("BTooth-change-BR", "onReceive: BLUETOOTH_ACTIVATED");
						EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
						break;
					case BluetoothAdapter.STATE_TURNING_ON:
						break;
				}

			}
		}
	};
}
