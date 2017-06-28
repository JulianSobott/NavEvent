package com.unknown.navevent.bLogic.events;


//Events to BeaconService
public class BeaconServiceEvent {
	public enum Type {
		EVENT_START_LISTENING,
		EVENT_STOP_LISTENING,
		EVENT_STOP_SELF,//Beacon service should be stopped
		EVENT_CONFIG_DEVICE,//A beacon should be configured
	}

	public final Type message;
	public static class WriteBeaconData {//Contains additional data for EVENT_CONFIG_DEVICE
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
