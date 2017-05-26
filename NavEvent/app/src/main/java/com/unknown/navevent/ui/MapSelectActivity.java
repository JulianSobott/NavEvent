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


        //Creating the mapSelectLogic // TODO uncomment when changed to the right class
        mIfc = new MapSelectActivityLogic(this);
        mIfc.onCreate(this);
    }


    @Override
    public void onlineMapQueryRespond(final List<MapData> maps) {
        final ListView list=(ListView) findViewById(R.id.onlineMapList);

        List<String> tmpList = new ArrayList<>();//convert to string list
        for( int i = 0 ; i < maps.size() ; i++ ) tmpList.add(maps.get(i).getName());

        ArrayAdapter <String> adapter = new ArrayAdapter<>(MapSelectActivity.this,android.R.layout.simple_list_item_1,tmpList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mIfc.downloadMap(maps.get(i).getName());
                //TODO: Add intent to switch to main activity if not handled through the logic

                //todo remove debug code
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void localMapsLoaded(List<MapData> maps) {

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
    public void downloadFinished(MapData map) {
        Toast.makeText(this, R.string.Download_Finished, Toast.LENGTH_SHORT).show();
        mIfc.setActiveMap(map.getName());
    }

    @Override
    public void foundLocalMap(MapData map) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapSelectActivity.this);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.FoundMap));

        // set dialog message
        alertDialogBuilder
                .setMessage("Local Map "+ map.getName()+ "found, do you want to load it?")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.String_Yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //Load the map current in storage
                    }
                })
                .setNegativeButton(getString(R.string.String_No),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
