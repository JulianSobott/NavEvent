package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.MainActivityUI;
import com.unknown.naveventinterfacetest.interfaces.MapData;

import java.util.List;

public class MainActivityUIDefault implements MainActivityUI
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
