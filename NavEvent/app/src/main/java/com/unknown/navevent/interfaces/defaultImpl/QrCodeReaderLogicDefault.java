package com.unknown.navevent.interfaces.defaultImpl;

import com.unknown.navevent.interfaces.QrCodeReaderLogicInterface;
import com.unknown.navevent.interfaces.QrCodeReaderUI;

public class QrCodeReaderLogicDefault implements QrCodeReaderLogicInterface
{
	private QrCodeReaderUI mResponder = null;
	QrCodeReaderLogicDefault(QrCodeReaderUI responder) {
		mResponder = responder;
	}

	public void takePicture() { mResponder.capturedMapID(1); }
}
