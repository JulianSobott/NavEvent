package com.unknown.navevent.bLogic;

import android.graphics.Bitmap;

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
	public Map<Integer, MapBeaconIR> beacons;//Set of Beacons on this map, mapped to their ids.
	public Map<Integer, Integer> beaconMap;//Mapping of minorIDs to beaconIDs in this map.

	public Map<String, List<Integer>> ordinaryPlaces;//E. g. exit(s), toilet(s). Each label can occur multiple times.
	public Map<String, List<Integer>> specialPlaces;//E. g. special presentation rooms. Each label can occur multiple times.

	MapIR() {
		beacons = new HashMap<>();
		beaconMap = new HashMap<>();
		ordinaryPlaces = new HashMap<>();
		specialPlaces = new HashMap<>();
	}
}
