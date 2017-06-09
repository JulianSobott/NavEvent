package com.unknown.navevent.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.unknown.navevent.R;
import com.unknown.navevent.interfaces.QrCodeReaderUI;

public class QrActivity extends AppCompatActivity implements QrCodeReaderUI {

    Button continueButton;

    String MapID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        continueButton =(Button) findViewById(R.id.continueButton);
        setOnclickListeners();
    }

    @Override
    public void capturedMapID(int mapID) {

    }
    private void setOnclickListeners(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

}
