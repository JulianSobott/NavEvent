package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.BottomSheetUI;

public class BottomSheetUIDefault implements BottomSheetUI
{
	private BottomSheetLogicDefault mIfc = null;
	BottomSheetUIDefault() {
		mIfc = new BottomSheetLogicDefault(this);
	}

	public void beaconInfoRespond(String info) {}
}
