package com.unknown.navevent.bLogic.events;

public class ServiceToActivityEvent {
	public static final int EVENT_LISTENER_STARTED = 1;
	public static final int EVENT_BLUETOOTH_DEACTIVATED = 2;
	public static final int EVENT_BLUETOOTH_NOT_SUPPORTED = 3;
	public static final int EVENT_BLE_NOT_SUPPORTED = 4;//Bluetooth Low Energy
	public static final int EVENT_ASK_PERMISSION = 5;
	public static final int EVENT_NEW_MAP_LOADED = 6;
	public static final int EVENT_CURRENT_MAP_UNLOADED = 7;
	public static final int EVENT_NO_LOCAL_MAPS_AVAILABLE = 8;
	public static final int EVENT_MAP_DOWNLOADED = 9;
	public static final int EVENT_MAP_DOWNLOAD_FAILED = 10;
	public static final int EVENT_FOUND_ONLINE_MAPS = 11;
	public static final int EVENT_FOUND_CORRESPONDING_MAP = 12;//A map corresponding to the nearest beacon was found.
	public static final int EVENT_AVAIL_LOCAL_MAPS_UPDATED = 13;
	public static final int EVENT_BEACON_UPDATE = 14;
	public static final int EVENT_NO_BEACON_FOUND = 15;
	public static final int EVENT_NO_CORRESPONDING_MAPS_AVAILABLE = 16;

	public final int message;

	public ServiceToActivityEvent(int message) {
		this.message = message;
	}
}
