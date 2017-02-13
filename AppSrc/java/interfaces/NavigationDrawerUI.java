package com.unknown.naveventinterfacetest.interfaces;

import java.util.List;

//Interface for responses to the UI. Has to be implemented by NavigationDrawer-class.
public interface NavigationDrawerUI
{
	//Respond to NavigationDrawerLogic.searchFor(). \p results will contain all beacons which match to the search.
	void searchResults(List<BeaconData> results);
}
