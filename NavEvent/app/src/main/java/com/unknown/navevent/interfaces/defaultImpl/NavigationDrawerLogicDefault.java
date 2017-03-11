package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerLogicInterface;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerLogicDefault implements NavigationDrawerLogicInterface
{
	private NavigationDrawerUI mResponder = null;
	NavigationDrawerLogicDefault(NavigationDrawerUI responder) {
		mResponder = responder;
	}

	public List<String> getSpecialBeacons() {
		List<String> b = new ArrayList<String>();
		b.add("Exit");
		return b;
	}

	public void findSpecialBeacon(String name) {} // todo: add respond to MainActivityUI
	public void findAllSpecialBeacon(String name) {} // todo: add respond to MainActivityUI

	public void searchFor(String name) {
		List<BeaconData> b = new ArrayList<BeaconData>();
		b.add(new BeaconDataDefault());
		mResponder.searchResults(b);
	}

	public List<BeaconData> getImportantPlaces() {
		List<BeaconData> b = new ArrayList<BeaconData>();
		b.add(new BeaconDataDefault());
		return b;
	}
}
