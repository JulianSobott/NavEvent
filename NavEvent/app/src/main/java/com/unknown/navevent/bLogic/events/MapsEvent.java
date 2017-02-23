package com.unknown.navevent.bLogic.events;

import com.unknown.navevent.bLogic.MapIR;

import java.util.List;

//Event to ServiceInterface to update map information
public class MapsEvent {
	public static final int EVENT_LOCAL_RELOADED = 1;

	public final int message;
	public final List<MapIR> maps;

	public MapsEvent(int message, List<MapIR> maps) {
		this.message = message;
		this.maps = maps;
	}
}
