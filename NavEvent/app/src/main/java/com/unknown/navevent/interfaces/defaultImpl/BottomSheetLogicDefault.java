package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.BottomSheetLogicInterface;
import com.unknown.navevent.interfaces.BottomSheetUI;

public class BottomSheetLogicDefault implements BottomSheetLogicInterface
{
	private BottomSheetUI mResponder = null;
	BottomSheetLogicDefault(BottomSheetUI responder) {
		mResponder = responder;
	}

	public void getBeaconInfo(int beaconID) { mResponder.beaconInfoRespond("This is the exit"); }//Example text
}
