package com.unknown.navevent.interfaces;

//Interface for responses to the UI. Has to be implemented by BottomSheet-class.
public interface BottomSheetUI
{
	//Respond to BottomSheetLogicInterface.getBeaconName(). \p info will contain the name.
	void beaconNameRespond(String name);

	//Respond to BottomSheetLogicInterface.getBeaconInfo(). \p info will contain the data.
	void beaconInfoRespond(String info);
}
