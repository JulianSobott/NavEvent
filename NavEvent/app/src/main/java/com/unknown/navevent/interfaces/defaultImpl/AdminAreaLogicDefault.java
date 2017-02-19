package com.unknown.navevent.interfaces.defaultImpl;

import android.content.Context;

import com.unknown.navevent.interfaces.AdminAreaLogicInterface;
import com.unknown.navevent.interfaces.AdminAreaUI;

public class AdminAreaLogicDefault implements AdminAreaLogicInterface
{
	private AdminAreaUI mResponder = null;
	AdminAreaLogicDefault(AdminAreaUI responder) {
		mResponder = responder;
	}

	public void onCreate(Context context) {}

	public void onDestroy() {}

	public void loadMap(int mapID) { mResponder.updateMap(new MapDataDefault()); }

	public void configureBeacon(int beaconID) { mResponder.beaconSuccessfullyConfigured(); }
}
