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
	boolean beacon_isSelected[];
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
		beacon_isSelected = new boolean[beaconNumber];
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
			canvas.drawBitmap(beaconTexture[i], null, new RectF(x[i], y[i], x[i] + 50, y[i] + 50), new Paint());

		}
		MainActivity.updateDisplayedText();
		invalidate();
	}

	public boolean onTouch(View v, MotionEvent me) {
		int selectedBeacon = getClickedBeacon((int) me.getX(), (int) me.getY());
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
			}
		return true;
	}

	private int getClickedBeacon(int x, int y) {
		int returnBeaconID = theMagicNumberThatNeverShouldBeUsed;
		for (int i = 0; i < beaconNumber; i++) {
			if (Math.abs((this.x[i] + 25) - x) <= 50 && Math.abs((this.y[i] + 25) - y) <= 50) {
				returnBeaconID = i;
			}
		}
		return returnBeaconID;
	}

	public void loadMap(MapDataForUI newMap) {
		displayedMap = newMap;
		beaconNumber = displayedMap.getBeaconNumber();
		x = new float[beaconNumber];
		y = new float[beaconNumber];
		beacon_isSelected = new boolean[beaconNumber];
		beaconTexture = new Bitmap[beaconNumber];

		for (int i = 0; i < beaconNumber; i++) {
			beaconTexture[i] = BitmapFactory.decodeResource(getResources(), R.mipmap.beacon_enabeld);
			x[i] = (float) this.displayedMap.Beacons[i].getxCord();
			y[i] = (float) this.displayedMap.Beacons[i].getyCord();
		}
	}

}