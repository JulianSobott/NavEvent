package com.unknown.navevent.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.MainActivityLogic;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MapSelectActivityLogicInterface;
import com.unknown.navevent.interfaces.MapSelectActivityUI;

import java.util.List;

public class MapSelectActivity extends AppCompatActivity implements MapSelectActivityUI {

	//Background-logic interface
	private MapSelectActivityLogicInterface mIfc = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_select);

		//Creating background-logic for this activity
		//mIfc = new MapSelectActivityLogic(this); todo
		//mIfc.onCreate(this);

	}

	@Override
	public void onlineMapsRespond(List<String> maps) {

	}

	@Override
	public void downloadFailed(String errorcode) {

	}

	@Override
	public void isOffline() {

	}

	@Override
	public void downloadFinished(String name) {

	}

	@Override
	public void foundLocalMap(String name) {

	}
}
