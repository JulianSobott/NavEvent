package com.unknown.navevent.bLogic.events;

import com.unknown.navevent.bLogic.services.MapIR;

//Events to MapService
public class MapServiceEvent {
	public enum Type {
		EVENT_LOAD_MAP_LOCAL,
		EVENT_SAVE_MAP_LOCAL,
		EVENT_DOWNLOAD_MAP,
		EVENT_GET_ALL_LOCAL_MAPS,
		EVENT_FIND_ONLINE_MAP_BY_QUERY,//Search for map with a query string
		EVENT_FIND_ONLINE_MAP_BY_ID,//Search for map by id
		EVENT_STOP_SELF,
	}

	public final Type task;
	public MapIR map;
	public String query;//Search query
	public int mapID;

	public MapServiceEvent(Type task) {
		this.task = task;
	}
	public MapServiceEvent(Type task, MapIR map) {
		this.task = task;
		this.map = map;
	}
	public MapServiceEvent(Type task, String query) {
		this.task = task;
		this.query = query;
	}
	public MapServiceEvent(Type task, int mapID) {
		this.task = task;
		this.mapID = mapID;
	}
}
