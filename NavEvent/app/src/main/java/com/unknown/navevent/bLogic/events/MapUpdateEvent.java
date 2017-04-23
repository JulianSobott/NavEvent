package com.unknown.navevent.bLogic.events;


import com.unknown.navevent.bLogic.MapDataImpl;

import java.util.List;

public class MapUpdateEvent {
	public static final int EVENT_MAP_LOADED = 1;
	public static final int EVENT_AVAIL_OFFLINE_MAPS_LOADED = 2;

	public final int message;
	public final List<MapDataImpl> maps;

	public MapUpdateEvent(int message, List<MapDataImpl> maps) {
		this.message = message;
		this.maps = maps;
	}
}
