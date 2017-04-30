package com.unknown.navevent.interfaces.defaultImpl;

import android.graphics.Bitmap;

import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.MapData;

import java.util.ArrayList;
import java.util.List;

public class MapDataDefault implements MapData
{
	public String getName() { return "DefaultMap"; }

	@Override
	public int getID() {
		return 1;
	}

	public Bitmap getImage() { return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888); } //Not a fixed size!

	public List<BeaconData> getBeacons() {
		List<BeaconData> b = new ArrayList<BeaconData>();
		b.add(new BeaconDataDefault());
		return b;
	}
}
