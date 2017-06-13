package com.unknown.navevent.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.BottomSheetLogic;
import com.unknown.navevent.interfaces.BottomSheetLogicInterface;
import com.unknown.navevent.interfaces.BottomSheetUI;

import us.feras.mdv.MarkdownView;

public class BeaconInfo extends Fragment implements BottomSheetUI {
	private TextView beaconNameText;
	private MarkdownView markdownView;
	private BottomSheetLogicInterface mIfc = null;
	private int nearestBeacon;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Creating the NavigationDrawer
		mIfc = new BottomSheetLogic(this);
		mIfc.onCreate();
	}

	public void onExtend() {     //is called when the Sheet is extended
		beaconNameText.setText("Click here to collapse");
	}

	public void onCollapse() {    //is called when the Sheet is collapsed
		beaconNameText.setText("NoBeaconSignal");
		mIfc.getBeaconData(nearestBeacon);
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
		beaconNameText = (TextView) v.findViewById(R.id.textViewBeaconName);
		beaconNameText.setText("NoBeaconSignal");
		markdownView = (MarkdownView) v.findViewById(R.id.markdownView);
		markdownView.loadMarkdown("test...GO *asdf*");
		return v;
	}

	public void updateBeaconText(int idNearestBeacon) {
		if (idNearestBeacon != nearestBeacon) {
			nearestBeacon = idNearestBeacon;
			mIfc.getBeaconData(idNearestBeacon);
		}

	}

	public void showBeaconInfo(int beaconID) {
		mIfc.getBeaconData(beaconID);
	}

	@Override
	public void beaconDataRespond(String name, String info) {
		markdownView.loadMarkdown("" + name + "\n" + name.replaceAll(".", "=") + "\n" + info);
		beaconNameText.setText(name);
	}
}