package com.unknown.navevent.interfaces;

import android.graphics.Bitmap;

import java.util.List;

//General interface to get map-data.
public interface MapData
{
	//Returns the human-readable name of the Map.
	String getName();

	//Returns the image which represents the map.
	Bitmap getImage();

	//Returns all beacons on the map.
	List<BeaconData> getBeacons();
}
