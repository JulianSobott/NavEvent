package com.unknown.navevent.bLogic.events;

//Transmits information to the UI-classes
public class ServiceToActivityEvent {
	public enum Type {
		EVENT_BEACON_LISTENER_STARTED,
		EVENT_BLUETOOTH_DEACTIVATED,//And should be activated
		EVENT_BLUETOOTH_NOT_SUPPORTED,
		EVENT_BLE_NOT_SUPPORTED,//Bluetooth Low Energy
		EVENT_ASK_PERMISSION,//Permission for coarse location
		EVENT_NEW_MAP_LOADED,
		EVENT_CURRENT_MAP_UNLOADED,
		EVENT_NO_LOCAL_MAPS_AVAILABLE,
		EVENT_MAP_DOWNLOADED,
		EVENT_MAP_DOWNLOAD_FAILED,
		EVENT_FOUND_ONLINE_MAPS,
		EVENT_AVAIL_LOCAL_MAPS_UPDATED,//List of local saved maps has been updated
		EVENT_BEACON_UPDATE,//the nearest beacon has changed
		EVENT_NO_BEACON_FOUND,
		EVENT_NO_CORRESPONDING_MAPS_AVAILABLE,//No map was found for a nearby beacon. Will be called a few seconds after the start.
		EVENT_MARK_BEACONS,//Some beacons should be marked in the MainActivity
		EVENT_BEACON_CONFIG_SUCCESSFUL,
		EVENT_BEACON_CONFIG_FAILED,
	}


	public final Type message;
	public String additionalInfo;//E. g. the error description.

	public ServiceToActivityEvent(Type message) {
		this.message = message;
	}
	public ServiceToActivityEvent(Type message, String additionalInfo) {
		this.message = message;
		this.additionalInfo = additionalInfo;
	}
}
