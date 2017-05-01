package com.unknown.navevent.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.interfaces.MapSelectActivityLogicInterface;
import com.unknown.navevent.interfaces.MapSelectActivityUI;
import com.unknown.navevent.interfaces.defaultImpl.MapSelectActivityLogicDefault;

import java.util.List;

public class MapSelectActivity extends AppCompatActivity implements MapSelectActivityUI {
    private MapSelectActivityLogicInterface msif = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_select);

        //Creatin the mapSelectLogic
        msif=new MapSelectActivityLogicDefault(this);
        msif.onCreate(this);
    }





    @Override
    public void onlineMapsRespond(final List<String> maps) {
        final ListView list=(ListView) findViewById(R.id.onlineMapList);
        ArrayAdapter <String> adapter =new ArrayAdapter<String>(MapSelectActivity.this,android.R.layout.simple_list_item_1,maps);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                msif.downloadMap(maps.get(i));
                //TODO: Add intent to switch to main activity if not hanedl through the logic
            }
        });
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
    public void downloadFinished(String name) {
        Toast.makeText(this, R.string.Download_Finished, Toast.LENGTH_SHORT).show();
        msif.setActiveMap(name);
    }

    @Override
    public void foundLocalMap(String name) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapSelectActivity.this);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.FoundMap));

        // set dialog message
        alertDialogBuilder
                .setMessage("Local Map "+ name+ "found, do you want to load it?")
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
