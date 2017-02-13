package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.BeaconData;

public class BeaconDataDefault implements BeaconData
{
	public int getId() { return 0; }

	public String getName() { return "Exit"; } //Example name.

	public double getMapPositionX() { return 0; }
	public double getMapPositionY() { return 0; }
}
