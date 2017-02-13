package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.BeaconData;
import com.unknown.naveventinterfacetest.interfaces.NavigationDrawerUI;

import java.util.List;

public class NavigationDrawerUIDefault implements NavigationDrawerUI
{
	private NavigationDrawerLogicDefault mIfc = null;
	NavigationDrawerUIDefault() {
		mIfc = new NavigationDrawerLogicDefault(this);
	}

	public void searchResults(List<BeaconData> results) {}
}
