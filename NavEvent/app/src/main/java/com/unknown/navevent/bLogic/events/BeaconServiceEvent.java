package com.unknown.navevent.bLogic.events;


import android.support.annotation.RequiresPermission;

//Events to BeaconService
public class BeaconServiceEvent {
	public enum Type {
		EVENT_START_LISTENING,
		EVENT_STOP_LISTENING,
		EVENT_STOP_SELF,
		EVENT_CONFIG_DEVICE,
	}

	public final Type message;
	public static class WriteBeaconData {//for EVENT_CONFIG_DEVICE
		public int writeMajor;
		public int writeMinor;
		public WriteBeaconData( int majorID, int minorID ) {
			writeMajor = majorID;
			writeMinor = minorID;
		}
	}
	public WriteBeaconData writeBeaconData;

	public BeaconServiceEvent(Type message) {
		this.message = message;
	}

	public BeaconServiceEvent(Type message, WriteBeaconData writeBeaconData) {
		this.message = message;
		this.writeBeaconData = writeBeaconData;
	}

}
