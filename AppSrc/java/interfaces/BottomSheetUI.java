package com.unknown.naveventinterfacetest.interfaces;

//Interface for responses to the UI. Has to be implemented by BottomSheet-class.
public interface BottomSheetUI
{
	//Repond to BottomSheetLogic.getBeaconInfo(). \p info will contain the data.
	void beaconInfoRespond(String info);
}
