package com.unknown.navevent.bLogic;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.MapsEvent;
import com.unknown.navevent.bLogic.events.ServiceInterfaceEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Handles maps (including down-/uploading)
public class MapService extends Service {
	private static final String TAG = "BeaconService";

	@Override
	public void onCreate() {
		super.onCreate();

		EventBus.getDefault().register(this);

		EventBus.getDefault().post(new ServiceInterfaceEvent(ServiceInterfaceEvent.EVENT_MAP_SERVICE_STARTED));


	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}


	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onMessageEvent(MapServiceEvent event) {
		if( event.message == MapServiceEvent.EVENT_STOP_SELF) {
			Log.d(TAG, "onMessageEvent: EVENT_STOP_SELF");
			stopSelf();
		}
		else if( event.message == MapServiceEvent.EVENT_RELOAD_LOCAL_MAPS) {
			Log.d(TAG, "onMessageEvent: EVENT_RELOAD_LOCAL_MAPS");
			reloadLocalMaps();
		}
	}

	//Reloads downloaded maps on this device.
	void reloadLocalMaps() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				//generate debug map todo: implement real loading
				List<MapIR> maps = new ArrayList<>();
				MapIR newMap = new MapIR();

				newMap.name = "Debugging map";
				newMap.id = 1;
				newMap.description = "This is a debugging map with: \n* Some information\n* In Markdown";

				newMap.imagePath = "debugImage.png";
				newMap.image = BitmapFactory.decodeResource(getResources(), R.drawable.default_map);

				newMap.majorID = 1;
				{
					newMap.beacons.put(1, new MapBeaconIR("Beacon1", 1, 1, 10, 10, "this is beacon 1"));
					newMap.beacons.put(2, new MapBeaconIR("Beacon2", 2, 1, 20, 15, "this is beacon 2"));
				}
				{
					newMap.beaconMap.put(1, 1);
					newMap.beaconMap.put(2, 2);
				}
				{
					newMap.ordinaryPlaces.put("One", new ArrayList<Integer>());
					newMap.ordinaryPlaces.get("One").add(1);
				}
				{
					newMap.specialPlaces.put("Two", new ArrayList<Integer>());
					newMap.specialPlaces.get("Two").add(1);
				}
				maps.add(newMap);

				EventBus.getDefault().post(new MapsEvent(MapsEvent.EVENT_LOCAL_RELOADED, maps));
			}
		}).start();
	}
}
