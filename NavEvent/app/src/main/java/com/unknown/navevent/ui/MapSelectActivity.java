package com.unknown.navevent.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        msif=new MapSelectActivityLogicDefault(this);
        msif.onCreate(this);
    }





    @Override
    public void onlineMapsRespond(List<String> maps) {

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
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //Load the map current in storage
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
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
