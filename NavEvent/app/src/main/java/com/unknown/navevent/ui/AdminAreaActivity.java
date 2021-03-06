package com.unknown.navevent.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.AdminAreaLogic;
import com.unknown.navevent.interfaces.AdminAreaLogicInterface;
import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.MapData;

import java.util.ArrayList;
import java.util.List;

public class AdminAreaActivity extends AppCompatActivity implements AdminAreaUI {
	private AdminAreaLogicInterface mIfc = null;
	private static int mapID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_area);

		//Creating the AdminAreaLogic
		mIfc = new AdminAreaLogic(this);
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
		mIfc.loadMap(mapID);
	}

	@Override
	protected void onStop() {
		super.onStop();

		mIfc.onStop();
	}

	@Override
	public void updateMap(final MapData map) {										//Loads a new map and Lists the beacons to configure in a Listview
		final List<String> mapList = new ArrayList();
		for (int i = 0; i < map.getBeacons().size(); i++) {
			mapList.add(map.getBeacons().get(i).getName());
		}
		final ListView BeaconList = (ListView) findViewById(R.id.ListViewConfiguration);									//Opens a Dialog to check if the user really wants to configure the beacon
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminAreaActivity.this, android.R.layout.simple_list_item_1, mapList);
		BeaconList.setAdapter(adapter);
		BeaconList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminAreaActivity.this);

				alertDialogBuilder.setTitle("Beacon Konfigurieren");

				alertDialogBuilder
						.setMessage("Do you want to configure this Beacon?")
						.setCancelable(false)
						.setPositiveButton(getString(R.string.String_Yes), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mIfc.configureBeacon(map.getBeacons().get(i).getId());
								beaconConfigurationStarted();
							}
						})
						.setNegativeButton(getString(R.string.String_No), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).show();
			}
		});
	}
    public static void setMapID(int mapIDin){
        mapID=mapIDin;
    }

	@Override
	public void invalidMapID() {
		Toast.makeText(this, R.string.invalid_MapID, Toast.LENGTH_LONG).show();
	}

	@Override
	public void downloadFailed(String errorcode) {
		Toast.makeText(this, getString(R.string.Download_failed) + errorcode, Toast.LENGTH_LONG).show();
	}

	@Override
	public void beaconSuccessfullyConfigured() {
		Toast.makeText(this, R.string.BeaconSuccessfullyConfigured, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void beaconConfigurationFailed(String errorcode) {
		Toast.makeText(this, getString(R.string.BeaconNotConfigured) + errorcode, Toast.LENGTH_LONG).show();
	}
	private void beaconConfigurationStarted(){
		Toast.makeText(this, getString(R.string.beacon_configuration_started), Toast.LENGTH_LONG).show();
	}

}
