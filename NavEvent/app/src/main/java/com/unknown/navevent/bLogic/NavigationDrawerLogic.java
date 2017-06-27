package com.unknown.navevent.bLogic;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.bLogic.services.MapBeaconIR;
import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerLogicInterface;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import org.altbeacon.beacon.Beacon;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
	public List<String> getSpecialBeacons() {
		List<String> retList = new ArrayList<>();
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {
			Map<String, List<Integer>> beacons = serviceInterface.currentMap.getSpecialPlaces();
			retList.addAll(beacons.keySet());
		}
		return retList;
	}

	@Override
	public List<String> getOrdinaryBeacons() {
		List<String> retList = new ArrayList<>();
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {
			Map<String, List<Integer>> beacons = serviceInterface.currentMap.getOrdinaryPlaces();
			retList.addAll(beacons.keySet());
		}
		return retList;
	}

	@Override
	public void findAllSpecialBeacons(String name) {
		if( serviceInterface.mapAvailabilityState == ServiceInterface.MapAvailabilityState.loaded ) {
			Map<String, List<Integer>> beacons = serviceInterface.currentMap.getSpecialPlaces();

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
			String queryString = query.toLowerCase();

			for( int i = 0 ; i < beacons.size() ; i++ ) {
				String name = beacons.valueAt(i).name.toLowerCase();
				String description = beacons.valueAt(i).description.toLowerCase();

				if( name.contains(queryString) || description.contains(queryString) ) {
					retBeacons.add(beacons.valueAt(i));
				}
			}

			mResponder.searchResults(retBeacons);
		}
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
	}
}
