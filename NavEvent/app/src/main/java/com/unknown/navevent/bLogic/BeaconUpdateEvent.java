package com.unknown.navevent.bLogic;

import com.unknown.navevent.bLogic.BeaconIR;

import java.util.List;

public class BeaconUpdateEvent {
	public static final int EVENT_BEACON_UPDATE = 1;

	public final int message;
	public final List<BeaconIR> beacons;

	public BeaconUpdateEvent(int message, List<BeaconIR> beacons) {
		this.message = message;
		this.beacons = beacons;
	}
}
