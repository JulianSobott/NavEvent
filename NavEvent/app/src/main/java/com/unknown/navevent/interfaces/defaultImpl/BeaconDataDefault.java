package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.BeaconData;

public class BeaconDataDefault implements BeaconData
{
	public int getId() { return 0; }

	public String getName() { return "Exit"; } //Example name.

	public double getMapPositionX() { return 0; }
	public double getMapPositionY() { return 0; }
}
