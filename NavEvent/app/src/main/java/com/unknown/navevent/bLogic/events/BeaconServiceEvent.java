package com.unknown.navevent.bLogic.events;


import android.support.annotation.RequiresPermission;

//Events to BeaconService
public class BeaconServiceEvent {
	public static final int EVENT_START_LISTENING = 1;
	public static final int EVENT_STOP_LISTENING =2;
	public static final int EVENT_STOP_SELF= 3;
	public static final int EVENT_CONFIG_DEVICE= 4;

	public final int message;
	public static class WriteBeaconData {//for EVENT_CONFIG_DEVICE
		public int writeMajor;
		public int writeMinor;
		public WriteBeaconData( int majorID, int minorID ) {
			writeMajor = majorID;
			writeMinor = minorID;
		}
	}
	public WriteBeaconData writeBeaconData;

	public BeaconServiceEvent(int message) {
		this.message = message;
	}

	public BeaconServiceEvent(int message, WriteBeaconData writeBeaconData) {
		this.message = message;
		this.writeBeaconData = writeBeaconData;
	}

}
