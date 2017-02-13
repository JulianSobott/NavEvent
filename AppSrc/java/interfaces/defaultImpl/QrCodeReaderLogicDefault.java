package com.unknown.naveventinterfacetest.interfaces.defaultImpl;

import com.unknown.naveventinterfacetest.interfaces.QrCodeReaderLogic;
import com.unknown.naveventinterfacetest.interfaces.QrCodeReaderUI;

public class QrCodeReaderLogicDefault implements QrCodeReaderLogic
{
	private QrCodeReaderUI mResponder = null;
	QrCodeReaderLogicDefault(QrCodeReaderUI responder) {
		mResponder = responder;
	}

	public void takePicture() { mResponder.capturedMapID(1); }
}
