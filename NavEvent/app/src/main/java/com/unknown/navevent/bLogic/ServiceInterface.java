package com.unknown.navevent.bLogic;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.BeaconUpdateEvent;
import com.unknown.navevent.bLogic.events.MapUpdateEvent;
import com.unknown.navevent.bLogic.events.ServiceInterfaceEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.bLogic.services.BeaconIR;
import com.unknown.navevent.bLogic.services.BeaconService;
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

	/////////////////////////////////////////////////////////
	// Settings
	/////////////////////////////////////////////////////////

	public static boolean autoDownloadMaps = true;//Map will be downloaded automatically, if nearby corresponding beacon found.


	/////////////////////////////////////////////////////////
	// Singleton stuff
	/////////////////////////////////////////////////////////

	private static ServiceInterface mSingleton = new ServiceInterface();
	private int instanceCount;

	public static ServiceInterface getInstance() {
		return mSingleton;
	}

	/////////////////////////////////////////////////////////
	// Data & constants
	/////////////////////////////////////////////////////////

	private static int beaconServiceBindCount = 0;//Handles service-access from multiple components(e. g. Activities)
	private static int mapServiceBindCount = 0;//Handles service-access from multiple components(e. g. Activities)

	private static final int HANDLER_NO_BEACON_DELAY = 1;//Timed handling after loosing all beacons
	private static final int NO_BEACON_DELAY_TIME = 5000;//milliseconds.

	private static final int HANDLER_NO_CORRESPONDING_BEACON_DELAY = 2;//Timed handling after haven't found beacon corresponding to any local map
	private static final int NO_CORRESPONDING_BEACON_DELAY_TIME = 5000;//milliseconds

	
	//Beacon data
	private enum BeaconAvailabilityState {
		starting,//Start searching for beacons
		found,//One or more beacons found
		nothingFound//No beacons found
	}
	private BeaconAvailabilityState beaconAvailabilityState = BeaconAvailabilityState.starting;//Currents state of beacon receiving
	List<BeaconIR> currentBeacons = new ArrayList<>();//Beacons in region
	int nearestBeaconID;//ID of nearest beacon
	int nearestBeaconMajorID = 0;//MajorID of nearest beacon. To find map on server.

	//Map data
	enum MapAvailabilityState {
		notLoaded,//No map is loaded. currentMap == null
		loading,//Map is loading, but currentMap == null
		loaded//Map was loaded currentMap != null
	}
	MapAvailabilityState mapAvailabilityState = MapAvailabilityState.notLoaded;//Currents state of map
	MapIR currentMap;//Current loaded map
	List<MapIR> availableLocalMaps = null;//Already downloaded maps
	MapIR lastDownloadedMap = null;
	List<MapIR> foundOnlineMaps = new ArrayList<>();//Maps which were found after online search.
	MapIR availableNearbyMap = null;//Map which was chosen by nearby beacons.
	boolean nearbyMapChosen = false;//If nearbyMap was chosen. Stop further attempts to select a nearby map.

	Context mContext = null;


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	public void onCreate(Context context) {
		mContext = context;

		instanceCount++;
		if( instanceCount == 1 ) {
			EventBus.getDefault().register(this);

			//Register for broadcast on bluetooth-events
			mContext.registerReceiver(btReceive, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
		}


		beaconServiceBindCount++;
		if( beaconServiceBindCount == 1) mContext.startService(new Intent(mContext, BeaconService.class));
		mapServiceBindCount++;
		if( mapServiceBindCount == 1) mContext.startService(new Intent(mContext, MapService.class));




		{//Debug-map todo del
			/*currentMap = new MapIR();

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
			}*/
		}
	}
	public void onDestroy() {
		beaconServiceBindCount--;
		if( beaconServiceBindCount == 0 ) EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_STOP_SELF));//todo: check if interrupts other activities
		mapServiceBindCount--;
		if( mapServiceBindCount == 0 ) {
			EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_STOP_SELF));//todo: check if interrupts other activities
			mContext.stopService(new Intent(mContext, MapService.class));
		}

		instanceCount--;
		if( instanceCount == 0 ) {
			EventBus.getDefault().unregister(this);
			mContext.unregisterReceiver(btReceive);
		}

	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onMessageEvent(ServiceInterfaceEvent event) {
		if (event.message == ServiceInterfaceEvent.EVENT_BEACON_SERVICE_STARTED) {
			Log.i(TAG, "onMessageEvent: EVENT_BEACON_SERVICE_STARTED");

			EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
		}
		else if (event.message == ServiceInterfaceEvent.EVENT_MAP_SERVICE_STARTED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_SERVICE_STARTED");

			EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_GET_ALL_LOCAL_MAPS));
			//EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_SAVE_MAP_LOCAL, currentMap));//todo del
			//EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_FIND_ONLINE_MAP_BY_QUERY, "map"));

		}
	}
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onMessageEvent(MapUpdateEvent event) {
		if (event.message == MapUpdateEvent.EVENT_MAP_LOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_LOADED");

			//Update current map
			if( !event.maps.isEmpty() ) {
				if (mapAvailabilityState == MapAvailabilityState.loaded && currentMap != event.maps.get(0))//Current map was unloaded
					EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_CURRENT_MAP_UNLOADED));

				currentMap = event.maps.get(0);
				mapAvailabilityState = MapAvailabilityState.loaded;

				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_NEW_MAP_LOADED));
			}
			else {//Map was unloaded
				currentMap = null;
				mapAvailabilityState = MapAvailabilityState.notLoaded;
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_NO_LOCAL_MAPS_AVAILABLE));
			}
		}
		else if (event.message == MapUpdateEvent.EVENT_AVAIL_OFFLINE_MAPS_LOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_AVAIL_OFFLINE_MAPS_LOADED ("+event.maps.size()+")");
			availableLocalMaps = event.maps;
			EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_AVAIL_LOCAL_MAPS_UPDATED));

			//Check if beacon corresponds to new map
			findCorrespondingMap();
		}
		else if (event.message == MapUpdateEvent.EVENT_FOUND_ONLINE_MAPS) {
			Log.i(TAG, "onMessageEvent: EVENT_FOUND_ONLINE_MAPS");
			foundOnlineMaps = event.maps;
			EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_FOUND_ONLINE_MAPS));
		}
		else if (event.message == MapUpdateEvent.EVENT_FOUND_ONLINE_MAP_BY_ID) {
			Log.i(TAG, "onMessageEvent: EVENT_FOUND_ONLINE_MAP_BY_ID");
			if( event.maps != null && event.maps.size() > 0 && ( availableNearbyMap == null || availableNearbyMap.getID() != event.maps.get(0).getID() ) ) {
				availableNearbyMap = event.maps.get(0);
				nearbyMapChosen = false;
				mapAvailabilityState = MapAvailabilityState.loading;
				if ( autoDownloadMaps ) {
					EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_DOWNLOAD_MAP, event.maps.get(0).getID()));
				} else
					EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_FOUND_CORRESPONDING_MAP));
			}
		}
		else if (event.message == MapUpdateEvent.EVENT_MAP_DOWNLOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOADED");
			if( event.maps.size() > 0) {
				lastDownloadedMap = event.maps.get(0);
				EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_GET_ALL_LOCAL_MAPS));
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_MAP_DOWNLOADED));
				if (currentMap == null) mapAvailabilityState = MapAvailabilityState.notLoaded;//Reset
			}
		}
		else if (event.message == MapUpdateEvent.EVENT_MAP_DOWNLOAD_FAILED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOAD_FAILED");
			EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_MAP_DOWNLOAD_FAILED));
			if (currentMap == null) mapAvailabilityState = MapAvailabilityState.notLoaded;//Reset
		}
	}
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onMessageEvent(BeaconUpdateEvent event) {
		if( event.message == BeaconUpdateEvent.EVENT_BEACON_UPDATE) {
			//Log.i("ServiceInterface", "onMessageEvent: EVENT_BEACON_UPDATE");

			if (mapAvailabilityState == MapAvailabilityState.notLoaded)//If first beacon call => set timed delay for no corresponding map -event
				mTimedDelayHandler.sendEmptyMessageDelayed(HANDLER_NO_CORRESPONDING_BEACON_DELAY, NO_CORRESPONDING_BEACON_DELAY_TIME);

			if (event.beacons != null && event.beacons.size() > 0) {
				currentBeacons = event.beacons;
				int nearestBeaconIndex = 0;
				nearestBeaconMajorID = currentBeacons.get(0).majorID;
				double nearestDistance = currentBeacons.get(0).distance;
				for (BeaconIR beacon : currentBeacons) {
					if ( ( mapAvailabilityState != MapAvailabilityState.loaded || beacon.majorID == currentMap.getMajorID() ) && nearestDistance > beacon.distance) {
						//Set nearest beacon (beacon which matches to current map OR to any map, if no map loaded)
						nearestBeaconIndex = currentBeacons.indexOf(beacon);
						nearestBeaconMajorID = beacon.majorID;
						nearestDistance = beacon.distance;
					}
				}

				//Set beacon availability state
				beaconAvailabilityState = ServiceInterface.BeaconAvailabilityState.found;

				findCorrespondingMap();

				//Update ui
				if( mapAvailabilityState == MapAvailabilityState.loaded ) {
					//Find id of minorID
					for( int i = 0 ; i < currentMap.getBeaconsIR().size() ; i++ ) {
						if( currentMap.getBeaconsIR().valueAt(i).minorID == currentBeacons.get(nearestBeaconIndex).minorID ) {
							//Found id
							nearestBeaconID = currentMap.getBeaconsIR().valueAt(i).id;
							EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_BEACON_UPDATE));
							break;
						}
					}
				}

				mTimedDelayHandler.removeMessages(HANDLER_NO_BEACON_DELAY);//Remove reminding delays

			} else {
				//Set beacon availability state
				if (beaconAvailabilityState == ServiceInterface.BeaconAvailabilityState.found) {
					beaconAvailabilityState = ServiceInterface.BeaconAvailabilityState.nothingFound;

					mTimedDelayHandler.sendEmptyMessageDelayed(HANDLER_NO_BEACON_DELAY, NO_BEACON_DELAY_TIME);
				}
			}
		}
	}


	/////////////////////////////////////////////////////////
	// Helper methods
	/////////////////////////////////////////////////////////

	//Checks if a beacon corresponds to a local saved map. Will load the map, if one was found.
	private void findCorrespondingMap() {
		//Search until any local map was found
		if( beaconAvailabilityState == BeaconAvailabilityState.found && availableLocalMaps != null && mapAvailabilityState == MapAvailabilityState.notLoaded ) {//Beacon has been found & local maps loaded & no map loaded
			boolean foundMap = false;
			if( !availableLocalMaps.isEmpty() ) {
				for (BeaconIR beacon : currentBeacons) {
					for (MapIR map : availableLocalMaps) {
						if (beacon.majorID == map.getMajorID()) { //Found a beacon corresponding to a local map.
							mTimedDelayHandler.removeMessages(HANDLER_NO_CORRESPONDING_BEACON_DELAY);//Stop delay
							EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_LOAD_MAP_LOCAL, map.getID()));
							mapAvailabilityState = MapAvailabilityState.loading;
							foundMap = true;
						}
					}
				}
			}
			if( !foundMap && !nearbyMapChosen ) {//No map found => (auto) download map of nearest beacon
				EventBus.getDefault().post(new MapServiceEvent(MapServiceEvent.EVENT_FIND_ONLINE_MAP_BY_ID, nearestBeaconMajorID));
				nearbyMapChosen = true;
			}
		}
	}


	/////////////////////////////////////////////////////////
	// Broadcast receiver & timed delay handler
	/////////////////////////////////////////////////////////

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
						Log.i("BTooth-change-BR", "onReceive: BLUETOOTH_ACTIVATED");
						EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
						break;
					case BluetoothAdapter.STATE_TURNING_ON:
						break;
				}

			}
		}
	};

	//Handles timed events. Will be called by the system.
	private Handler mTimedDelayHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if ( msg.what == HANDLER_NO_BEACON_DELAY ) {//no beacons-signals were received
				mTimedDelayHandler.removeMessages(HANDLER_NO_BEACON_DELAY);//Stop delay for "no beacon found"
				EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_NO_BEACON_FOUND));
			}
			else if ( msg.what == HANDLER_NO_CORRESPONDING_BEACON_DELAY ) {//no beacons-signals were received after delay (for a corresponding local map)
				if( mapAvailabilityState == MapAvailabilityState.notLoaded ) {//No corresponding beacons to map found
					mTimedDelayHandler.removeMessages(HANDLER_NO_CORRESPONDING_BEACON_DELAY);//Stop delay for "no beacon found"
					EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.EVENT_NO_CORRESPONDING_MAPS_AVAILABLE));
				}
			}
		}
	};
}
