package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.QrCodeReaderUI;

public class QrCodeReaderUIDefault implements QrCodeReaderUI
{
	private QrCodeReaderLogicDefault mIfc = null;
	QrCodeReaderUIDefault() {
		mIfc = new QrCodeReaderLogicDefault(this);
	}

	public void capturedMapID(int mapID) {}
}
