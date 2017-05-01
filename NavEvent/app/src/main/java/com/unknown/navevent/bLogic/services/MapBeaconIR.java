package com.unknown.navevent.bLogic.services;

import com.unknown.navevent.interfaces.BeaconData;

//Intern(beacon-logic) representation of a beacon on a map
public class MapBeaconIR implements BeaconData {
	public String name;//Name of this Beacon
	public int id;//Beacon id. Not major/minor id!

	public int minorID;//Major beaconID corresponding to the map.

	public double positionX;//X-position on the map
	public double positionY;//Y-position on the map

	public String description;//Text to give additional information about this beacon. Appears in BottomSheet

	public MapBeaconIR( String name, int id, int minorID, double positionX, double positionY, String description ) {
		this.name = name;
		this.id = id;
		this.minorID = minorID;
		this.positionX = positionX;
		this.positionY = positionY;
		this.description = description;
	}
	public MapBeaconIR() {

	}

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
