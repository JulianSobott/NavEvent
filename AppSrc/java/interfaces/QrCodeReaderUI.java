package com.unknown.naveventinterfacetest.interfaces;

//Interface for responses to the UI. Has to be implemented by QrCodeReader-class.
public interface QrCodeReaderUI
{
	//Respond to QrCodeReaderLogic.capturedMapID(). \p mapID is the id which was generated from the qr-code
	void capturedMapID(int mapID);
}
