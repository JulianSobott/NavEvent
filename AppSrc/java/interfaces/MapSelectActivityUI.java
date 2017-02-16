package com.unknown.navevent.interfaces;

import java.util.List;

//Interface for responses to the UI. Has to be implemented by MapSelectActivity-class.
public interface MapSelectActivityUI
{
	//Respond to MapSelectActivityLogicInterface.loadOnlineMaps()
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
