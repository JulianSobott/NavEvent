package com.unknown.navevent.bLogic;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.events.BeaconServiceEvent;
import com.unknown.navevent.bLogic.events.LogicIfcBaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

//Enables interaction with the BeaconService & MapService (including startup, etc.)
class ServiceInterface {

	private static int beaconServiceBindCount = 0;//Handles service-access of multiple components(e. g. Activities)

	protected Context mContext;


	MapIR currentMap;//Current loaded map


	void onCreate(Context context) {
		mContext = context;
		EventBus.getDefault().register(this);
		beaconServiceBindCount++;
		if( beaconServiceBindCount == 1) mContext.startService(new Intent(mContext, BeaconService.class));


		//Register for broadcast on bluetooth-events
		mContext.registerReceiver(btReceive, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

	}
	void onDestroy() {
		beaconServiceBindCount--;
		if( beaconServiceBindCount == 0 ) EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_STOP_SELF));//todo: check if interrupts other activities

		EventBus.getDefault().unregister(this);
		mContext.unregisterReceiver(btReceive);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(LogicIfcBaseEvent event) {
		if (event.message == LogicIfcBaseEvent.EVENT_SERVICE_STARTED) {
			Log.d("ServiceInterface", "onMessageEvent: EVENT_SERVICE_STARTED");

			EventBus.getDefault().post(new BeaconServiceEvent(BeaconServiceEvent.EVENT_START_LISTENING));
		}
	}


	//Receiver checks if bluetooth was de-/activated
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
