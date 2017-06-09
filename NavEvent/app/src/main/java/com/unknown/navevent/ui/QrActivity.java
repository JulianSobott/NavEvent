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

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrActivity extends AppCompatActivity implements QrCodeReaderUI,ZXingScannerView.ResultHandler {

    Button continueButton;

    String MapID;

    private ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        continueButton =(Button) findViewById(R.id.continueButton);
        setOnclickListeners();
        zXingScannerView= new ZXingScannerView(getApplicationContext());
        zXingScannerView.setResultHandler(this);
    }

    @Override
    public void capturedMapID(int mapID) {

    }
    private void setOnclickListeners(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(zXingScannerView);
                zXingScannerView.startCamera();
            }
        });
    }

    @Override
    public void handleResult(Result result) {
        MapID=result.getText();
        Toast.makeText(this, MapID, Toast.LENGTH_SHORT).show();
        zXingScannerView.stopCamera();
        setContentView(R.layout.activity_qr);
    }
    @Override
    public void onPause(){
        super.onPause();
        zXingScannerView.stopCamera();

    }
}
