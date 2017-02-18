package com.unknown.navevent.bLogic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

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



	private static final int HANDLER_NO_BEACON_DELAY = 1;

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
		else if( event.message == MainActivityLogicEvent.EVENT_BLE_NOT_SUPPORTED) {
			Log.d("LogicIfcBase", "onMessageEvent: EVENT_BLE_NOT_SUPPORTED");

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

				mResponder.updateBeaconPosition(event.beacons.get(nearestIndex).minorID);//todo: match to id-impl
				mNoBeaconHandler.removeMessages(HANDLER_NO_BEACON_DELAY);
			}
			else {
				mNoBeaconHandler.sendEmptyMessageDelayed(HANDLER_NO_BEACON_DELAY, 5000);
				//Toast.makeText(mContext, "Beacon0 set", Toast.LENGTH_SHORT).show();
				//mResponder.updateBeaconPosition(0);
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

}
