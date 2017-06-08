package com.unknown.navevent.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unknown.navevent.R;


public class MapDisplayFragment extends Fragment {

	View v;
	DrawTheMap theMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_map_display, container, false);
		//if(theMap!=null){
		theMap = new DrawTheMap(getActivity(), MainActivity.getMap());
		return theMap;
	}

	public void LoadBeacons() {
		theMap.loadMap(MainActivity.getMap());

	}

	static public int getBeaconToDisplay(MapDataForUI map) {
		// // TODO Add a method to return the Beacon that is closest to you because it is always seleced an dits massage is displayed at the bottom by default

		return 0;
	}

    /*public int getParentsViewID(){
        return ((ViewGroup) getView().getParent()).getId();
    }*/
}
