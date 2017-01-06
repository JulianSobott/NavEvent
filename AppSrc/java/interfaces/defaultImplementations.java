package com.unknown.naveventinterfacetest.interfaces;

/* This file contains default/example implementations of the interfaces in interfaces.java
 * These classes should only be used for testing purposes. Later they will be replaced with the actual implementations.
 * By default this classes respond in 'best case', which means they don't call e. g. MainActivityUI.notSupported().
 *
 * Lifecycle:
 * 	1. First UI will be created (e. g. MainActivityUIDefault or MainActivity at all)
 * 	2. UI creates logic(e. g. MainActivityLogicDefault).
 * 	3. Logic-constructor will save referenze to UI to respond later.
 */

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

class MapDataDefault implements MapData
{
	public String getName() { return "DefaultMap"; }

	public Bitmap getImage() { return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888); } //Not a fixed size!

	public List<BeaconData> getBeacons() {
		List<BeaconData> b = new ArrayList<BeaconData>();
		b.add(new BeaconDataDefault());
		return b;
	}
}
class BeaconDataDefault implements BeaconData
{
	public int getId() { return 0; }

	public String getName() { return "Exit"; } //Example name.

	public double getMapPositionX() { return 0; }
	public double getMapPositionY() { return 0; }
}




class MainActivityLogicDefault implements MainActivityLogic
{
	private MainActivityUI mResponder = null;
	MainActivityLogicDefault(MainActivityUI responder) {
		mResponder = responder;
	}

	public void initBeaconManager() { mResponder.initCompleted(); }

	public void retryBeaconConnection() { mResponder.initCompleted(); }

	public void getMap(String name) { mResponder.updateMap(new MapDataDefault()); mResponder.updateBeaconPosition(0); } //Second call not necessarily
}
class MainActivityUIDefault implements MainActivityUI
{
	private MainActivityLogicDefault mIfc = null;
	MainActivityUIDefault() {
		mIfc = new MainActivityLogicDefault(this);
	}

	public void initCompleted() { mIfc.getMap("DefaultMap"); }//Not called necessarily
	public void notSupported(String errorcode) {}
	public void bluetoothDeactivated() {}
	public void askForPermissions() { mIfc.retryBeaconConnection(); }

	public void updateMap(MapData map) {}

	public void updateBeaconPosition(int beaconID) {}

	public void markBeacons(List<Integer> beaconIDs) {}
}

class MapSelectActivityLogicDefault implements MapSelectActivityLogic
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
class MapSelectActivityUIDefault implements MapSelectActivityUI
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

class BottomSheetLogicDefault implements BottomSheetLogic
{
	private BottomSheetUI mResponder = null;
	BottomSheetLogicDefault(BottomSheetUI responder) {
		mResponder = responder;
	}

	public void getBeaconInfo(int beaconID) { mResponder.beaconInfoRespond("This is the exit"); }//Example text
}
class BottomSheetUIDefault implements BottomSheetUI
{
	private BottomSheetLogicDefault mIfc = null;
	BottomSheetUIDefault() {
		mIfc = new BottomSheetLogicDefault(this);
	}

	public void beaconInfoRespond(String info) {}
}

class NavigationDrawerLogicDefault implements NavigationDrawerLogic
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
class NavigationDrawerUIDefault implements NavigationDrawerUI
{
	private NavigationDrawerLogicDefault mIfc = null;
	NavigationDrawerUIDefault() {
		mIfc = new NavigationDrawerLogicDefault(this);
	}

	public void searchResults(List<BeaconData> results) {}
}

class QrCodeReaderLogicDefault implements QrCodeReaderLogic
{
	private QrCodeReaderUI mResponder = null;
	QrCodeReaderLogicDefault(QrCodeReaderUI responder) {
		mResponder = responder;
	}

	public void takePicture() { mResponder.capturedMapID(1); }
}
class QrCodeReaderUIDefault implements QrCodeReaderUI
{
	private QrCodeReaderLogicDefault mIfc = null;
	QrCodeReaderUIDefault() {
		mIfc = new QrCodeReaderLogicDefault(this);
	}

	public void capturedMapID(int mapID) {}
}

class AdminAreaLogicDefault implements AdminAreaLogic
{
	private AdminAreaUI mResponder = null;
	AdminAreaLogicDefault(AdminAreaUI responder) {
		mResponder = responder;
	}

	public void loadMap(int mapID) { mResponder.updateMap(new MapDataDefault()); }

	public void configureBeacon(int beaconID) { mResponder.beaconSuccessfullyConfigured(); }
}
class AdminAreaUIDefault implements AdminAreaUI
{
	private AdminAreaLogicDefault mIfc = null;
	AdminAreaUIDefault() {
		mIfc = new AdminAreaLogicDefault(this);
	}

	public void updateMap(MapData map) {}
	public void invalidMapID() {}
	public void downloadFailed(String errorcode) {}
	public void isOffline() {}

	public void beaconSuccessfullyConfigured() {}
	public void beaconConfigurationFailed(String errorcode) {}
}
