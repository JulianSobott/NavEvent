package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.MainActivityLogic;
import com.unknown.naveventinterfacetest.interfaces.MainActivityUI;

public class MainActivityLogicDefault implements MainActivityLogic
{
	private MainActivityUI mResponder = null;
	MainActivityLogicDefault(MainActivityUI responder) {
		mResponder = responder;
	}

	public void initBeaconManager() { mResponder.initCompleted(); }

	public void retryBeaconConnection() { mResponder.initCompleted(); }

	public void getMap(String name) { mResponder.updateMap(new MapDataDefault()); mResponder.updateBeaconPosition(0); } //Second call not necessarily
}
