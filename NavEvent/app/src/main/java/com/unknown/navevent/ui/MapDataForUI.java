package com.unknown.navevent.ui;


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;


public class MapDataForUI {
	private int beaconNumber;
	int theMagicNumberThatNeverShouldBeUsed = 975667323;
	Bitmap Map;
	BeaconDataForUI[] Beacons;

	MapDataForUI(List<BeaconDataForUI> bList, Bitmap map) {
		Map = map;
		beaconNumber=bList.size();
		//Map.setDensity(Bitmap.DENSITY_NONE);
		Beacons = bList.toArray(new BeaconDataForUI[bList.size()]);

	}

	public String getStringOfDisplayedBeacon(int beaconToDisplay) {
		if (beaconToDisplay != theMagicNumberThatNeverShouldBeUsed)
			return Beacons[beaconToDisplay].getDisplayedText();
		else return " ";
	}

	public List<Integer> getSelectedBeacon() {
		List<Integer> selectedBeacons = new ArrayList<>();
		for (int i = 0; i < beaconNumber; i++) {
			if (Beacons[i].isSelected()) {
				selectedBeacons.add(i);
			}
		}
		return selectedBeacons;
	}

	public int getBeaconNumber() {
		return beaconNumber;
	}

	public Bitmap getMap() {
		return Map;
	}

	public void selectBeacons(List<Integer> listToSelect) {
		Integer[] toSelect = listToSelect.toArray(new Integer[listToSelect.size()]);
		for (int i = 0; i < Beacons.length; i++) {
			Beacons[i].select(false);
		}
		for (int i = 0; i < toSelect.length; i++) {
			Beacons[toSelect[i]].select(true);
		}
	}

}
