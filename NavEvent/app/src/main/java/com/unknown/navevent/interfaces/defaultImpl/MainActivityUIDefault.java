package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;

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

	public void switchToMapSelectActivity() {}

	public void updateMap(MapData map) {}

	public void updateBeaconPosition(int beaconID) {}

	public void markBeacons(List<Integer> beaconIDs) {}
}
