package com.unknown.navevent.bLogic.events;


import com.unknown.navevent.bLogic.services.MapIR;

import java.util.List;

//Transmits any map state change
public class MapUpdateEvent {
	public enum Type {
		EVENT_MAP_LOADED,
		EVENT_AVAIL_OFFLINE_MAPS_LOADED,
		EVENT_FOUND_ONLINE_MAPS,
		EVENT_FOUND_ONLINE_MAP_BY_ID,
		EVENT_MAP_DOWNLOADED,
		EVENT_MAP_DOWNLOAD_FAILED,
	}

	public Type message;
	public List<MapIR> maps;
	public String additionalData;//E. g. the error description.

	public MapUpdateEvent(Type message, List<MapIR> maps) {
		this.message = message;
		this.maps = maps;
	}
	public MapUpdateEvent(Type message, String additionalData) {
		this.message = message;
		this.additionalData = additionalData;
	}
}
