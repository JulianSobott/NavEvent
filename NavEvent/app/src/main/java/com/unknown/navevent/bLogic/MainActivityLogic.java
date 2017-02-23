package com.unknown.navevent.bLogic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.BeaconUpdateEvent;
import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

//Background-logic of the MainActivity
public class MainActivityLogic  implements MainActivityLogicInterface {
	private static final String TAG = "MainActivityLogic";

	private MainActivityUI mResponder = null;

	private ServiceInterface serviceInterface = new ServiceInterface();

	private static final int HANDLER_NO_BEACON_DELAY = 1;//Timed handling after loosing all beacons

	private boolean searchingForCurrentMap = true;//Wait until current beacons where found (on start)
	private static final int HANDLER_NO_CORRESPONDING_BEACON_DELAY = 1;//Timed handling after haven't found beacon corresponding to any local map

	//private boolean isStaring = true;//To enable checking for beacons//del


	public MainActivityLogic(MainActivityUI responder) {
		mResponder = responder;
	}


	@Override
	public void onCreate(Context context) {
		EventBus.getDefault().register(this);

		serviceInterface.onCreate(context);
	}
	@Override
	public void onDestroy() {
		serviceInterface.onDestroy();


		EventBus.getDefault().unregister(this);
	}

	@Override
	public void retryBeaconConnection() {
		EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
	}

	@Override
	public void getMap(String name) {

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
		if( event.message == ServiceToActivityEvent.EVENT_LISTENER_STARTED) {
			Log.d(TAG, "onMessageEvent: EVENT_LISTENER_STARTED");

			//todo
		}
		else if( event.message == ServiceToActivityEvent.EVENT_BLUETOOTH_DEACTIVATED) {
			Log.d(TAG, "onMessageEvent: EVENT_BLUETOOTH_DEACTIVATED");

			mResponder.bluetoothDeactivated();
		}
		else if( event.message == ServiceToActivityEvent.EVENT_BLUETOOTH_NOT_SUPPORTED) {
			Log.d(TAG, "onMessageEvent: EVENT_BLUETOOTH_NOT_SUPPORTED");

			mResponder.notSupported("");
		}
		else if( event.message == ServiceToActivityEvent.EVENT_BLE_NOT_SUPPORTED) {
			Log.d(TAG, "onMessageEvent: EVENT_BLE_NOT_SUPPORTED");

			mResponder.notSupported("");
		}
		else if( event.message == ServiceToActivityEvent.EVENT_ASK_PERMISSION) {
			Log.d(TAG, "onMessageEvent: EVENT_ASK_PERMISSION");

			mResponder.askForPermissions();
		}
		else if( event.message == ServiceToActivityEvent.EVENT_CURRENT_MAP_UNLOADED) {
			Log.d(TAG, "onMessageEvent: EVENT_CURRENT_MAP_UNLOADED");

			mResponder.switchToMapSelectActivity();
		}
		else if( event.message == ServiceToActivityEvent.EVENT_NO_LOCAL_MAPS_AVAILABLE) {
			Log.d(TAG, "onMessageEvent: EVENT_NO_LOCAL_MAPS_AVAILABLE");

			mResponder.switchToMapSelectActivity();
		}
	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(BeaconUpdateEvent event) {
		if( event.message == BeaconUpdateEvent.EVENT_BEACON_UPDATE) {
			Log.d(TAG, "onMessageEvent: EVENT_BEACON_UPDATE");

			if( event.beacons != null && event.beacons.size() > 0) {
				int nearestIndex = 0;
				double nearestDistance = event.beacons.get(0).distance;
				for (BeaconIR beacon : event.beacons) {
					if ( nearestDistance > beacon.distance ) {
						nearestIndex = event.beacons.indexOf(beacon);
						nearestDistance = beacon.distance;
					}
				}

				//Search until any map was found
				if( searchingForCurrentMap && !serviceInterface.availableLocalMaps.isEmpty() ) {
					mNoCorrespondingBeaconHandler.sendEmptyMessageDelayed(HANDLER_NO_CORRESPONDING_BEACON_DELAY, 5000);

					for (BeaconIR beacon : event.beacons) {
						for( MapIR map : serviceInterface.availableLocalMaps ) {
							if (beacon.majorID == map.majorID) {//Found a beacon corresponding to a local map
								searchingForCurrentMap = false;
							}
						}
					}
				}

				//Set beacon availability state
				serviceInterface.beaconAvailabilityState = ServiceInterface.BeaconAvailabilityState.found;

				//Update ui
				mResponder.updateBeaconPosition(event.beacons.get(nearestIndex).minorID);//todo: match to id-impl
				mNoBeaconHandler.removeMessages(HANDLER_NO_BEACON_DELAY);//Stop delay for "no beacon found"
			}
			else {
				//Set beacon availability state
				serviceInterface.beaconAvailabilityState = ServiceInterface.BeaconAvailabilityState.nothingFound;

				mNoBeaconHandler.sendEmptyMessageDelayed(HANDLER_NO_BEACON_DELAY, 5000);
			}
		}
	}

	//Handles if no beacons-signals were received after delay
	private Handler mNoBeaconHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if ( msg.what == HANDLER_NO_BEACON_DELAY ) {
				//Toast.makeText(mContext, "Beacon0 set", Toast.LENGTH_SHORT).show();
				mResponder.updateBeaconPosition(0);
			}
		}
	};
	//Handles if no beacons-signals were received after delay
	private Handler mNoCorrespondingBeaconHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if ( msg.what == HANDLER_NO_CORRESPONDING_BEACON_DELAY ) {
				if( searchingForCurrentMap ) {//No corresponding beacons to map found
					searchingForCurrentMap = false;
					mResponder.switchToMapSelectActivity();
				}
			}
		}
	};

}
