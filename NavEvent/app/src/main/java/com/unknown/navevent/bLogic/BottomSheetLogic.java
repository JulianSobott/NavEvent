package com.unknown.navevent.bLogic;

import com.unknown.navevent.interfaces.BottomSheetLogicInterface;
import com.unknown.navevent.interfaces.BottomSheetUI;

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

	}

	@Override
	public void onDestroy() {

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

}
