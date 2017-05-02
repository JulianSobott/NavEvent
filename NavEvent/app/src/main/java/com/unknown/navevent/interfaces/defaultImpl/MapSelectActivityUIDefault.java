package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.MapData;
import com.unknown.navevent.interfaces.MapSelectActivityUI;

import java.util.List;

public class MapSelectActivityUIDefault implements MapSelectActivityUI
{
	private MapSelectActivityLogicDefault mIfc = null;
	MapSelectActivityUIDefault() {
		mIfc = new MapSelectActivityLogicDefault(this);
	}

	public void onlineMapsRespond(List<MapData> maps) {}
	public void downloadFailed(String errorcode) {}
	public void isOffline() {}

	public void downloadFinished(String name) {}

	public void foundLocalMap(String name) {}
}
