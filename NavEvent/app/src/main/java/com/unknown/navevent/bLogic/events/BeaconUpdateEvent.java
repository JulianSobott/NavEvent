package com.unknown.navevent.bLogic.events;

import com.unknown.navevent.bLogic.services.BeaconIR;

import java.util.List;

public class BeaconUpdateEvent {
	public enum Type {
		EVENT_BEACON_UPDATE,
	}

	public final Type message;
	public final List<BeaconIR> beacons;

	public BeaconUpdateEvent(Type message, List<BeaconIR> beacons) {
		this.message = message;
		this.beacons = beacons;
	}
}
