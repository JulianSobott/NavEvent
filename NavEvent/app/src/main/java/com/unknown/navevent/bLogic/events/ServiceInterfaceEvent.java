package com.unknown.navevent.bLogic.events;

public class ServiceInterfaceEvent {
	public static final int EVENT_BEACON_SERVICE_STARTED = 1;
	public static final int EVENT_MAP_SERVICE_STARTED = 2;

	public final int message;

	public ServiceInterfaceEvent(int message) {
		this.message = message;
	}

}
