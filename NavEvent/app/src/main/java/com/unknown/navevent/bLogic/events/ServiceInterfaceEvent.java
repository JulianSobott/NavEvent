package com.unknown.navevent.bLogic.events;

public class ServiceInterfaceEvent {
	public enum Type {
		EVENT_BEACON_SERVICE_STARTED,
		EVENT_MAP_SERVICE_STARTED,
	}

	public final Type message;

	public ServiceInterfaceEvent(Type message) {
		this.message = message;
	}

}
