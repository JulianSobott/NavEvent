package com.unknown.navevent.bLogic;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;

import java.util.List;
import java.util.Map;

//Intern(beacon-logic) representation of a map
class MapIR {
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
}
//Intern(beacon-logic) representation of a beacon on a map
class MapBeaconIR {
	public String name;//Name of this Beacon
	public int id;//Beacon id. Not major/minor id!

	public int minorID;//Major beaconID corresponding to the map.

	public double positionX;//X-position on the map
	public double positionY;//Y-position on the map

	public String description;//Text to give additional information about this beacon. Appears in BottomSheet
}

public class MapService extends Service {
	public MapService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
