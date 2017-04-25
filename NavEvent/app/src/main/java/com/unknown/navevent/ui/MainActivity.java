package com.unknown.navevent.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.unknown.navevent.R;
import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapData;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SideBar.SideBarInterface,MainActivityUI {
    private BeaconInfo text;
    private SideBar bar;
    private Button sideOpen;
    private MapDisplayFragment mapDisplayFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    @Override
    public void bluetoothDeactivated() {

    }

    @Override
    public void askForPermissions() {

    }

    @Override
    public void switchToMapSelectActivity() {

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
