package com.unknown.navevent.bLogic.events;

//Message event to MapService
public class MapServiceEvent {
	public static final int EVENT_STOP_SELF = 1;
	public static final int EVENT_RELOAD_LOCAL_MAPS = 2;

	public final int message;

	public MapServiceEvent(int message) {
		this.message = message;
	}
}
