package com.unknown.navevent.interfaces;

import android.content.Context;

import java.util.List;

//Interface for the NavigationDrawer-Logic
public interface NavigationDrawerLogicInterface
{
	//Should be called on creation.
	void onCreate();
	//Should be called on destruction.
	void onDestroy();

	//Will return a list of special beacon-names on the active map (e. g. exit)
	List<String> getSpecialBeacons();

	//Will return a list of beacon-names which are marked as important (in the active map).
	List<String> getOrdinaryBeacons();

	//Will respond with MainActivityUI.markBeacons() with all beacon which match to \p name.
	void findAllSpecialBeacons(String name);

	//Will respond with MainActivityUI.markBeacons() with all beacon which match to \p name.
	void findAllOrdinaryBeacons(String name);

	//Search for all beacons which contain \p name in their name and/or information. Will respond in NavigationDrawerUI.searchResults().
	void searchFor(String name);

}
