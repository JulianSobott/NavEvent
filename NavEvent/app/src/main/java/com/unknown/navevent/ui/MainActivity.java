package com.unknown.navevent.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.MainActivityLogic;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.MainActivityLogicInterface;
import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements SideBar.SideBarInterface, MainActivityUI {
	//Background-logic interface
	private MainActivityLogicInterface mIfc = null;
	
	//Request-callback ids
	private static final int PERMISSION_REQUEST_COARSE_LOCATION = 0;
	
	
    private static BeaconInfo text;
    private SideBar bar;
    private Button sideOpen;
    private MapDisplayFragment mapDisplayFragment;
    MapForTests mapFlur;
    MapForTests mapFlurKreuzung;
    private static MapForTests activeMap;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Generating 2 Maps for testing purposes
        List <BeaconForTests> list1=new ArrayList<BeaconForTests>();

        List <BeaconForTests> list2=new ArrayList<BeaconForTests>();

        list1.add(new BeaconForTests(200, 100));
        list1.add(new BeaconForTests(200, 600));

        list2.add(new BeaconForTests(200, 100));
        list2.add(new BeaconForTests(200, 600));
        list2.add(new BeaconForTests(430, 300));

        mapFlur = new MapForTests(list1, BitmapFactory.decodeResource(getResources(),R.mipmap.testmapflur), 2);

        mapFlurKreuzung = new MapForTests(list2, BitmapFactory.decodeResource(getResources(),R.mipmap.testmapflurkreuzung),3);
        if (activeMap==null) {
            activeMap=mapFlur;
        }

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

    public static void updateDisplayedText(){
        text.changeText(activeMap.getStringOfDisplayedBeacon(activeMap.getSelectedBeacon()));
    }

    public static MapForTests getMap(){
        return activeMap;
    }

    public void hideSideBar(){
        hideFragment(bar);
    }

    @Override
    public void showMapFlur() {
        activeMap=mapFlur;
        mapDisplayFragment.LoadBeacons();
    }

    @Override
    public void showMapKreuz() {
        activeMap=mapFlurKreuzung;
        mapDisplayFragment.LoadBeacons();

    }


    @Override
    public void initCompleted() {

    }

    @Override
    public void notSupported(String errorcode) {
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
        activeMap=MapdataAdapter(map);
        mapDisplayFragment.LoadBeacons();
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
        //int [] IDArray= beaconIDs.toArray(new int[beaconIDs.size()]);

    }
    private MapForTests MapdataAdapter(MapData in){
        List <BeaconForTests> newBeaconList=new ArrayList<BeaconForTests>();
        BeaconData[] oldBeacons;
        oldBeacons= in.getBeacons().toArray(new BeaconData[in.getBeacons().size()]);
        for(int i=0;i<in.getBeacons().size();i++){
            newBeaconList.add(new BeaconForTests(oldBeacons[i].getMapPositionX(),oldBeacons[i].getMapPositionY()));
        }
        MapForTests out=new MapForTests(newBeaconList,in.getImage(),in.getBeacons().size());
        return out;
    }

    public static boolean mapIsSelected(){
        if (activeMap==null)
            return false;
        else return true;
    }

}
