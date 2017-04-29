package com.unknown.navevent.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.ViewGroup.*;
import com.unknown.navevent.R;

import java.util.ArrayList;
import java.util.List;


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
        v=inflater.inflate(R.layout.fragment_map_display, container, false);
        //if(theMap!=null){
        theMap = new DrawTheMap(getActivity(),MainActivity.getMap());
        return theMap;
    }

    public void LoadBeacons() {
        theMap.loadMap(MainActivity.getMap());

    }
    static public int getBeaconToDisplay(MapForTests map){
        int beaconToDisplay;
        beaconToDisplay=map.getSelectedBeacon();
        return beaconToDisplay;
    }

    public int getParentsViewID(){
        return ((ViewGroup) getView().getParent()).getId();
    }
}
