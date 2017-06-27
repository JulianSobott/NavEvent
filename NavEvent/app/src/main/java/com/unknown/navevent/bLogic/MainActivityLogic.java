package com.unknown.navevent.bLogic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.bLogic.services.MapIR;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

//Background-logic of the MainActivity
public class MainActivityLogic implements MainActivityLogicInterface {
	private static final String TAG = "MainActivityLogic";

	private MainActivityUI mResponder = null;

	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public MainActivityLogic(MainActivityUI responder) {
		mResponder = responder;
	}


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate(Context context) {
		serviceInterface.onCreate(context);
	}

	@Override
	public void onDestroy() {
		serviceInterface.onDestroy();
	}

	@Override
	public void onStart() {
		EventBus.getDefault().register(this);

		if (serviceInterface.currentMap != null)
			mResponder.updateMap(serviceInterface.currentMap);
	}

	@Override
	public void onStop() {
		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public void retryBeaconConnection() {
		EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.Type.EVENT_START_LISTENING));
	}

	@Override
	public void getMap(int id) {
		boolean contains = false;
		if (serviceInterface.availableLocalMaps != null) {
			for (MapIR map : serviceInterface.availableLocalMaps) {
				if (map.getID() == id) {
					contains = true;
					break;
				}
			}
		}

		if (contains) {//load offline
			EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.Type.EVENT_LOAD_MAP_LOCAL, id));
			serviceInterface.mapAvailabilityState = ServiceInterface.MapAvailabilityState.loading;
		} else {//download map
			EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.Type.EVENT_DOWNLOAD_MAP, id));
		}
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
		if (event.message == ServiceToActivityEvent.Type.EVENT_BEACON_LISTENER_STARTED) {
			Log.i(TAG, "onMessageEvent: EVENT_BEACON_LISTENER_STARTED");
			mResponder.initCompleted();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_BLUETOOTH_DEACTIVATED) {
			Log.i(TAG, "onMessageEvent: EVENT_BLUETOOTH_DEACTIVATED");

			mResponder.bluetoothDeactivated();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_BLUETOOTH_NOT_SUPPORTED) {
			Log.i(TAG, "onMessageEvent: EVENT_BLUETOOTH_NOT_SUPPORTED");

			mResponder.notSupported(event.additionalInfo);
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_BLE_NOT_SUPPORTED) {
			Log.i(TAG, "onMessageEvent: EVENT_BLE_NOT_SUPPORTED");

			mResponder.notSupported("");
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_ASK_PERMISSION) {
			Log.i(TAG, "onMessageEvent: EVENT_ASK_PERMISSION");

			mResponder.askForPermissions();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_NEW_MAP_LOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_NEW_MAP_LOADED");


			mResponder.updateMap(serviceInterface.currentMap);
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_CURRENT_MAP_UNLOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_CURRENT_MAP_UNLOADED");

			mResponder.switchToMapSelectActivity();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_NO_LOCAL_MAPS_AVAILABLE) {
			Log.i(TAG, "onMessageEvent: EVENT_NO_LOCAL_MAPS_AVAILABLE");

			mResponder.switchToMapSelectActivity();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_MAP_DOWNLOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOADED");

			Toast.makeText(serviceInterface.mContext, "Map '" + serviceInterface.lastDownloadedMap.getName() + "' downloaded.", Toast.LENGTH_SHORT).show();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_FOUND_CORRESPONDING_MAP) {
			Log.i(TAG, "onMessageEvent: EVENT_FOUND_CORRESPONDING_MAP");
			mResponder.foundLocalMap(serviceInterface.availableNearbyMap);
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_BEACON_UPDATE) {
			//Log.i(TAG, "onMessageEvent: EVENT_BEACON_UPDATE");
			mResponder.updateBeaconPosition(serviceInterface.nearestBeaconID);
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_NO_BEACON_FOUND) {
			Log.i(TAG, "onMessageEvent: EVENT_NO_BEACON_FOUND");
			mResponder.updateBeaconPosition(0);
			Toast.makeText(serviceInterface.mContext, "NO_BEACON", Toast.LENGTH_SHORT).show();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_NO_CORRESPONDING_MAPS_AVAILABLE) {
			Log.i(TAG, "onMessageEvent: EVENT_NO_CORRESPONDING_MAPS_AVAILABLE");
			mResponder.switchToMapSelectActivity();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_MARK_BEACONS) {
			Log.i(TAG, "onMessageEvent: EVENT_NO_CORRESPONDING_MAPS_AVAILABLE");
			mResponder.markBeacons(serviceInterface.markableBeacons);
		}
	}
}
