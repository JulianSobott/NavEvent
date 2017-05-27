package com.unknown.navevent.interfaces;

import android.content.Context;

import java.util.List;

//Interface for the MapSelectActivity-Logic
public interface MapSelectActivityLogicInterface
{
	//Should be called on creation.
	void onCreate(Context context);
	//Should be called on destruction.
	void onDestroy();

	//Returns a list of the offline saved maps
	//List<String> loadAvailableMaps();//todo del

	//Returns true, if connection to server is available
	//boolean isOnline();//todo del

	//Load maps which match to the query string \p name. Responses in MapSelectActivityUI.onlineMapsRespond(), MapSelectActivityUI.downloadFailed() or MapSelectActivityUI.isOffline()
	void findOnlineMap(String query);

	//Download the map sepecified by \p name. Responses in MapSelectActivityUI.downloadFinished(), MapSelectActivityUI.downloadFailed() or MapSelectActivityUI.isOffline()
	void downloadMap(int mapID);

	//Set active map to a map specified by \p name. Returns false if map is not available offline.
	boolean setActiveMap(int mapID);
}
