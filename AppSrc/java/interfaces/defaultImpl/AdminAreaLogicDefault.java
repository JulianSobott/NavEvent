package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.AdminAreaLogic;
import com.unknown.naveventinterfacetest.interfaces.AdminAreaUI;

public class AdminAreaLogicDefault implements AdminAreaLogic
{
	private AdminAreaUI mResponder = null;
	AdminAreaLogicDefault(AdminAreaUI responder) {
		mResponder = responder;
	}

	public void loadMap(int mapID) { mResponder.updateMap(new MapDataDefault()); }

	public void configureBeacon(int beaconID) { mResponder.beaconSuccessfullyConfigured(); }
}
