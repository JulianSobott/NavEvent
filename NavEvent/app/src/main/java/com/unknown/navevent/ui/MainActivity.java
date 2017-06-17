package com.unknown.navevent.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.MainActivityLogic;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SideBar.SideBarInterface, MainActivityUI, DrawTheMap.MapActionInterface {
    //Background-logic interface
    private MainActivityLogicInterface mIfc = null;

    //Request-callback ids
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 0;

    private static BeaconInfo beaconInfo;
    private SideBar bar;
    private Button sideOpen;
    private MapDisplayFragment mapDisplayFragment;
    MapDataForUI mapDefault;
    private static MapDataForUI activeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creating background-logic for this activity
        mIfc = new MainActivityLogic(this);
        mIfc.onCreate(this);

        super.onCreate(savedInstanceState);

        //Generating a default empty map
        List<BeaconDataForUI> list1 = new ArrayList<BeaconDataForUI>();
        mapDefault = new MapDataForUI(list1, BitmapFactory.decodeResource(getResources(), R.mipmap.empty_map));


        if (activeMap == null) {                        //Sets a empty default map if there is no Map loaded to prevent a Crash
            activeMap = mapDefault;
        }

        setContentView(R.layout.activity_main);

        bar = (SideBar) getSupportFragmentManager().findFragmentById(R.id.SideBarFrag);                 //setting the fragments
        beaconInfo = (BeaconInfo) getSupportFragmentManager().findFragmentById(R.id.frag);
        sideOpen = (Button) findViewById(R.id.SideBarBtn);
        mapDisplayFragment = (MapDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.mapDisplayfragment);
        bar.getView().setBackgroundColor(Color.argb(255, 240, 240, 240));                               //Setting the rough desing for the Fragments
        beaconInfo.getView().setBackgroundColor(Color.argb(255, 255, 255, 255));

        hideFragment(bar);

        sideOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(bar);
            }
        });

        beaconInfo.getView().setOnClickListener(new View.OnClickListener() {                            //Expanding the Bottomsheet if clicked on
            @Override
            public void onClick(View view) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) beaconInfo.getView().getLayoutParams();
                if (params.height == RelativeLayout.LayoutParams.WRAP_CONTENT) {
                    params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());       //some complex code to insert the height in density pixels not in normal
                    beaconInfo.onCollapse();
                } else {
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    beaconInfo.onExtend();
                }
                beaconInfo.getView().setLayoutParams(params);
            }
        });


    }

    @Override
    protected void onDestroy() {
        mIfc.onDestroy();//Destroying background-logic

        super.onDestroy();
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

    //Display a hidden fragment
    private void showFragment(Fragment f) {
        FragmentTransaction Tr = getSupportFragmentManager().beginTransaction();
        Tr.show(f);
        Tr.addToBackStack("f");
        Tr.commit();
    }

    //Hide a displayed fragment
    public void hideFragment(Fragment f) {
        FragmentTransaction Tr = getSupportFragmentManager().beginTransaction();
        Tr.hide(f);
        Tr.addToBackStack("f");
        Tr.commit();
    }

    public static MapDataForUI getMap() {
        return activeMap;
    }

    public void hideSideBar() {
        hideFragment(bar);
    }


    @Override
    public void initCompleted() {
    }

    @Override
    public void notSupported(String errorcode) {            //Checks if the Device is supported and kills the App if not
        //todo debug: uncomment this block to enable the app only for supported devices
        //Notify user and shutdown the app
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.bluetoothNotAvailable);
		builder.setPositiveButton(android.R.string.ok, null);
		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				finish();
			}
		});
		builder.show();*/
        Toast.makeText(MainActivity.this, "Device does not support required Bluetooth LE", Toast.LENGTH_LONG).show();
    }

    @Override
    public void bluetoothDeactivated() {        //Is called if bluetooth is offline, requests to enable it
        //Notify user to enable bluetooth
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.bluetoothNotEnabled);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }

    @Override
    public void askForPermissions() {            //Is called if device-location is offline, asks for permission to enable it
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
    public void onRequestPermissionsResult(int requestCode,        //Is called to tell the user if the app can eable the things it needs
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "coarse location permission granted", Toast.LENGTH_SHORT).show();//debug
                mIfc.retryBeaconConnection();
            } else {
                Toast.makeText(this, R.string.locationAccessDeniedWarning, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void switchToMapSelectActivity() {                        //Switches to the Activity to select a Map if none is Loaded
        Intent intent = new Intent(getApplicationContext(), MapSelectActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Switch to map select activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void foundLocalMap(MapData map) {
        //todo
    }

    @Override
    public void updateMap(MapData map) {                            //Loads a Map if one is selected in the MapSelectActivity
        activeMap = mapDataAdapter(map);
        mapDisplayFragment.LoadBeacons();
        bar.loadBeacons();
        Toast.makeText(this, "Map '" + map.getName() + " loaded!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateBeaconPosition(int beaconID) {                //this method gives the beacon where the usern is standing at right now and shows it on the map
        if (beaconID == 0)
            Toast.makeText(this, "Lost beacon signal", Toast.LENGTH_SHORT).show();
        else {
            activeMap.setClosestBeacon(beaconID);
            mapDisplayFragment.LoadBeacons();
        }
        beaconInfo.updateBeaconText(beaconID);
    }

    @Override
    public void markBeacons(List<Integer> beaconIDs) {                //Marks a list of beacons on the map for example as a search result
        activeMap.selectBeacons(beaconIDs);
        mapDisplayFragment.LoadBeacons();
    }

    private MapDataForUI mapDataAdapter(MapData in) {                //Converts a list of Data for a Map into a UI-usable format.
        List<BeaconDataForUI> newBeaconList = new ArrayList<BeaconDataForUI>();
        BeaconData[] oldBeacons;
        oldBeacons = in.getBeacons().toArray(new BeaconData[in.getBeacons().size()]);


        for (int i = 0; i < in.getBeacons().size(); i++) {
            newBeaconList.add(new BeaconDataForUI(oldBeacons[i].getId(), oldBeacons[i].getMapPositionX(), oldBeacons[i].getMapPositionY()));
            newBeaconList.get(i).setDisplayedText(oldBeacons[i].getName());
            for (List<Integer> listSpec : in.getSpecialPlaces().values()) {
                if (listSpec.contains(newBeaconList.get(i).getID())) {
                    newBeaconList.get(i).setSpecial(true);
                }
            }
            for (List<Integer> listOrd : in.getOrdinaryPlaces().values()) {
                if (listOrd.contains(newBeaconList.get(i).getID())) {
                    newBeaconList.get(i).setOrdinary(true);
                }
            }
        }
        MapDataForUI out = new MapDataForUI(newBeaconList, in.getImage());
        return out;
    }

    @Override
    public void beaconTaped(int beaconID){                   //The logic for the Case the user tapped on a Beacon
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) beaconInfo.getView().getLayoutParams();
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        beaconInfo.showBeaconInfo(beaconID);
        beaconInfo.onExtend();
        beaconInfo.getView().setLayoutParams(params);
    }


}