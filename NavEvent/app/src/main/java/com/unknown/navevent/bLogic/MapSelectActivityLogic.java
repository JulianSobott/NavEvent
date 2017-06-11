package com.unknown.navevent.bLogic;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.bLogic.services.MapIR;
import com.unknown.navevent.bLogic.services.MapService;
import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;
import com.unknown.navevent.interfaces.MapSelectActivityLogicInterface;
import com.unknown.navevent.interfaces.MapSelectActivityUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MapSelectActivityLogic implements MapSelectActivityLogicInterface {
	private static final String TAG = "MapSelectActivityLogic";


	private MapSelectActivityUI mResponder = null;

	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public MapSelectActivityLogic(MapSelectActivityUI responder) {
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
		EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_GET_ALL_LOCAL_MAPS));
	}

	@Override
	public void onStop() {
		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public void findOnlineMap(String name) {
		EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_FIND_ONLINE_MAP_BY_QUERY, name));
	}

	@Override
	public void downloadMap(int mapID) {
		EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_DOWNLOAD_MAP, mapID));
	}

	@Override
	public void setActiveMap(int mapID) {
		EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_LOAD_MAP_LOCAL, mapID));
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
		if (event.message == ServiceToActivityEvent.EVENT_NEW_MAP_LOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_NEW_MAP_LOADED");
			mResponder.switchToMainActivity();
		} else if (event.message == ServiceToActivityEvent.EVENT_MAP_DOWNLOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOADED");

			mResponder.downloadFinished(serviceInterface.lastDownloadedMap);
			Toast.makeText(serviceInterface.mContext, "Map '" + serviceInterface.lastDownloadedMap.getName() + "' downloaded.", Toast.LENGTH_SHORT).show();
		} else if (event.message == ServiceToActivityEvent.EVENT_MAP_DOWNLOAD_FAILED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOAD_FAILED");
			mResponder.downloadFailed(event.additionalInfo);
		} else if (event.message == ServiceToActivityEvent.EVENT_FOUND_ONLINE_MAPS) {
			Log.i(TAG, "onMessageEvent: EVENT_FOUND_ONLINE_MAPS");

			List<MapData> newList = new ArrayList<>();
			for (MapIR map : serviceInterface.foundOnlineMaps) {
				newList.add(map);
			}
			mResponder.onlineMapQueryRespond(newList);
		} else if (event.message == ServiceToActivityEvent.EVENT_FOUND_CORRESPONDING_MAP) {
			Log.i(TAG, "onMessageEvent: EVENT_FOUND_CORRESPONDING_MAP");
			mResponder.foundLocalMap(serviceInterface.availableNearbyMap);
		} else if (event.message == ServiceToActivityEvent.EVENT_AVAIL_LOCAL_MAPS_UPDATED) {
			Log.i(TAG, "onMessageEvent: EVENT_AVAIL_LOCAL_MAPS_UPDATED");

			//Downcast list
			List<MapData> tmpList = new ArrayList<>();
			for (int i = 0; i < serviceInterface.availableLocalMaps.size(); i++)
				tmpList.add(serviceInterface.availableLocalMaps.get(i));
			mResponder.localMapsLoaded(tmpList);
		}
	}
}
