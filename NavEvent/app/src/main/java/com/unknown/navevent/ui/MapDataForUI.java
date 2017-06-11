package com.unknown.navevent.ui;


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;


public class MapDataForUI {
	private int beaconNumber;
	//private int theMagicNumberThatNeverShouldBeUsed = 975667323; todo del
	private Bitmap Map;
	BeaconDataForUI[] beacons;

	MapDataForUI(List<BeaconDataForUI> bList, Bitmap map) {
		Map = map;
		beaconNumber=bList.size();
		//Map.setDensity(Bitmap.DENSITY_NONE);      todo del
		beacons = bList.toArray(new BeaconDataForUI[bList.size()]);

	}

	public String getStringOfDisplayedBeacon(int beaconToDisplay) {
		String returnText=" ";
        for (int i=0;i<beaconNumber;i++) {
            if (beaconToDisplay==beacons[i].getID()) returnText=beacons[i].getDisplayedText();
        }
		return returnText;
	}

	/*public List<Integer> getSelectedBeacon() {            todo check if needed del if not
		List<Integer> selectedBeacons = new ArrayList<>();
		for (int i = 0; i < beaconNumber; i++) {
			if (beacons[i].isSelected()) {
				selectedBeacons.add(i);
			}
		}
		return selectedBeacons;
	}*/

	public int getBeaconNumber() {
		return beaconNumber;
	}

	public Bitmap getMap() {
		return Map;
	}

	public void selectBeacons(List<Integer> listToSelect) {		//resets the selectedstate of all beacons and selects the ones given
		Integer[] toSelect = listToSelect.toArray(new Integer[listToSelect.size()]);
		for (int i = 0; i < beacons.length; i++) {
			beacons[i].select(false);
		}
		for (int i = 0; i < toSelect.length; i++) {
            for (int j = 0; j < toSelect.length; j++) {
                if (beacons[i].getID() == toSelect[j]) beacons[i].select(true);
            }
		}
	}
	public void setClosestBeacon(int closestBeaconID){			//resets all beacons to not be the closest than sets the given beacons as closest
		for (BeaconDataForUI beacon: beacons) {
			beacon.setClosest(false);
		}
		for(int i=0;i<beaconNumber;i++){
			if(beacons[i].getID()==closestBeaconID) beacons[i].setClosest(true);
		}
	}

}
