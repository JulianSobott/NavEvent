package com.unknown.naveventinterfacetest.interfaces;

//Interface for the BottomSheet-Logic
public interface BottomSheetLogic
{
	//Get the information of the beacon specified by beaconID. Responses in BottomSheetUI.beaconInfoRespond().
	void getBeaconInfo(int beaconID);
}
