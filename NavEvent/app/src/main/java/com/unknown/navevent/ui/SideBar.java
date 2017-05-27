package com.unknown.navevent.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.AdminAreaLogic;
import com.unknown.navevent.bLogic.NavigationDrawerLogic;
import com.unknown.navevent.interfaces.AdminAreaLogicInterface;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerLogicInterface;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import java.util.List;


public class SideBar extends Fragment implements NavigationDrawerUI{
    private NavigationDrawerLogicInterface mIfc = null;

    public SideBarInterface activityCommander;

    @Override
    public void searchResults(List<BeaconData> results) {

    }

    public interface SideBarInterface{
        void hideSideBar();
        void showMapFlur();
        void showMapKreuz();
    }
    private View v;
    private Button closeButton;
    private Button buttonMapFlur;
    private Button buttonMapKreuz;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCommander = (SideBarInterface) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


	    //Creating the NavigationDrawer
	    mIfc = new NavigationDrawerLogic(this);
	    mIfc.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_side_bar, container, false);
        closeButton =(Button) v.findViewById(R.id.buttonClose);
        buttonMapFlur =(Button)v.findViewById(R.id.buttonMapFlur);
        buttonMapKreuz=(Button)v.findViewById(R.id.buttonMapKreuz);
        createButtonListeners();
        return v;
    }

	@Override
	public void onDestroy() {
		super.onDestroy();

		mIfc.onDestroy();
	}

	private void createButtonListeners(){
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            activityCommander.hideSideBar();
            }
        });

        buttonMapFlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCommander.showMapFlur();
            }
        });

        buttonMapKreuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCommander.showMapKreuz();
            }
        });
    }

}
