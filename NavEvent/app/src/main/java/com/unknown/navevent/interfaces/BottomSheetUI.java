package com.unknown.navevent.interfaces;

//Interface for responses to the UI. Has to be implemented by BottomSheet-class.
public interface BottomSheetUI
{
	//Respond to BottomSheetLogicInterface.getBeaconData(). \p info will contain the name. \p info will contain the data.
	void beaconDataRespond(String name, String info);
}
