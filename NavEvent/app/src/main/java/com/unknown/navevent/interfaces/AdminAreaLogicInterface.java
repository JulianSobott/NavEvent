package com.unknown.navevent.interfaces;

import android.content.Context;

//Interface for the AdminArea-Logic
public interface AdminAreaLogicInterface
{
	//Should be called on creation.
	void onCreate(Context context);
	//Should be called on destruction.
	void onDestroy();

	//Will download the map specified by \p mapID. Will respond with AdminAreaUI.updateMap(), AdminAreaUI.invalidMapID(), AdminAreaUI.downloadFailed() or AdminAreaUI.isOffline().
	void loadMap(int mapID);

	//Configure neares local-area beacon with map-data for \p beaconID. Will respond in AdminAreaUI.beaconSuccessfullyConfigured() or AdminAreaUI.beaconConfigurationFailed().
	void configureBeacon(int beaconID);
}
