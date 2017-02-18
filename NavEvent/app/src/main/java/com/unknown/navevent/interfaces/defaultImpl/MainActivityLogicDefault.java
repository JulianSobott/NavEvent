package com.unknown.navevent.interfaces.defaultImpl;

import android.content.Context;

import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;

public class MainActivityLogicDefault implements MainActivityLogicInterface
{
	private MainActivityUI mResponder = null;
	MainActivityLogicDefault(MainActivityUI responder) {
		mResponder = responder;
	}

	public void onCreate(Context context) {}

	public void onDestroy() {}

	public void initBeaconManager() { mResponder.initCompleted(); }

	public void retryBeaconConnection() { mResponder.initCompleted(); }

	public void getMap(String name) { mResponder.updateMap(new MapDataDefault()); mResponder.updateBeaconPosition(0); } //Second call not necessarily
}
