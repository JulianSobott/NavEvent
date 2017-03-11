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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import com.unknown.navevent.R;

public class DrawTheMap extends View implements View.OnTouchListener {
    boolean beacon_isSelected=false;
    Bitmap beacon[];
    float[] x;
    float[] y;
    int BeaconNumber;
    int theMagicNumberThatNeverShouldBeUsed=975667323;
    public DrawTheMap(Context context, int BeaconNumber) {
        super(context);
        x=new float[BeaconNumber];
        y=new float[BeaconNumber];
        x[0]=200;
        x[1]=200;
        x[2]=200;
        x[3]=200;
        y[0]=100;
        y[1]=200;
        y[2]=300;
        y[3]=400;
        beacon=new Bitmap[BeaconNumber];
        this.BeaconNumber=BeaconNumber;
        for(int i=0; i<BeaconNumber;i++){
            beacon[i]=BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
        }
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<BeaconNumber;i++){
            canvas.drawBitmap(beacon[i],x[i],y[i],new Paint());
        }


        invalidate();
    }
    public boolean onTouch(View v, MotionEvent me) {

        if(me.ACTION_DOWN==me.getAction()&&getClickedBeacon((int) me.getX(), (int) me.getY())!=theMagicNumberThatNeverShouldBeUsed)

            if (!beacon_isSelected) {
            beacon[getClickedBeacon((int) me.getX(), (int) me.getY())] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_selected);
            beacon_isSelected = true;

            } else {
            beacon[getClickedBeacon((int) me.getX(), (int) me.getY())] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
            beacon_isSelected = false;
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

}