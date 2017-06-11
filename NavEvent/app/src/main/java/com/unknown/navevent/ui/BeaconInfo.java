package com.unknown.navevent.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.BottomSheetLogic;
import com.unknown.navevent.bLogic.NavigationDrawerLogic;
import com.unknown.navevent.interfaces.BottomSheetLogicInterface;
import com.unknown.navevent.interfaces.BottomSheetUI;

import us.feras.mdv.MarkdownView;

public class BeaconInfo extends Fragment implements BottomSheetUI{
	private TextView infoText;
	private MarkdownView markdownView;
	private BottomSheetLogicInterface mIfc = null;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Creating the NavigationDrawer
		mIfc=new BottomSheetLogic(this);
		mIfc.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mIfc.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_beacon_info, container, false);
		infoText = (TextView) v.findViewById(R.id.textView);
		markdownView = (MarkdownView) v.findViewById(R.id.markdownView);

		infoText.setText("das ist ein Infotext\nwithsome\nmoreLines\nThan\nNeeded");			//todo del if not needed anymore
        return v;
	}

    public void updateBeaconText(int idNearestBeacon){
        mIfc.getBeaconName(idNearestBeacon);
	    mIfc.getBeaconInfo(idNearestBeacon);
    }

	@Override
	public void beaconInfoRespond(String info) {
		markdownView.loadMarkdown(info);
		//todo
	}

	@Override
	public void beaconNameRespond(String name) {
		infoText.setText(name);
	}
}