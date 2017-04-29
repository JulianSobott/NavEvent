package com.unknown.navevent.bLogic;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.MapUpdateEvent;
import com.unknown.navevent.bLogic.events.ServiceInterfaceEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.bLogic.services.BeaconService;
import com.unknown.navevent.bLogic.services.MapBeaconIR;
import com.unknown.navevent.bLogic.services.MapIR;
import com.unknown.navevent.bLogic.services.MapService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

//Enables interaction with the BeaconService & MapService (including startup, etc.)
public class ServiceInterface {
	private static final String TAG = "ServiceInterface";

	private static int beaconServiceBindCount = 0;//Handles service-access from multiple components(e. g. Activities)
	private static int mapServiceBindCount = 0;//Handles service-access from multiple components(e. g. Activities)

	
	//Beacon data
	enum BeaconAvailabilityState {
		starting,//Start searching for beacons
		found,//One or more beacons found
		nothingFound//No beacons found
	}
	public BeaconAvailabilityState beaconAvailabilityState = BeaconAvailabilityState.starting;//Currents state of beacon receiving

	//Map data
	MapIR currentMap;//Current loaded map
	List<MapIR> availableLocalMaps;//Already downloaded maps
	List<MapIR> foundOnlineMaps;//Maps which were found after online search.
	protected Context mContext;


	public void onCreate(Context context) {
		mContext = context;
		EventBus.getDefault().register(this);
		beaconServiceBindCount++;
		if( beaconServiceBindCount == 1) mContext.startService(new Intent(mContext, BeaconService.class));
		mapServiceBindCount++;
		if( mapServiceBindCount == 1) mContext.startService(new Intent(mContext, MapService.class));


		//Register for broadcast on bluetooth-events
		mContext.registerReceiver(btReceive, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));


		{//Debug-map
			currentMap = new MapIR();

			currentMap.name = "debug map 1";
			currentMap.id = 1;
			currentMap.description = "This is the first testing map for debugging purposes.";
			currentMap.imagePath = "debugmap1.png";
			{//Bitmap
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				currentMap.image = BitmapFactory.decodeFile(currentMap.imagePath, options);
			}
			currentMap.majorID = 1;
			currentMap.beacons.put(1, new MapBeaconIR( "Entrance/Exit", 1, 1, 532, 446, "Were you can go in and out." ));
			currentMap.beacons.put(2, new MapBeaconIR( "Auditorium 1", 2, 2, 395, 305, "A very important lecture." ));
			currentMap.beacons.put(3, new MapBeaconIR( "Auditorium 2", 3, 3, 400, 130, "Also very important." ));
			currentMap.beacons.put(4, new MapBeaconIR( "WC", 4, 4, 395, 500, "Your loo." ));

			currentMap.beaconMap.put(1,1);
			currentMap.beaconMap.put(2,2);
			currentMap.beaconMap.put(3,3);
			currentMap.beaconMap.put(4,4);

			{//Ordinary places
				List<Integer> ordinaryPlaceExits = new ArrayList<>();
				ordinaryPlaceExits.add(1);
				List<Integer> ordinaryPlaceToilets = new ArrayList<>();
				ordinaryPlaceToilets.add(4);
				currentMap.ordinaryPlaces.put("Exit", ordinaryPlaceExits);
				currentMap.ordinaryPlaces.put("WC", ordinaryPlaceToilets);
			}
			{//Special places
				List<Integer> specialPlaceAuditoriums = new ArrayList<>();
				specialPlaceAuditoriums.add(2);
				specialPlaceAuditoriums.add(3);
				currentMap.specialPlaces.put("Auditorium", specialPlaceAuditoriums);
			}
		}
	}
	public void onDestroy() {
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
			Log.d(TAG, "onMessageEvent: EVENT_BEACON_SERVICE_STARTED");

			EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
		}
		else if (event.message == ServiceInterfaceEvent.EVENT_MAP_SERVICE_STARTED) {
			Log.d(TAG, "onMessageEvent: EVENT_MAP_SERVICE_STARTED");

			EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_GET_ALL_LOCAL_MAPS));
			//EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_SAVE_MAP_LOCAL, currentMap));//todo del
			//EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_FIND_ONLINE_MAP, "apm"));

		}
	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(MapUpdateEvent event) {
		if (event.message == MapUpdateEvent.EVENT_MAP_LOADED) {
			Log.d(TAG, "onMessageEvent: EVENT_MAP_LOADED");

			if( !event.maps.isEmpty() )
				currentMap = event.maps.get(0);
			else currentMap = null;

			if( beaconAvailabilityState != BeaconAvailabilityState.starting &&
					!availableLocalMaps.contains(currentMap) ) {//Current map removed and not in starting mode
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_CURRENT_MAP_UNLOADED));
			}
			if( event.maps.isEmpty() ) {
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_NO_LOCAL_MAPS_AVAILABLE));
			}
			else {
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_NEW_MAP_LOADED));
			}
		}
		else if (event.message == MapUpdateEvent.EVENT_AVAIL_OFFLINE_MAPS_LOADED) {
			Log.d(TAG, "onMessageEvent: EVENT_AVAIL_OFFLINE_MAPS_LOADED");
			availableLocalMaps = event.maps;
		}
		else if (event.message == MapUpdateEvent.EVENT_FOUND_ONLINE_MAPS) {
			Log.d(TAG, "onMessageEvent: EVENT_FOUND_ONLINE_MAPS");
			foundOnlineMaps = event.maps;
			EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_FOUND_ONLINE_MAPS));
		}
	}


	//Receiver checks if bluetooth was (de-)activated
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
