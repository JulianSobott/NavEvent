package com.unknown.navevent.bLogic.events;

import com.unknown.navevent.bLogic.services.BeaconIR;

import java.util.List;

//This event notifies the ui-classes if beacon data changed.
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
