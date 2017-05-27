package com.unknown.navevent.bLogic;

import android.content.Context;

import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.interfaces.NavigationDrawerUI;
import com.unknown.navevent.interfaces.QrCodeReaderLogicInterface;
import com.unknown.navevent.interfaces.QrCodeReaderUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class QrCodeReaderLogic implements QrCodeReaderLogicInterface {
	private static final String TAG = "QrCodeReaderLogic";


	private QrCodeReaderUI mResponder = null;
	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public QrCodeReaderLogic(QrCodeReaderUI responder) {
		mResponder = responder;
	}


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate(Context context) {
		EventBus.getDefault().register(this);

		serviceInterface.onCreate(context);

	}

	@Override
	public void onDestroy() {
		serviceInterface.onDestroy();

		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public void takePicture() {
		//todo
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
		/*if( event.message == ServiceToActivityEvent.EVENT_NEW_MAP_LOADED) { todo change
			Log.i(TAG, "onMessageEvent: EVENT_NEW_MAP_LOADED");

			mResponder.updateMap(serviceInterface.currentMap);
		}
		else if( event.message == ServiceToActivityEvent.EVENT_MAP_DOWNLOADED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOADED");

			Toast.makeText(serviceInterface.mContext, "Map '"+serviceInterface.lastDownloadedMap.getName()+"' downloaded.", Toast.LENGTH_SHORT).show();
			//todo load map
		}
		else if( event.message == ServiceToActivityEvent.EVENT_MAP_DOWNLOAD_FAILED) {
			Log.i(TAG, "onMessageEvent: EVENT_MAP_DOWNLOAD_FAILED");
			mResponder.downloadFailed("Failed to download map!");//todo change string
		}*/
	}
}
