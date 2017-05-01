package com.unknown.navevent.bLogic.events;

import com.unknown.navevent.bLogic.services.MapIR;

//Events to MapService
public class MapServiceEvent {
	public static final int EVENT_LOAD_MAP_LOCAL = 1;
	public static final int EVENT_SAVE_MAP_LOCAL = 2;
	public static final int EVENT_DOWNLOAD_MAP = 3;
	public static final int EVENT_GET_ALL_LOCAL_MAPS = 4;
	public static final int EVENT_FIND_ONLINE_MAP_BY_QUERY = 5;//Search for map with a query string
	public static final int EVENT_FIND_ONLINE_MAP_BY_ID = 6;//Search for map by id
	public static final int EVENT_STOP_SELF = 7;

	public final int task;
	public MapIR map;
	public String query;//Search query
	public int mapID;

	public MapServiceEvent(int task) {
		this.task = task;
	}
	public MapServiceEvent(int task, MapIR map) {
		this.task = task;
		this.map = map;
	}
	public MapServiceEvent(int task, String query) {
		this.task = task;
		this.query = query;
	}
	public MapServiceEvent(int task, int mapID) {
		this.task = task;
		this.mapID = mapID;
	}
}
