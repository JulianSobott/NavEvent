package com.unknown.navevent.ui;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
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
    String mapID;
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
                qrCodeReaderView.setVisibility(View.VISIBLE);
                qrCodeReaderView.setBackCamera();
                qrCodeReaderView.setQRDecodingEnabled(true);
                qrCodeReaderView.startCamera();
            }
        });
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        mapID = text;
        qrCodeReaderView.stopCamera();
        Intent intent =new Intent(this,AdminAreaActivity.class);
        startActivity(intent);
    }
}
