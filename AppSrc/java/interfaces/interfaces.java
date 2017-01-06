package com.unknown.naveventinterfacetest.interfaces;

/* This file contains interfaces for the user-interface and the logic.
 * MapData and BeaconData are classes to define a interface to prepared data.
 * The other classes occour in pairs of one logic-interface (named '-Logic', should be implemented by the logic)
 * and one ui-interface (named '-UI', should be implemented by the ui).
 */

import android.graphics.Bitmap;

import java.util.List;

//General interface to get map-data.
interface MapData
{
	//Returns the human-readable name of the Map.
	String getName();

	//Returns the image which represents the map.
	Bitmap getImage();

	//Returns all beacons on the map.
	List<BeaconData> getBeacons();
}
//General interface to get beacon-data.
interface BeaconData
{
	//Returns beaconID on the map.
	int getId();

	//Returns the human-readable name of the beacon.
	String getName();

	//Return the x-position on the map.
	double getMapPositionX();
	//Return the y-position on the map.
	double getMapPositionY();
}




//Interface for the MainActivity-Logic
interface MainActivityLogic
{
	//Sould be called on startup. Will controll internet-connection, permissions, bluetooth, etc. Will respond with MainActivityUI.initCompleted(), MainActivityUI.notSupported(), MainActivityUI.bluetoothDeactivated() or MainActivityUI.askForPermissions().
	void initBeaconManager();

	//Sould be called after permission-check failed with MainActivityUI.askForPermissions().
	void retryBeaconConnection();

	//Starts loading of active map, if not already loaded. Will respond with MainActivityUI.updateMap()
	void getMap(String name);
}
//Interface for responses to the UI. Has to be implemented by MainActivity-class.
interface MainActivityUI
{
	//Respond to MainActivityLogic.initBeaconManager().
	void initCompleted();
	//Will be called, if bluetooth or beacons are not supported. \p errorcode contains a human-readable descrition of the problem.
	void notSupported(String errorcode);
	//Will be called, if bluetooth is deactivated, but should be activated to detect beacons.
	void bluetoothDeactivated();
	//Will be called, if permissions for COARSE_LOCATION is not given, but should be to detect beacons.
	void askForPermissions();

	//Respond to MainActivityLogic.getMap(). \p map is the data contained in the active map.
	void updateMap(MapData map);

	//Will be called, if a new 'neares' beacon was found. \p beaconID is the id of the new beacon in MapData.
	void updateBeaconPosition(int beaconID);

	//Respond to NavigationDrawerLogik.findSpecialBeacon() or NavigationDrawerLogik.findAllSpecialBeacon() to mark the results. \p beaconIDs is a list of the beacons which should be marked.
	void markBeacons(List<Integer> beaconIDs);
}

//Interface for the MapSelectActivity-Logic
interface MapSelectActivityLogic
{
	//Returns a list of the offline saved maps
	List<String> loadAvailableMaps();

	//Returns true, if connection to server is available
	boolean isOnline();
	//Load maps which match to the string \p name. Responses in MapSelectActivityUI.onlineMapsRespond(), MapSelectActivityUI.downloadFailed() or MapSelectActivityUI.isOffline()
	void loadOnlineMaps(String name);

	//Download the map sepecified by \p name. Responses in MapSelectActivityUI.downloadFinished(), MapSelectActivityUI.downloadFailed() or MapSelectActivityUI.isOffline()
	void downloadMap(String name);

	//Set active map to a map specified by \p name. Returns false if map is not available offline.
	boolean setActiveMap(String name);
}
//Interface for responses to the UI. Has to be implemented by MapSelectActivity-class.
interface MapSelectActivityUI
{
	//Respond to MapSelectActivityLogic.loadOnlineMaps()
	void onlineMapsRespond(List<String> maps);
	//Map-download failed. \p errorcode contains a human-readable descrition of the problem.
	void downloadFailed(String errorcode);
	//Network-action failed. Cannot connect to server.
	void isOffline();

	//Map-download finished. \p name is the map-name.
	void downloadFinished(String name);

	//Is called when a beacon and a corresponding map could be found. Will only be called if is online. \p name will contain the map-name.
	void foundLocalMap(String name);
}

//Interface for the BottomSheet-Logic
interface BottomSheetLogic
{
	//Get the information of the beacon specified by beaconID. Responses in BottomSheetUI.beaconInfoRespond().
	void getBeaconInfo(int beaconID);
}
//Interface for responses to the UI. Has to be implemented by BottomSheet-class.
interface BottomSheetUI
{
	//Repond to BottomSheetLogic.getBeaconInfo(). \p info will contain the data.
	void beaconInfoRespond(String info);
}

//Interface for the NavigationDrawer-Logic
interface NavigationDrawerLogic
{
	//Will return a list of special beacon-names on the active map (e. g. exit)
	List<String> getSpecialBeacons();

	//Will respond with MainActivityUI.markBeacons() with the best/nearest beacon which matches to \p name.
	void findSpecialBeacon(String name);
	//Will respond with MainActivityUI.markBeacons() with all beacon which match to \p name.
	void findAllSpecialBeacon(String name);

	//Search for all beacons which contain \p name in their name and/or information. Will respond in NavigationDrawerUI.searchResults().
	void searchFor(String name);

	//Will return a list of beacons which are marked as important (in the active map).
	List<BeaconData> getImportantPlaces();
}
//Interface for responses to the UI. Has to be implemented by NavigationDrawer-class.
interface NavigationDrawerUI
{
	//Respond to NavigationDrawerLogic.searchFor(). \p results will contain all beacons which match to the search.
	void searchResults(List<BeaconData> results);
}

//Interface for the QrCodeReader-Logic
interface QrCodeReaderLogic
{
	//Will start camera, take a picture and generate the map-id/-link. Will respond with QrCodeReaderUI.capturedMapID().
	void takePicture();
}
//Interface for responses to the UI. Has to be implemented by QrCodeReader-class.
interface QrCodeReaderUI
{
	//Respond to QrCodeReaderLogic.capturedMapID(). \p mapID is the id which was generated from the qr-code
	void capturedMapID(int mapID);
}

//Interface for the AdminArea-Logic
interface AdminAreaLogic
{
	//Will download the map specified by \p mapID. Will resppond with AdminAreaUI.upateMap(), AdminAreaUI.invalidMapID(), AdminAreaUI.downloadFailed() or AdminAreaUI.isOffline().
	void loadMap(int mapID);

	//Configure neares local-area beacon with map-data for \p beaconID. Will respond in AdminAreaUI.beaconSuccessfullyConfigured() or AdminAreaUI.beaconConfigurationFailed().
	void configureBeacon(int beaconID);
}
//Interface for responses to the UI. Has to be implemented by AdminArea-class.
interface AdminAreaUI
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
