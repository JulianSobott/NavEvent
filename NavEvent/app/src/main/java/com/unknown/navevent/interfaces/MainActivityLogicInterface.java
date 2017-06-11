package com.unknown.navevent.interfaces;

import android.content.Context;

//Interface for the MainActivityLogicImpl-Logic
public interface MainActivityLogicInterface
{
	//Should be called on creation. Will check internet-connection, permissions, bluetooth, etc. Will respond with MainActivityUI.initCompleted(), MainActivityUI.notSupported(), MainActivityUI.bluetoothDeactivated() or MainActivityUI.askForPermissions().
	void onCreate(Context context);
	//Should be called on destruction.
	void onDestroy();

	//Should be called on start.
	void onStart();
	//Should be called on stop.
	void onStop();


	//Should be called after permission-check failed with MainActivityUI.askForPermissions().
	void retryBeaconConnection();

	//Starts loading of active map, if not already loaded. Will respond with MainActivityUI.updateMap()
	void getMap(int id);
}
