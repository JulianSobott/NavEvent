package com.unknown.naveventinterfacetest.interfaces;

//Interface for the AdminArea-Logic
public interface AdminAreaLogic
{
	//Will download the map specified by \p mapID. Will resppond with AdminAreaUI.upateMap(), AdminAreaUI.invalidMapID(), AdminAreaUI.downloadFailed() or AdminAreaUI.isOffline().
	void loadMap(int mapID);

	//Configure neares local-area beacon with map-data for \p beaconID. Will respond in AdminAreaUI.beaconSuccessfullyConfigured() or AdminAreaUI.beaconConfigurationFailed().
	void configureBeacon(int beaconID);
}
