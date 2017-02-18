package com.unknown.navevent.bLogic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.LogicIfcBaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LogicIfcBase {

	public static int beaconServiceBindCount = 0;//Handles service-access of multiple components(e. g. Activities)

	void onStart(Context context) {

		beaconServiceBindCount++;
		if( beaconServiceBindCount == 1) context.startService(new Intent(context, BeaconService.class));
	}
	void onStop() {

		beaconServiceBindCount--;
		if( beaconServiceBindCount == 0 ) EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_STOP_SELF));//todo: check if interrupts other activities

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(LogicIfcBaseEvent event) {
		if (event.message == LogicIfcBaseEvent.EVENT_SERVICE_STARTED) {
			Log.d("LogicIfcBase", "onMessageEvent: EVENT_SERVICE_STARTED");

			EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
		}
	}
}
