package com.unknown.navevent.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.MainActivityLogic;
import com.unknown.navevent.bLogic.MapSelectActivityLogic;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MapData;
import com.unknown.navevent.interfaces.MapSelectActivityLogicInterface;
import com.unknown.navevent.interfaces.MapSelectActivityUI;

import java.util.ArrayList;
import java.util.List;

public class MapSelectActivity extends AppCompatActivity implements MapSelectActivityUI {
	private MapSelectActivityLogicInterface mIfc = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_select);


		//Creating the mapSelectLogic
		mIfc = new MapSelectActivityLogic(this);
		mIfc.onCreate(this);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		mIfc.onDestroy();
	}


	@Override
	protected void onStart() {
		super.onStart();

		mIfc.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();

		mIfc.onStop();
	}

	@Override
	public void onlineMapQueryRespond(final List<MapData> maps) {
		final List<String> results = new ArrayList<>();//convert to string list
		for (int i = 0; i < maps.size(); i++) results.add(maps.get(i).getName());

		if(!results.isEmpty()) {												//Opens a dialog for the user to select the Maps he wants to load
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Searchresults");
			String[] beaconNames = new String[results.size()];
			for (int i = 0; i < results.size(); i++) {
				beaconNames[i] = maps.get(i).getName();
			}
			builder.setItems(beaconNames, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					mIfc.downloadMap(maps.get(i).getID());
				}
			}).show();
		}
		else Toast.makeText(this, getString(R.string.found_no_search_results), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void localMapsLoaded(final List<MapData> maps) {
		final ListView list = (ListView) findViewById(R.id.offlineMapList);

		List<String> tmpList = new ArrayList<>();//convert to string list
		for (int i = 0; i < maps.size(); i++) tmpList.add(maps.get(i).getName());

		ArrayAdapter<String> adapter = new ArrayAdapter<>(MapSelectActivity.this, android.R.layout.simple_list_item_1, tmpList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				mIfc.setActiveMap(maps.get(i).getID());
			}
		});
	}

	@Override
	public void downloadFailed(String errorcode) {
		Toast.makeText(this, getString(R.string.Download_failed) + errorcode, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void downloadFinished(MapData map) {
		Toast.makeText(this, R.string.Download_Finished, Toast.LENGTH_SHORT).show();

		mIfc.setActiveMap(map.getID());
	}

	@Override
	public void foundLocalMap(final MapData map) {					//notifies when a map is already located on the device
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapSelectActivity.this);

		// set title
		alertDialogBuilder.setTitle(getString(R.string.FoundMap));

		// set dialog message
		alertDialogBuilder
				.setMessage("Local Map " + map.getName() + "found, do you want to load it?")
				.setCancelable(false)
				.setPositiveButton(getString(R.string.String_Yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mIfc.setActiveMap(map.getID());
					}
				})
				.setNegativeButton(getString(R.string.String_No), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	@Override
	public void switchToMainActivity() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		finish();
	}
}
