package com.unknown.navevent.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unknown.navevent.R;


public class MapDisplayFragment extends Fragment {

	DrawTheMap theMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		theMap = new DrawTheMap(getActivity(), MainActivity.getMap());
		return theMap;
	}

	public void LoadBeacons() {
		theMap.loadMap(MainActivity.getMap());
	}
}
