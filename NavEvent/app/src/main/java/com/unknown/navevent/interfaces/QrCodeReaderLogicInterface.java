package com.unknown.navevent.interfaces;

import android.content.Context;

//Interface for the QrCodeReader-Logic
public interface QrCodeReaderLogicInterface
{
	//Should be called on creation.
	void onCreate(Context context);
	//Should be called on destruction.
	void onDestroy();

	//Will start camera, take a picture and generate the map-id/-link. Will respond with QrCodeReaderUI.capturedMapID().
	void takePicture();
}
