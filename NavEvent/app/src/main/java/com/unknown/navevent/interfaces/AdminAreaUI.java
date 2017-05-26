package com.unknown.navevent.interfaces;

//Interface for responses to the UI. Has to be implemented by AdminArea-class.
public interface AdminAreaUI
{
	//Respond to AdminAreaLogicInterface.loadMap(). \p map is the data contained in the downloaded map.
	void updateMap(MapData map);
	//Respond to AdminAreaLogicInterface.loadMap(). The map-id doesn't match to a map on the server.
	void invalidMapID();
	//Respond to AdminAreaLogicInterface.loadMap(). Download failed. \p errorcode contains a human-readable descrition of the problem.
	void downloadFailed(String errorcode);

	//Positive respond to AdminAreaLogicInterface.configureBeacon().
	void beaconSuccessfullyConfigured();
	//Negative respond to AdminAreaLogicInterface.configureBeacon(). \p errorcode contains a human-readable descrition of the problem.
	void beaconConfigurationFailed(String errorcode);

}
