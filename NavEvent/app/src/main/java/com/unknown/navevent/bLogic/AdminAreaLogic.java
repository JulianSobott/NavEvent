package com.unknown.navevent.bLogic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.bLogic.services.MapIR;
import com.unknown.navevent.interfaces.AdminAreaLogicInterface;
import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.MapData;
import com.unknown.navevent.interfaces.MapSelectActivityUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class AdminAreaLogic implements AdminAreaLogicInterface {
	private static final String TAG = "AdminAreaLogic";


	private AdminAreaUI mResponder = null;
	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public AdminAreaLogic(AdminAreaUI responder) {
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
	}

	@Override
	public void onStop() {
		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public void loadMap(int mapID) {
		EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.Type.EVENT_DOWNLOAD_MAP, mapID));
	}

	@Override
	public void configureBeacon(int beaconID) {
		EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.Type.EVENT_CONFIG_DEVICE, new BeaconServiceEvent.WriteBeaconData(serviceInterface.currentMap.getMajorID(), serviceInterface.currentMap.getBeaconsIR().get(beaconID).minorID)));
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
		if (event.message == ServiceToActivityEvent.Type.EVENT_NEW_MAP_LOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_NEW_MAP_LOADED");

			mResponder.updateMap(serviceInterface.currentMap);
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_MAP_DOWNLOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOADED");

			mResponder.updateMap(serviceInterface.currentMap);
			Toast.makeText(serviceInterface.mContext, "Map '" + serviceInterface.lastDownloadedMap.getName() + "' downloaded.", Toast.LENGTH_SHORT).show();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_MAP_DOWNLOAD_FAILED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOAD_FAILED");
			mResponder.downloadFailed(event.additionalInfo);
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_BEACON_CONFIG_SUCCESSFUL) {
			Log.i(TAG, "onMessageEvent: EVENT_BEACON_CONFIG_SUCCESSFUL");
			mResponder.beaconSuccessfullyConfigured();
		} else if (event.message == ServiceToActivityEvent.Type.EVENT_BEACON_CONFIG_FAILED) {
			Log.i(TAG, "onMessageEvent: EVENT_BEACON_CONFIG_FAILED");
			mResponder.beaconConfigurationFailed(event.additionalInfo);
		}
	}
}
