package com.unknown.navevent.interfaces;

import android.content.Context;

//Interface for the MainActivityLogicImpl-Logic
public interface MainActivityLogicInterface
{
	void onCreate(Context context);
	void onDestroy();

	//Sould be called on startup. Will controll internet-connection, permissions, bluetooth, etc. Will respond with MainActivityUI.initCompleted(), MainActivityUI.notSupported(), MainActivityUI.bluetoothDeactivated() or MainActivityUI.askForPermissions().
	void initBeaconManager();

	//Sould be called after permission-check failed with MainActivityUI.askForPermissions().
	void retryBeaconConnection();

	//Starts loading of active map, if not already loaded. Will respond with MainActivityUI.updateMap()
	void getMap(String name);
}
