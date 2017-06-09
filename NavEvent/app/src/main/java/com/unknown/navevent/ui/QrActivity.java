package com.unknown.navevent.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

    //Request-callback ids
    private static final int PERMISSION_REQUEST_CAMERA = 0;

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
                if (ContextCompat.checkSelfPermission(QrActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(QrActivity.this);
                    builder.setTitle(R.string.cameraAccessDialogTitle);
                    builder.setMessage(R.string.cameraAccessDialogContent);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @TargetApi(23)          //this method is only applied to devices with api 23 or higher because they need a new ind of permissions
                        @Override
                        public void onDismiss(DialogInterface dialog) {     //request permission to use camera for higher api levels for lower the manifest is enough
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                        }

                    });
                    builder.show();
                } else {
                    activateQrCodeScanner();
                }
            }
        });
    }

    private void activateQrCodeScanner() {          //Activates the scanner and performs a scan
        qrCodeReaderView.setVisibility(View.VISIBLE);
        qrCodeReaderView.setBackCamera();
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.startCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,        //Is called to tell the user if the app can enable the things it needs
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_SHORT).show();//debug
                activateQrCodeScanner();
            } else {
                Toast.makeText(this, R.string.cameraAccessDeniedWarning, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {       //is called when a QRCODE is found by the camera
        mapID = Integer.parseInt(text);
        qrCodeReaderView.stopCamera();
        Intent intent = new Intent(this, AdminAreaActivity.class);
        AdminAreaActivity.setMapID(mapID);
        startActivity(intent);
    }
}
