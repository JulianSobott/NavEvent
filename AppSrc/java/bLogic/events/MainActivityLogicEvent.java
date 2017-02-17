package com.unknown.navevent.bLogic.events;

public class MainActivityLogicEvent {
	public static final int EVENT_BLUETOOTH_DEACTIVATED = 1;
	public static final int EVENT_BLUETOOTH_NOT_SUPPORTED = 2;
	public static final int EVENT_ASK_PERMISSION = 3;

	public final int message;

	public MainActivityLogicEvent(int message) {
		this.message = message;
	}
}
