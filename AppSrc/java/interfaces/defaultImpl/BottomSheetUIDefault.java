package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.BottomSheetUI;

public class BottomSheetUIDefault implements BottomSheetUI
{
	private BottomSheetLogicDefault mIfc = null;
	BottomSheetUIDefault() {
		mIfc = new BottomSheetLogicDefault(this);
	}

	public void beaconInfoRespond(String info) {}
}
