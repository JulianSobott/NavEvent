package com.unknown.navevent.ui;


import android.graphics.Bitmap;

import java.util.List;


//This class wll be obsolete in the final Version and only is for testing purposes, should not be used due to that /\ well it isnt so this name is wrong but im too lazy to change ^^
public class MapForTests {
    private int beaconNumber;
    int theMagicNumberThatNeverShouldBeUsed=975667323;
    Bitmap Map;
    BeaconForTests[] Beacons;
    MapForTests(List<BeaconForTests> bList, Bitmap map, int NR){
        Map=map;
        Beacons=bList.toArray(new BeaconForTests[NR]);
        beaconNumber=NR;

    }
    public String getStringOfDisplayedBeacon(int beaconToDisplay){
        if(beaconToDisplay!=theMagicNumberThatNeverShouldBeUsed)
        return Beacons[beaconToDisplay].getDisplayedText();
        else return " ";
    }
    public int getSelectedBeacon(){
        int selectedB=theMagicNumberThatNeverShouldBeUsed;
        for(int i=0;i<beaconNumber;i++){
            if (Beacons[i].isSelected()){
                selectedB=i;
            }
        }
        return selectedB;
    }
    public int getBeaconNumber(){
        return beaconNumber;
    }
    public Bitmap getMap(){
        return Map;
    }

}
