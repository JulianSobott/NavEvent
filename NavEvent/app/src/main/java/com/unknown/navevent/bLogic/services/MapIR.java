package com.unknown.navevent.bLogic.services;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Intern(beacon-logic) representation of a map
public class MapIR {
	public String name;//Map name
	public int id;//Map id
	public String description;//Text to describe the map

	public String imagePath;//Path to the image-file
	public Bitmap image;//Image of this map

	public int majorID;//Major beaconID corresponding to this map
	public SparseArray<MapBeaconIR> beacons = new SparseArray<>();//Set of Beacons on this map, mapped to their ids.
	public SparseIntArray beaconMap = new SparseIntArray();//Mapping of minorIDs to beaconIDs in this map.

	public Map<String, List<Integer>> ordinaryPlaces = new HashMap<>();//E. g. exit(s), toilet(s). Each label can occur multiple times.
	public Map<String, List<Integer>> specialPlaces = new HashMap<>();//E. g. special presentation rooms. Each label can occur multiple times.
}
