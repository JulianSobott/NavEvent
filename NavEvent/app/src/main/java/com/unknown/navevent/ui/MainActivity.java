package com.unknown.navevent.ui;

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

import com.unknown.navevent.R;
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

		//Creating background-logic for this activity
		mIfc = new MainActivityLogic(this);
		mIfc.onCreate(this);

		//Debug-GUI stuff
		outputView = (TextView)findViewById(R.id.output);
	}

	@Override
	protected  void onDestroy() {
		mIfc.onDestroy();//Destroying background-logic

		super.onDestroy();
	}

	@Override
	public void initCompleted() { //todo: del

	}
	@Override
	public void notSupported(String errorcode) {
		//Notify user and shutdown the app
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.bluetoothNotAvailable);
		builder.setPositiveButton(android.R.string.ok, null);
		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				finish();
			}
		});
		builder.show();
	}
	@Override
	public void bluetoothDeactivated() {
		//Notify user to enable bluetooth
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.bluetoothNotEnabled);
		builder.setPositiveButton(android.R.string.ok, null);
		builder.show();
	}
	@Override
	public void askForPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			//Android M+ Permission check
			if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.locationAccessDialogTitle);
				builder.setMessage(R.string.locationAccessDialogContent);
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
		}
	}

	@Override
	public void switchToMapSelectActivity() {

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       String permissions[], int[] grantResults) {
		if( requestCode == PERMISSION_REQUEST_COARSE_LOCATION ) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this, "coarse location permission granted", Toast.LENGTH_SHORT).show();//debug
				mIfc.retryBeaconConnection();
			} else {
				Toast.makeText(this, R.string.locationAccessDeniedWarning, Toast.LENGTH_LONG).show();
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
