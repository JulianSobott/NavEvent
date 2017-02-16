package com.unknown.navevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unknown.navevent.bLogic.MainActivityLogic;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityUI {

	private MainActivityLogicInterface mIfc = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mIfc = new MainActivityLogic();
		mIfc.onCreate();
	}

	@Override
	protected  void onDestroy() {
		mIfc.onDestroy();
	}

	@Override
	public void initCompleted() {

	}
	@Override
	public void notSupported(String errorcode) {

	}
	@Override
	public void bluetoothDeactivated() {

	}
	@Override
	public void askForPermissions() {

	}

	@Override
	public void updateMap(MapData map) {

	}

	@Override
	public void updateBeaconPosition(int beaconID) {

	}

	@Override
	public void markBeacons(List<Integer> beaconIDs) {

	}
}
