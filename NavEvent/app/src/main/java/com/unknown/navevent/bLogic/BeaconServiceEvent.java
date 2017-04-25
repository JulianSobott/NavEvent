package com.unknown.navevent.bLogic;


public class BeaconServiceEvent {
	public static final int EVENT_START_LISTENING = 1;
	public static final int EVENT_STOP_LISTENING =21;
	public static final int EVENT_STOP_SELF= 3;

	public final int message;

	public BeaconServiceEvent(int message) {
		this.message = message;
	}

}
