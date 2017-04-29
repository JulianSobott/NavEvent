package com.unknown.navevent.ui;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import com.unknown.navevent.R;

import java.util.ArrayList;
import java.util.List;

public class DrawTheMap extends View implements View.OnTouchListener {
    boolean beacon_isSelected[];
    Bitmap beaconTexture[];
    float[] x;
    float[] y;
    private int BeaconNumber=3;
    int theMagicNumberThatNeverShouldBeUsed=975667323;
    MapForTests testMap;
    public DrawTheMap(Context context , MapForTests MapInput) {
        super(context);
            testMap = MapInput;
            BeaconNumber = testMap.getBeaconNumber();
            x = new float[BeaconNumber];
            y = new float[BeaconNumber];
            beacon_isSelected = new boolean[BeaconNumber];
            beaconTexture = new Bitmap[BeaconNumber];

            for (int i = 0; i < BeaconNumber; i++) {
                beaconTexture[i] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
                x[i] = (float) this.testMap.Beacons[i].getxCord();
                y[i] = (float) this.testMap.Beacons[i].getyCord();
            }
            setOnTouchListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(testMap.getMap(),0,0,new Paint());
        for (int i=0;i<BeaconNumber;i++){
            canvas.drawBitmap(beaconTexture[i],x[i],y[i],new Paint());

        }
        MainActivity.updateDisplayedText();
        invalidate();
    }
    public boolean onTouch(View v, MotionEvent me) {
        int selectedBeacon= getClickedBeacon((int) me.getX(), (int) me.getY());
        if(me.ACTION_DOWN==me.getAction()&&selectedBeacon!=theMagicNumberThatNeverShouldBeUsed)

            if (!beacon_isSelected[selectedBeacon]) {
            beaconTexture[selectedBeacon] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_selected);
            beacon_isSelected[selectedBeacon] = true;
                testMap.Beacons[selectedBeacon].select(true);
                for(int i=0;i<BeaconNumber;i++){
                    if(beacon_isSelected[i]&&i!=selectedBeacon){
                        beacon_isSelected[selectedBeacon] = false;
                        testMap.Beacons[selectedBeacon].select(false);

                    }
                }

            } else {
                beaconTexture[selectedBeacon] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
                beacon_isSelected[selectedBeacon] = false;
                testMap.Beacons[selectedBeacon].select(false);
        }
        return true;
    }

    private int getClickedBeacon(int x, int y){
        int returnBeaconID=theMagicNumberThatNeverShouldBeUsed;
        for (int i=0;i<BeaconNumber;i++){
            if (Math.abs((this.x[i]+25)-x)<=50&&Math.abs((this.y[i]+25)-y)<=50){
                returnBeaconID=i;
            }
        }
        return returnBeaconID;
    }

    public void loadMap(MapForTests newMap){
        testMap=newMap;
        BeaconNumber = testMap.getBeaconNumber();
        x=new float[BeaconNumber];
        y=new float[BeaconNumber];
        beacon_isSelected=new boolean[BeaconNumber];
        beaconTexture=new Bitmap[BeaconNumber];

        for(int i=0; i<BeaconNumber;i++){
            beaconTexture[i]=BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
            x[i]=(float)this.testMap.Beacons[i].getxCord();
            y[i]=(float)this.testMap.Beacons[i].getyCord();
        }
    }

}