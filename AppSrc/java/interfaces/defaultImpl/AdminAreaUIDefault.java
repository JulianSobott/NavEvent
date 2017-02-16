package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.*;

public class AdminAreaUIDefault implements AdminAreaUI
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
