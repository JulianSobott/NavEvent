package com.unknown.navevent.bLogic.events;

public class LogicIfcBaseEvent {
	public static final int EVENT_SERVICE_STARTED = 1;

	public final int message;

	public LogicIfcBaseEvent(int message) {
		this.message = message;
	}

}
