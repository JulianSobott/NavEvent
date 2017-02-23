package com.unknown.navevent.bLogic.events;

import com.unknown.navevent.bLogic.MapIR;

import java.util.List;

//Message event from a service to the ServiceInterface
public class ServiceInterfaceEvent {
	public static final int EVENT_BEACON_SERVICE_STARTED = 1;
	public static final int EVENT_MAP_SERVICE_STARTED = 2;

	public final int message;

	public ServiceInterfaceEvent(int message) {
		this.message = message;
	}

}