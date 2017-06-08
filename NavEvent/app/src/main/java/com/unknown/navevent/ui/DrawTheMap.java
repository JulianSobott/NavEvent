package com.unknown.navevent.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.unknown.navevent.R;

public class DrawTheMap extends View implements View.OnTouchListener {
	//boolean beacon_isSelected[];
	Bitmap beaconTexture[];
	float[] x;
	float[] y;
	private int beaconNumber;
	int theMagicNumberThatNeverShouldBeUsed = 975667323;
	MapDataForUI displayedMap;

	public DrawTheMap(Context context, MapDataForUI MapInput) {
		super(context);
		displayedMap = MapInput;
		beaconNumber = displayedMap.getBeaconNumber();
		x = new float[beaconNumber];
		y = new float[beaconNumber];
		beaconTexture = new Bitmap[beaconNumber];

		for (int i = 0; i < beaconNumber; i++) {
			beaconTexture[i] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
			x[i] = (float) this.displayedMap.Beacons[i].getxCord();
			y[i] = (float) this.displayedMap.Beacons[i].getyCord();
		}
		setOnTouchListener(this);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	    /*Log.e("TestLog","Canvasstats:"+canvas.getDensity()+" "+canvas.getHeight()+" "+canvas.getWidth());

        Log.e("TestLog","Bitmapstats:"+displayedMap.getMap().getDensity()+" "+displayedMap.getMap().getHeight()+" "+displayedMap.getMap().getWidth());*/

		float scale = 1;
        /*if(true){
        scale=(float)(240.0/ displayedMap.getMap().getDensity());}

        Log.e("TestLog","scale:"+scale);*/

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

			//canvas.drawBitmap(beaconTexture[i], null, new RectF(x[i], y[i], x[i] + 50, y[i] + 50), new Paint()); todo del

			if(displayedMap.Beacons[i].isVisible()) canvas.drawCircle(x[i],y[i],25,paintBlue);
			else if(displayedMap.Beacons[i].isClosest()) canvas.drawCircle(x[i],y[i],25,paintRed);
			else if(displayedMap.Beacons[i].isSpecial()) canvas.drawCircle(x[i],y[i],25,paintGreen);
			else if(displayedMap.Beacons[i].isSelected()) canvas.drawCircle(x[i],y[i],25,paintYellow);
			else canvas.drawCircle(x[i],y[i],25,paintNot);
		}
		//MainActivity.updateDisplayedText();		// TODO: 08.06.2017  del
		invalidate();
	}

	public boolean onTouch(View v, MotionEvent me) {
		/*int selectedBeacon = getClickedBeacon((int) me.getX(), (int) me.getY());							todo Add funbktion to display text of a selected Beacon
		if (me.ACTION_DOWN == me.getAction() && selectedBeacon != theMagicNumberThatNeverShouldBeUsed)

			if (!beacon_isSelected[selectedBeacon]) {
				beaconTexture[selectedBeacon] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_selected);
				beacon_isSelected[selectedBeacon] = true;
				 displayedMap.Beacons[selectedBeacon].select(true);
				for (int i = 0; i < beaconNumber; i++) {
					if (beacon_isSelected[i] && i != selectedBeacon) {
						beacon_isSelected[selectedBeacon] = false;
						 displayedMap.Beacons[selectedBeacon].select(false);

					}
				}

			} else {
				beaconTexture[selectedBeacon] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
				beacon_isSelected[selectedBeacon] = false;
				displayedMap.Beacons[selectedBeacon].select(false);
			}*/
		return true;
	}

	private int getClickedBeacon(int x, int y) {					//returns the beacon at the position the user tapped or a magic number if there is no Beacon
		int returnBeaconID = theMagicNumberThatNeverShouldBeUsed;
		for (int i = 0; i < beaconNumber; i++) {
			if (Math.abs((this.x[i]) - x) <= 25 && Math.abs((this.y[i]) - y) <= 25) {
				returnBeaconID = i;
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
			x[i] = (float) this.displayedMap.Beacons[i].getxCord();
			y[i] = (float) this.displayedMap.Beacons[i].getyCord();
		}
	}

}