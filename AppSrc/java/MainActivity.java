package com.unknown.navevent;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unknown.navevent.bLogic.MainActivityLogic;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityUI {

	//Background-logic interface
	private MainActivityLogicInterface mIfc = null;


	//GUI
	TextView outputView;


	//Request-callback ids
	private static final int PERMISSION_REQUEST_COARSE_LOCATION = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mIfc = new MainActivityLogic(this);
		mIfc.onCreate(this);

		//Debug-GUI stuff
		outputView = (TextView)findViewById(R.id.output);
	}

	@Override
	protected  void onDestroy() {
		mIfc.onDestroy();

		super.onDestroy();
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			//Android M+ Permission check
			if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.locationAccessDialogTitle);
				builder.setMessage(R.string.locationAccessDialoContent);
				builder.setPositiveButton(android.R.string.ok, null);
				builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

					@TargetApi(23)
					@Override
					public void onDismiss(DialogInterface dialog) {
						requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
					}

				});
				builder.show();
			}
			else mIfc.retryBeaconConnection();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       String permissions[], int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST_COARSE_LOCATION: {
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(this, "coarse location permission granted", Toast.LENGTH_SHORT);//debug
					mIfc.retryBeaconConnection();
				} else {
					Toast.makeText(this, R.string.locationAccessDeniedWarning, Toast.LENGTH_LONG);
					finish();
					System.exit(1);
				}
				return;
			}
		}
	}

	@Override
	public void updateMap(MapData map) {

	}

	@Override
	public void updateBeaconPosition(int beaconID) {
		outputView.setText("Beacon id: " + beaconID);
	}

	@Override
	public void markBeacons(List<Integer> beaconIDs) {

	}


}
