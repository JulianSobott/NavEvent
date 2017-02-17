package com.unknown.navevent.bLogic.events;


public class BeaconServiceEvent {
	public static final int EVENT_START_LISTENING = 1;
	public static final int EVENT_STOP_SELF= 2;

	public final int message;

	public BeaconServiceEvent(int message) {
		this.message = message;
	}

}
