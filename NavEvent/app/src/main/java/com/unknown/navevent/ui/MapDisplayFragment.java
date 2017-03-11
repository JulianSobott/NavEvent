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
    RelativeLayout layout;
    DrawTheMap theMap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        theMap = new DrawTheMap(getActivity(),4);
        v=inflater.inflate(R.layout.fragment_map_display, container, false);

        return theMap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout= new RelativeLayout(getActivity());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        LoadBeacons();
        ViewGroup group = (ViewGroup) v;
        ((ViewGroup) v).addView(layout);
    }

    private void LoadBeacons() {
        Button b = new Button(getActivity());
        layout.addView(b);

    }
}
