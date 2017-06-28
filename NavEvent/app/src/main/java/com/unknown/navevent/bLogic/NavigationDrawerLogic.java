package com.unknown.navevent.bLogic;

import android.util.SparseArray;

import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.bLogic.services.MapBeaconIR;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerLogicInterface;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class NavigationDrawerLogic implements NavigationDrawerLogicInterface {
	private static final String TAG = "NavigationDrawerLogic";


	private NavigationDrawerUI mResponder = null;
	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public NavigationDrawerLogic(NavigationDrawerUI responder) {
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
	public List<String> getSpecialBeacons() {
		List<String> retList = new ArrayList<>();
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {//Only return the list if the map was loaded
			Map<String, List<Integer>> beacons = serviceInterface.currentMap.getSpecialPlaces();
			retList.addAll(beacons.keySet());
		}
		return retList;
	}

	@Override
	public List<String> getOrdinaryBeacons() {
		List<String> retList = new ArrayList<>();
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {//Only return the list if the map was loaded
			Map<String, List<Integer>> beacons = serviceInterface.currentMap.getOrdinaryPlaces();
			retList.addAll(beacons.keySet());
		}
		return retList;
	}

	@Override
	public void findAllSpecialBeacons(String name) {
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {
			Map<String, List<Integer>> beacons = serviceInterface.currentMap.getSpecialPlaces();

			//Select all beacons by name
			for( Map.Entry<String, List<Integer>> beacon : beacons.entrySet() ) {
				if( beacon.getKey().equals(name) ) { //Found beacon-type by name
					serviceInterface.markableBeacons = beacon.getValue();
					EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.Type.EVENT_MARK_BEACONS));
					break;
				}
			}
		}
	}

	@Override
	public void findAllOrdinaryBeacons(String name) {
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {
			Map<String, List<Integer>> beacons = serviceInterface.currentMap.getOrdinaryPlaces();

			//Select all beacons by name
			for( Map.Entry<String, List<Integer>> beacon : beacons.entrySet() ) {
				if( beacon.getKey().equals(name) ) { //Found beacon-type by name
					serviceInterface.markableBeacons = beacon.getValue();
					EventBus.getDefault().post(new ServiceToActivityEvent(ServiceToActivityEvent.Type.EVENT_MARK_BEACONS));
					break;
				}
			}
		}
	}

	@Override
	public void searchFor(String query) {
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {
			SparseArray<MapBeaconIR> beacons = serviceInterface.currentMap.getBeaconsIR();
			List<BeaconData> retBeacons = new LinkedList<>();
			String queryString = query.toLowerCase();//Use only lower case

			for( int i = 0 ; i < beacons.size() ; i++ ) {
				String name = beacons.valueAt(i).name.toLowerCase();//Use only lower case
				String description = beacons.valueAt(i).description.toLowerCase();

				if( name.contains(queryString) || description.contains(queryString) ) {//Searched string is in name or in description of the beacon.
					retBeacons.add(beacons.valueAt(i));
				}
			}

			mResponder.searchResults(retBeacons);
		}
	}
}
