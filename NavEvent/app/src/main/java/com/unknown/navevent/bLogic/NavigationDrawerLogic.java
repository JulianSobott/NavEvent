package com.unknown.navevent.bLogic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerLogicInterface;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class NavigationDrawerLogic implements NavigationDrawerLogicInterface {
	private static final String TAG = "NavigationDrawerLogic";


	private NavigationDrawerUI mResponder = null;
	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public NavigationDrawerLogic(NavigationDrawerUI responder) {
		mResponder = responder;
	}


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate() {
		EventBus.getDefault().register(this);


	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public List<String> getSpecialBeacons() {
		return null;
	}

	@Override
	public void findSpecialBeacon(String name) {

	}

	@Override
	public void findAllSpecialBeacon(String name) {

	}

	@Override
	public void searchFor(String name) {

	}

	@Override
	public List<BeaconData> getImportantPlaces() {
		return null;
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
		/*if( event.message == ServiceToActivityEvent.EVENT_NEW_MAP_LOADED) { todo change
			Log.i(TAG, "onMessageEvent: EVENT_NEW_MAP_LOADED");

			mResponder.updateMap(serviceInterface.currentMap);
		}
		else if( event.message == ServiceToActivityEvent.EVENT_MAP_DOWNLOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOADED");

			Toast.makeText(serviceInterface.mContext, "Map '"+serviceInterface.lastDownloadedMap.getName()+"' downloaded.", Toast.LENGTH_SHORT).show();
			//todo load map
		}
		else if( event.message == ServiceToActivityEvent.EVENT_MAP_DOWNLOAD_FAILED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOAD_FAILED");
			mResponder.downloadFailed("Failed to download map!");//todo change string
		}*/
	}
}
