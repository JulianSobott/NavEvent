package com.unknown.navevent.interfaces;

import java.util.List;

//Interface for responses to the UI. Has to be implemented by MapSelectActivity-class.
public interface MapSelectActivityUI
{
	//Respond to MapSelectActivityLogicInterface.findOnlineMap()
	void onlineMapQueryRespond(List<MapData> maps);
	//Local(offline) maps were loaded.
	void localMapsLoaded(List<MapData> maps);

	//Map-download failed. \p errorcode contains a human-readable descrition of the problem.
	void downloadFailed(String errorcode);
	//Network-action failed. Cannot connect to server.
	void isOffline();

	//Map-download finished. \p name is the map-name.
	void downloadFinished(MapData map);

	//Is called when a beacon and a corresponding map could be found.
	void foundLocalMap(MapData map);//todo del
}
