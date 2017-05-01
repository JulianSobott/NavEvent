package com.unknown.navevent.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.MapData;

public class AdminAreaActivity extends AppCompatActivity implements AdminAreaUI {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);
    }

    @Override
    public void updateMap(MapData map) {

    }

    @Override
    public void invalidMapID() {
        Toast.makeText(this, R.string.invalid_MapID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void downloadFailed(String errorcode) {
        Toast.makeText(this, getString(R.string.Download_failed)+ errorcode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isOffline() {
        Toast.makeText(this, R.string.NoInternetAccess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beaconSuccessfullyConfigured() {
        Toast.makeText(this, R.string.BeaconSuccessfullyConfigured, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beaconConfigurationFailed(String errorcode) {
        Toast.makeText(this, getString(R.string.BeaconNotConfigured)+ errorcode, Toast.LENGTH_SHORT).show();
    }
}
