package com.unknown.navevent.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.BottomSheetLogic;
import com.unknown.navevent.interfaces.BottomSheetLogicInterface;
import com.unknown.navevent.interfaces.BottomSheetUI;

public class BeaconInfo extends Fragment implements BottomSheetUI{
	private TextView infoText;
	private BottomSheetLogicInterface mIfc = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_beacon_info, container, false);
		infoText = (TextView) v.findViewById(R.id.textView);
        mIfc=new BottomSheetLogic(this);
        return v;
	}

	public void changeText(String text) {
		infoText.setText(text);
	}

    public void updateBeaconText(int idNearestBeacon){
        mIfc.getBeaconInfo(idNearestBeacon);
    }

	@Override
	public void beaconInfoRespond(String info) {
		//todo
	}

	@Override
	public void beaconNameRespond(String info) {
		changeText(info);
	}
}