package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.BottomSheetLogic;
import com.unknown.naveventinterfacetest.interfaces.BottomSheetUI;

public class BottomSheetLogicDefault implements BottomSheetLogic
{
	private BottomSheetUI mResponder = null;
	BottomSheetLogicDefault(BottomSheetUI responder) {
		mResponder = responder;
	}

	public void getBeaconInfo(int beaconID) { mResponder.beaconInfoRespond("This is the exit"); }//Example text
}
