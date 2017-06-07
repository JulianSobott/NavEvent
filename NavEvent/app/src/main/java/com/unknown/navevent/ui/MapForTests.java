package com.unknown.navevent.ui;


import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;


//This class wll be obsolete in the final Version and only is for testing purposes, should not be used due to that /\ well it isnt so this name is wrong but im too lazy to change ^^
public class MapForTests {
	private int beaconNumber;
	int theMagicNumberThatNeverShouldBeUsed = 975667323;
	Bitmap Map;
	BeaconForTests[] Beacons;

	MapForTests(List<BeaconForTests> bList, Bitmap map, int NR) {
		Map = map;
		//Map.setDensity(Bitmap.DENSITY_NONE);
		Beacons = bList.toArray(new BeaconForTests[NR]);
		beaconNumber = NR;

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
