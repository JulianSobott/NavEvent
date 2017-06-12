package com.unknown.navevent.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.unknown.navevent.R;

public class DrawTheMap extends View implements View.OnTouchListener {
	Bitmap beaconTexture[];
	float[] x;
	float[] y;
	private int beaconNumber;
    private ScaleGestureDetector scaleGestureDetector;
	MapDataForUI displayedMap;
	float scale = 1;

	public DrawTheMap(Context context, MapDataForUI MapInput) {
		super(context);
		displayedMap = MapInput;
		beaconNumber = displayedMap.getBeaconNumber();
		x = new float[beaconNumber];
		y = new float[beaconNumber];
        scaleGestureDetector = new ScaleGestureDetector(context,new Scalelistener());

		for (int i = 0; i < beaconNumber; i++) {
			x[i] = (float) this.displayedMap.beacons[i].getxCord();
			y[i] = (float) this.displayedMap.beacons[i].getyCord();
		}

        setOnTouchListener(this);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawBitmap( displayedMap.getMap(), null, new RectF(0, 0, ((float) ( displayedMap.getMap().getWidth() * scale)), ((float) ( displayedMap.getMap().getHeight() * scale))), new Paint());

		for (int i = 0; i < beaconNumber; i++) {
			Paint paintGreen= new Paint();			//creating the paints to paint the beacons with and configure them
			Paint paintRed = new Paint();
			Paint paintYellow = new Paint();
			Paint paintBlue = new Paint();
			Paint paintNot = new Paint();

			paintGreen.setARGB(255,0,100,0);
			paintRed.setARGB(255,255,0,0);
			paintYellow.setARGB(255,255,215,0);
			paintBlue.setARGB(255,0,0,255);
			paintNot.setARGB(0,0,0,0);

            if(displayedMap.beacons[i].isOrdinary()) canvas.drawCircle(x[i]*scale,y[i]*scale,25*scale,paintBlue);		//Painting the Beacons according to their state
            else if(displayedMap.beacons[i].isSpecial()) canvas.drawCircle(x[i]*scale,y[i]*scale,25*scale,paintGreen);
            else canvas.drawCircle(x[i]*scale,y[i]*scale,25*scale,paintNot);
            if(displayedMap.beacons[i].isSelected()) canvas.drawCircle(x[i]*scale,y[i]*scale,25*scale,paintYellow);
            if(displayedMap.beacons[i].isClosest()) canvas.drawCircle(x[i]*scale,y[i]*scale,25*scale,paintRed);
		}
		invalidate();
	}

	public boolean onTouch(View v, MotionEvent me) {
		scaleGestureDetector.onTouchEvent(me);
		int selectedBeaconID = getClickedBeacon((int) me.getX(), (int) me.getY());
		if (me.ACTION_DOWN == me.getAction()) {

		}
		return true;
	}

	private int getClickedBeacon(int x, int y) {					//returns the beacon at the position the user tapped or a magic number if there is no Beacon
		int returnBeaconID = 0;
		for (int i = 0; i < beaconNumber; i++) {
			if (Math.abs((this.x[i]) - x) <= 50 && Math.abs((this.y[i]) - y) <= 50) {
				returnBeaconID = displayedMap.beacons[i].getID();
			}
		}
		return returnBeaconID;
	}

	public void loadMap(MapDataForUI newMap) {						//has to be called every time something on the map changes!!
		displayedMap = newMap;
		beaconNumber = displayedMap.getBeaconNumber();
		x = new float[beaconNumber];
		y = new float[beaconNumber];
		beaconTexture = new Bitmap[beaconNumber];

		for (int i = 0; i < beaconNumber; i++) {
			beaconTexture[i] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
			x[i] = (float) this.displayedMap.beacons[i].getxCord();
			y[i] = (float) this.displayedMap.beacons[i].getyCord();
		}
	}
	private class Scalelistener extends ScaleGestureDetector.SimpleOnScaleGestureListener{      //Androidnative Listenerclass for scaleevents
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();


			scale = Math.max(0.1f, Math.min(scale, 5.0f));

			invalidate();
			return true;
		}

	}

}