package com.unknown.navevent.bLogic;

import com.unknown.navevent.interfaces.MainActivityLogicInterface;

public class MainActivityLogic extends LogicIfcBase implements MainActivityLogicInterface {

	@Override
	public void onCreate() {
		onStart();
	}
	@Override
	public void onDestroy() {
		onStop();
	}

	@Override
	public void initBeaconManager() {

	}

	@Override
	public void retryBeaconConnection() {

	}

	@Override
	public void getMap(String name) {

	}
}
