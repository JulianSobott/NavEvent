package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.QrCodeReaderUI;

public class QrCodeReaderUIDefault implements QrCodeReaderUI
{
	private QrCodeReaderLogicDefault mIfc = null;
	QrCodeReaderUIDefault() {
		mIfc = new QrCodeReaderLogicDefault(this);
	}

	public void capturedMapID(int mapID) {}
}
