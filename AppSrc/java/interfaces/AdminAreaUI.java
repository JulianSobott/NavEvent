package com.unknown.naveventinterfacetest.interfaces;

//Interface for responses to the UI. Has to be implemented by AdminArea-class.
public interface AdminAreaUI
{
	//Respond to AdminAreaLogic.loadMap(). \p map is the data contained in the downloaded map.
	void updateMap(MapData map);
	//Respond to AdminAreaLogic.loadMap(). The map-id doesn't match to a map on the server.
	void invalidMapID();
	//Respond to AdminAreaLogic.loadMap(). Download failed. \p errorcode contains a human-readable descrition of the problem.
	void downloadFailed(String errorcode);
	//Respond to AdminAreaLogic.loadMap(). No connection to the server.
	void isOffline();

	//Positive respond to AdminAreaLogic.configureBeacon().
	void beaconSuccessfullyConfigured();
	//Negative respond to AdminAreaLogic.configureBeacon(). \p errorcode contains a human-readable descrition of the problem.
	void beaconConfigurationFailed(String errorcode);

}
