package com.unknown.navevent.interfaces;

//Interface for the BottomSheet-Logic
public interface BottomSheetLogicInterface
{
	//Get the information of the beacon specified by beaconID. Responses in BottomSheetUI.beaconInfoRespond().
	void getBeaconInfo(int beaconID);
}
