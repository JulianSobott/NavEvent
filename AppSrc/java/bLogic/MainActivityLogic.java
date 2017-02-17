package com.unknown.navevent.bLogic;

import android.content.Context;
import android.util.Log;

import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.BeaconUpdateEvent;
import com.unknown.navevent.bLogic.events.LogicIfcBaseEvent;
import com.unknown.navevent.bLogic.events.MainActivityLogicEvent;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivityLogic extends LogicIfcBase implements MainActivityLogicInterface {

	private MainActivityUI mResponder = null;
	public MainActivityLogic(MainActivityUI responder) {
		mResponder = responder;
	}

	@Override
	public void onCreate(Context context) {
		EventBus.getDefault().register(this);

		onStart(context);
	}
	@Override
	public void onDestroy() {
		onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void initBeaconManager() {

	}

	@Override
	public void retryBeaconConnection() {
		EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
	}

	@Override
	public void getMap(String name) {

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(MainActivityLogicEvent event) {
		if( event.message == MainActivityLogicEvent.EVENT_BLUETOOTH_DEACTIVATED) {
			Log.d("LogicIfcBase", "onMessageEvent: EVENT_BLUETOOTH_DEACTIVATED");

			mResponder.bluetoothDeactivated();
		}
		else if( event.message == MainActivityLogicEvent.EVENT_BLUETOOTH_NOT_SUPPORTED) {
			Log.d("LogicIfcBase", "onMessageEvent: EVENT_BLUETOOTH_NOT_SUPPORTED");

			mResponder.notSupported("");
		}
		else if( event.message == MainActivityLogicEvent.EVENT_ASK_PERMISSION) {
			Log.d("LogicIfcBase", "onMessageEvent: EVENT_ASK_PERMISSION");

			mResponder.askForPermissions();
		}
	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(BeaconUpdateEvent event) {
		if( event.message == BeaconUpdateEvent.EVENT_BEACON_UPDATE) {
			Log.d("LogicIfcBase", "onMessageEvent: EVENT_BEACON_UPDATE");

			if( event.beacons != null && event.beacons.size() > 0) {
				int nearestIndex = 0;
				double nearestDistance = event.beacons.get(0).distance;
				for (BeaconIR beacon : event.beacons) {
					if ( nearestDistance > beacon.distance ) {
						nearestIndex = event.beacons.indexOf(beacon);
						nearestDistance = beacon.distance;
					}
				}

				mResponder.updateBeaconPosition(nearestIndex);//todo: match to id-impl
			}
			else mResponder.updateBeaconPosition(0);
		}
	}
}
