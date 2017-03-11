package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import java.util.List;

public class NavigationDrawerUIDefault implements NavigationDrawerUI
{
	private NavigationDrawerLogicDefault mIfc = null;
	NavigationDrawerUIDefault() {
		mIfc = new NavigationDrawerLogicDefault(this);
	}

	public void searchResults(List<BeaconData> results) {}
}
