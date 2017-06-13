package com.unknown.navevent.interfaces;

//Interface for the BottomSheet-Logic
public interface BottomSheetLogicInterface
{
	//Should be called on creation.
	void onCreate();
	//Should be called on destruction.
	void onDestroy();

	//Get the name & info of the beacon specified by beaconID. Responses in BottomSheetUI.beaconNameData().
	void getBeaconData(int beaconID);
}
