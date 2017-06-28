package com.unknown.navevent.interfaces;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

//General interface to get map-data.
public interface MapData
{
	//Returns the human-readable name of the Map.
	String getName();

	//Returns the description of this map.
	String getDescription();

	//Returns the id of the Map.
	int getID();

	//Returns the image which represents the map.
	Bitmap getImage();

	//Returns all beacons on the map.
	List<BeaconData> getBeacons();

	//Returns the lists of beacon-ids mapped to their group-name
	Map<String, List<Integer>> getOrdinaryPlaces();

	//Returns the lists of beacon-ids mapped to their group-name
	Map<String, List<Integer>> getSpecialPlaces();
}