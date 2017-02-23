package com.unknown.navevent.bLogic.events;

//Message event from a service to the current Activities
public class ServiceToActivityEvent {
	public static final int EVENT_LISTENER_STARTED = 1;
	public static final int EVENT_BLUETOOTH_DEACTIVATED = 2;
	public static final int EVENT_BLUETOOTH_NOT_SUPPORTED = 3;
	public static final int EVENT_BLE_NOT_SUPPORTED = 4;//Bluetooth Low Energy
	public static final int EVENT_ASK_PERMISSION = 5;
	public static final int EVENT_CURRENT_MAP_UNLOADED = 6;
	public static final int EVENT_NO_LOCAL_MAPS_AVAILABLE = 7;


	public final int message;

	public ServiceToActivityEvent(int message) {
		this.message = message;
	}
}
