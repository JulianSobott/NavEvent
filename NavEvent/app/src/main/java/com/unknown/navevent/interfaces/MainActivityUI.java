package com.unknown.navevent.interfaces;

import java.util.List;

//Interface for responses to the UI. Has to be implemented by MainActivityLogicImpl-class.
public interface MainActivityUI
{
	//Respond to MainActivityLogicInterface.initBeaconManager().
	void initCompleted();
	//Will be called, if bluetooth or beacons are not supported. \p errorcode contains a human-readable descrition of the problem.
	void notSupported(String errorcode);
	//Will be called, if bluetooth is deactivated, but should be activated to detect beacons.
	void bluetoothDeactivated();
	//Will be called, if permissions for COARSE_LOCATION is not given, but should be to detect beacons.
	void askForPermissions();

	//Respond to MainActivityLogicInterface.getMap(). \p map is the data contained in the active map.
	void updateMap(MapData map);

	//Will be called, if a new 'neares' beacon was found. \p beaconID is the id of the new beacon in MapData.
	void updateBeaconPosition(int beaconID);

	//Respond to NavigationDrawerLogik.findSpecialBeacon() or NavigationDrawerLogik.findAllSpecialBeacon() to mark the results. \p beaconIDs is a list of the beacons which should be marked.
	void markBeacons(List<Integer> beaconIDs);
}
