package com.unknown.navevent.bLogic.services;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.MapData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Intern(beacon-logic) representation of a map
public class MapIR implements MapData {
	public String name;//Map name
	public int id;//Map id
	public int majorID;//Major beaconID corresponding to this map
	public String description;//Text to describe the map

	public String imagePath;//Path to the image-file
	public Bitmap image;//Image of this map

	public SparseArray<MapBeaconIR> beacons = new SparseArray<>();//Set of Beacons on this map, mapped to their ids.
	public SparseIntArray beaconMap = new SparseIntArray();//Mapping of minorIDs to beaconIDs in this map.

	public Map<String, List<Integer>> ordinaryPlaces = new HashMap<>();//E. g. exit(s), toilet(s). Each label can occur multiple times.
	public Map<String, List<Integer>> specialPlaces = new HashMap<>();//E. g. special presentation rooms. Each label can occur multiple times.


	public MapIR() {

	}
	public MapIR( String name, int id, int majorID ) {
		this.name = name;
		this.id = id;
		this.majorID = majorID;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public Bitmap getImage() {
		return image;
	}
	@Override
	public List<BeaconData> getBeacons() {
		return null;//todo
	}
}
