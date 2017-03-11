package com.unknown.navevent.interfaces;

import java.util.List;

//Interface for the NavigationDrawer-Logic
public interface NavigationDrawerLogicInterface
{
	//Will return a list of special beacon-names on the active map (e. g. exit)
	List<String> getSpecialBeacons();

	//Will respond with MainActivityUI.markBeacons() with the best/nearest beacon which matches to \p name.
	void findSpecialBeacon(String name);
	//Will respond with MainActivityUI.markBeacons() with all beacon which match to \p name.
	void findAllSpecialBeacon(String name);

	//Search for all beacons which contain \p name in their name and/or information. Will respond in NavigationDrawerUI.searchResults().
	void searchFor(String name);

	//Will return a list of beacons which are marked as important (in the active map).
	List<BeaconData> getImportantPlaces();
}
