package com.unknown.navevent.interfaces;

import java.util.List;

//Interface for the MapSelectActivity-Logic
public interface MapSelectActivityLogicInterface
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
