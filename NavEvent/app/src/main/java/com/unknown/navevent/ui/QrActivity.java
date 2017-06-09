package com.unknown.navevent.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.unknown.navevent.R;
import com.unknown.navevent.interfaces.QrCodeReaderUI;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class QrActivity extends AppCompatActivity implements QrCodeReaderUI, QRCodeReaderView.OnQRCodeReadListener {

    Button continueButton;
    int mapID;
    private QRCodeReaderView qrCodeReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        continueButton = (Button) findViewById(R.id.continueButton);
        setOnclickListeners();
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void capturedMapID(int mapID) {

    }

    private void setOnclickListeners() {
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(QrActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {      //asks user for permission to use the cam
                    //ActivityCompat.requestPermissions(QrActivity.this,new String[]{Manifest.permission.CAMERA},);

                } else {
                    qrCodeReaderView.setVisibility(View.VISIBLE);
                    qrCodeReaderView.setBackCamera();
                    qrCodeReaderView.setQRDecodingEnabled(true);
                    qrCodeReaderView.startCamera();
                }
            }
        });
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        mapID = Integer.parseInt(text);
        qrCodeReaderView.stopCamera();
        Intent intent = new Intent(this, AdminAreaActivity.class);
        AdminAreaActivity.setMapID(mapID);
        startActivity(intent);
    }
}
