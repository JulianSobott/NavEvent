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

	public BeaconService beaconService;

	void onStart(Context context) {
		//EventBus.getDefault().register(this);//todo del

		context.startService(new Intent(context, BeaconService.class));
	}
	void onStop() {
		EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_STOP_SELF));//todo: check if interrupts other activities

		//EventBus.getDefault().unregister(this);//todo del
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(LogicIfcBaseEvent event) {
		if (event.message == LogicIfcBaseEvent.EVENT_SERVICE_STARTED) {
			Log.d("LogicIfcBase", "onMessageEvent: EVENT_SERVICE_STARTED");

			EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
		}
	}
}
