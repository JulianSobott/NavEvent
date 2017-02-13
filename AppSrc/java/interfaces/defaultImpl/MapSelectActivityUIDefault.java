package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.MapSelectActivityUI;

import java.util.List;

public class MapSelectActivityUIDefault implements MapSelectActivityUI
{
	private MapSelectActivityLogicDefault mIfc = null;
	MapSelectActivityUIDefault() {
		mIfc = new MapSelectActivityLogicDefault(this);
	}

	public void onlineMapsRespond(List<String> maps) {}
	public void downloadFailed(String errorcode) {}
	public void isOffline() {}

	public void downloadFinished(String name) {}

	public void foundLocalMap(String name) {}
}
