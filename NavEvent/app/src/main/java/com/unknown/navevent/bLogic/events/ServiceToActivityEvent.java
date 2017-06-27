package com.unknown.navevent.bLogic.events;

public class ServiceToActivityEvent {
	public enum Type {
		EVENT_BEACON_LISTENER_STARTED,
		EVENT_BLUETOOTH_DEACTIVATED,
		EVENT_BLUETOOTH_NOT_SUPPORTED,
		EVENT_BLE_NOT_SUPPORTED,//Bluetooth Low Energy
		EVENT_ASK_PERMISSION,
		EVENT_NEW_MAP_LOADED,
		EVENT_CURRENT_MAP_UNLOADED,
		EVENT_NO_LOCAL_MAPS_AVAILABLE,
		EVENT_MAP_DOWNLOADED,
		EVENT_MAP_DOWNLOAD_FAILED,
		EVENT_FOUND_ONLINE_MAPS,
		EVENT_AVAIL_LOCAL_MAPS_UPDATED,
		EVENT_BEACON_UPDATE,
		EVENT_NO_BEACON_FOUND,
		EVENT_NO_CORRESPONDING_MAPS_AVAILABLE,
		EVENT_MARK_BEACONS,
		EVENT_BEACON_CONFIG_SUCCESSFUL,
		EVENT_BEACON_CONFIG_FAILED,
	}


	public final Type message;
	public String additionalInfo;

	public ServiceToActivityEvent(Type message) {
		this.message = message;
	}
	public ServiceToActivityEvent(Type message, String additionalInfo) {
		this.message = message;
		this.additionalInfo = additionalInfo;
	}
}
