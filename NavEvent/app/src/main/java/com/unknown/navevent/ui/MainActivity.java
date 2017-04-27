package com.unknown.navevent.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.MainActivityLogic;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SideBar.SideBarInterface, MainActivityUI {
	//Background-logic interface
	private MainActivityLogicInterface mIfc = null;
	
	//Request-callback ids
	private static final int PERMISSION_REQUEST_COARSE_LOCATION = 0;
	
	
    private BeaconInfo text;
    private SideBar bar;
    private Button sideOpen;
    private MapDisplayFragment mapDisplayFragment;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		//Creating background-logic for this activity
		mIfc = new MainActivityLogic(this);
		mIfc.onCreate(this);
		

        bar= (SideBar) getSupportFragmentManager().findFragmentById(R.id.SideBarFrag);
        text = (BeaconInfo) getSupportFragmentManager().findFragmentById(R.id.frag);
        sideOpen=(Button)findViewById(R.id.SideBarBtn);
        mapDisplayFragment = (MapDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.mapDisplayfragment);
        bar.getView().setBackgroundColor(Color.BLUE);

        hideFragment(bar);


        sideOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(bar);
            }
        });
    }
	
	@Override
	protected  void onDestroy() {
		mIfc.onDestroy();//Destroying background-logic

		super.onDestroy();
	}

    private void showFragment(Fragment f){
    FragmentTransaction Tr = getSupportFragmentManager().beginTransaction();
        Tr.show(f);
        Tr.commit();
    }

    public void hideFragment(Fragment f){
        FragmentTransaction Tr = getSupportFragmentManager().beginTransaction();
        Tr.hide(f);
        Tr.commit();
    }

    public void hideSideBar(){
        hideFragment(bar);
    }

    @Override
    public void initCompleted() {

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

					@TargetApi(23)//todo find out what this is
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
    public void switchToMapSelectActivity() {
		//todo: first create MapSelect activity and then use this code
		/*Intent intent = new Intent(getApplicationContext(), MapSelectActivity.class);
		startActivity(intent);
		finish();*/
	    Toast.makeText(this, "Switch to map select activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateMap(MapData map) {
		Toast.makeText(this, "Map '"+map.getName()+"'' loaded!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateBeaconPosition(int beaconID) {
		//example implementation
		/*if( beaconID == 0 )
			Toast.makeText(this, "Lost beacon signal", Toast.LENGTH_SHORT).show();
		else outputView.setText("Beacon id: " + beaconID);*/
    }

    @Override
    public void markBeacons(List<Integer> beaconIDs) {

    }
}
