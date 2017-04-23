package com.unknown.navevent.bLogic;

import android.graphics.Bitmap;

import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.MapData;

import java.util.List;

public class MapDataImpl implements MapData {
	String name;
	Bitmap bitmap;
	List<BeaconData> beaons;
	int id;//Not major id!

	public MapDataImpl( String name, int id ) {
		this.name = name;
		this.id = id;
	}


	@Override
	public String getName() {
		return name;
	}
	@Override
	public Bitmap getImage() {
		return bitmap;
	}
	@Override
	public List<BeaconData> getBeacons() {
		return beaons;
	}
}
