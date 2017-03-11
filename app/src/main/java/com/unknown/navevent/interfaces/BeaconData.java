package com.unknown.navevent.interfaces;

//General interface to get beacon-data.
public interface BeaconData
{
	//Returns beaconID on the map.
	int getId();

	//Returns the human-readable name of the beacon.
	String getName();

	//Return the x-position on the map.
	double getMapPositionX();
	//Return the y-position on the map.
	double getMapPositionY();
}