package com.unknown.navevent.bLogic.services;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.MapData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Intern(beacon-logic) representation of a map
public class MapIR implements MapData {
	String name;//Map name
	int id;//Map id
	int majorID;//Major beaconID corresponding to this map
	String description;//Text to describe the map

	String imagePath;//Path to the image-file
	Bitmap image;//Image of this map

	SparseArray<MapBeaconIR> beacons = new SparseArray<>();//Set of Beacons on this map, mapped to their ids.
	SparseIntArray beaconMap = new SparseIntArray();//Mapping of minorIDs to beaconIDs in this map.

	Map<String, List<Integer>> ordinaryPlaces = new HashMap<>();//E. g. exit(s), toilet(s). Each label can occur multiple times.
	Map<String, List<Integer>> specialPlaces = new HashMap<>();//E. g. special presentation rooms. Each label can occur multiple times.


	MapIR() {

	}
	MapIR( String name, int id, int majorID ) {
		this.name = name;
		this.id = id;
		this.majorID = majorID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getID() {
		return id;
	}

	public int getMajorID(){
		return majorID;
	}

	@Override
	public Bitmap getImage() {
		return image;
	}
	@Override
	public List<BeaconData> getBeacons() {
		List<BeaconData> newList = new ArrayList<>();
		for( int i = 0 ; i < beacons.size() ; i++ ) newList.add( beacons.valueAt(i) );
		return newList;
	}
	public SparseArray<MapBeaconIR> getBeaconsIR() {
		return beacons;
	}
}
