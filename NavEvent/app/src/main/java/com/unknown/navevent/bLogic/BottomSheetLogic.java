package com.unknown.navevent.bLogic;

import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.BottomSheetLogicInterface;
import com.unknown.navevent.interfaces.BottomSheetUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BottomSheetLogic implements BottomSheetLogicInterface {
	private static final String TAG = "BottomSheetLogic";


	private BottomSheetUI mResponder = null;
	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public BottomSheetLogic(BottomSheetUI responder) {
		mResponder = responder;
	}


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate() {
		EventBus.getDefault().register(this);

	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);

	}



	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public void getBeaconData(int beaconID) {
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded &&
				serviceInterface.currentMap.getBeaconsIR().get(beaconID) != null ) {//Fixes bug where this method is called with not existing beaconID.
			mResponder.beaconDataRespond(serviceInterface.currentMap.getBeaconsIR().get(beaconID).name, serviceInterface.currentMap.getBeaconsIR().get(beaconID).description);
		}
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
	}
}
