package com.unknown.naveventinterfacetest.interfaces;

//Interface for the QrCodeReader-Logic
public interface QrCodeReaderLogic
{
	//Will start camera, take a picture and generate the map-id/-link. Will respond with QrCodeReaderUI.capturedMapID().
	void takePicture();
}
