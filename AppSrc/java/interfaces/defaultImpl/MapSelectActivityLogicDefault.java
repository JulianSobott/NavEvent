package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.MapSelectActivityLogic;
import com.unknown.naveventinterfacetest.interfaces.MapSelectActivityUI;

import java.util.ArrayList;
import java.util.List;

public class MapSelectActivityLogicDefault implements MapSelectActivityLogic
{
	private MapSelectActivityUI mResponder = null;
	MapSelectActivityLogicDefault(MapSelectActivityUI responder) {
		mResponder = responder;
	}

	public List<String> loadAvailableMaps() {
		List<String> m = new ArrayList<String>();
		m.add("DefaultMap");
		return m;
	}

	public boolean isOnline() { return true; }
	public void loadOnlineMaps(String name) {

		List<String> m = new ArrayList<String>();
		m.add("DefaultMap");
		mResponder.onlineMapsRespond(m);
	}

	public void downloadMap(String name) { mResponder.downloadFinished(name); }

	public boolean setActiveMap(String name) { return true; }
}
