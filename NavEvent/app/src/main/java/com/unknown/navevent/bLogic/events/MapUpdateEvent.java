package com.unknown.navevent.bLogic.events;


import com.unknown.navevent.bLogic.services.MapIR;

import java.util.List;

public class MapUpdateEvent {
	public static final int EVENT_MAP_LOADED = 1;
	public static final int EVENT_AVAIL_OFFLINE_MAPS_LOADED = 2;

	public final int message;
	public List<MapIR> maps;

	public MapUpdateEvent(int message) {
		this.message = message;
	}
	public MapUpdateEvent(int message, List<MapIR> maps) {
		this.message = message;
		this.maps = maps;
	}
}
