package com.unknown.navevent.bLogic.services;

import com.unknown.navevent.interfaces.BeaconData;

//Intern(beacon-logic) representation of a beacon on a map
public class MapBeaconIR implements BeaconData {
	public String name;//Name of this Beacon
	public int id;//Beacon id. Not major/minor id!

	public int minorID;//Major beaconID corresponding to the map.

	double positionX;//X-position on the map
	double positionY;//Y-position on the map

	public String description;//Text to give additional information about this beacon. Appears in BottomSheet

	MapBeaconIR() {}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getMapPositionX() {
		return positionX;
	}

	@Override
	public double getMapPositionY() {
		return positionY;
	}
}
