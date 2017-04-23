package com.unknown.navevent.bLogic.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.bLogic.MapDataImpl;
import com.unknown.navevent.bLogic.events.LogicIfcBaseEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.MapUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

//Service for map loading, etc.
public class MapService extends Service {
	private static final String TAG = "MapService";

	/////////////////////////////////////////////////////////
	// Background data
	/////////////////////////////////////////////////////////

	private Thread mBackgroundThread;
	private AtomicBoolean mBackgroundThreadRunning = new AtomicBoolean();
	private AtomicBoolean mBackgroundThreadShouldStop = new AtomicBoolean();
	private AtomicInteger mBackgroundThreadShouldWait = new AtomicInteger();//In 100 milliseconds

	private Queue<MapServiceEvent> backgroundTaskQueue = new ConcurrentLinkedQueue<>();


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate() {
		super.onCreate();

		EventBus.getDefault().register(this);

		//Thread
		mBackgroundThreadShouldStop.set(false);
		mBackgroundThreadShouldWait.set(10);

		mBackgroundThread = new Thread(new Runnable() {
			@Override
			public void run() {
				MapServiceEvent task;
				try {
					mBackgroundThreadRunning.set(true);
					while( !mBackgroundThreadShouldStop.get() ) {//Loop until thread should stop
						mBackgroundThreadShouldWait.set( 10 );
						while( mBackgroundThreadShouldWait.getAndDecrement() > 0 ) Thread.sleep(100);

						while( ( task = backgroundTaskQueue.poll() ) != null ) {

							if( task.task == MapServiceEvent.EVENT_LOAD_MAP_LOCAL) {
								Log.d(TAG, "mapThread: EVENT_LOAD_MAP_LOCAL");
								loadLocalMap(task.map);
							}
							else if( task.task == MapServiceEvent.EVENT_SAVE_MAP_LOCAL) {
								Log.d(TAG, "mapThread: EVENT_SAVE_MAP_LOCAL");
								saveLocalMap(task.map);
							}
							else if( task.task == MapServiceEvent.EVENT_DOWNLOAD_MAP) {
								Log.d(TAG, "mapThread: EVENT_DOWNLOAD_MAP");
								downloadMap(task.mapName);
							}
							else if( task.task == MapServiceEvent.EVENT_GET_ALL_LOCAL_MAPS) {
								Log.d(TAG, "mapThread: EVENT_GET_ALL_LOCAL_MAPS");
								getAllLocalMaps();
							}
							else if( task.task == MapServiceEvent.EVENT_FIND_ONLINE_MAP) {
								Log.d(TAG, "mapThread: EVENT_FIND_ONLINE_MAP");
								findOnlineMap(task.mapName);
							}
							else if( task.task == MapServiceEvent.EVENT_STOP_SELF) {
								Log.d(TAG, "mapThread: EVENT_STOP_SELF");
								stopSelf();
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mBackgroundThreadRunning.set(false);
			}
		});
		mBackgroundThread.start();

		EventBus.getDefault().post(new LogicIfcBaseEvent(LogicIfcBaseEvent.EVENT_MAP_SERVICE_STARTED));
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mBackgroundThreadShouldStop.set(true);
		try {
			mBackgroundThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onMessageEvent(MapServiceEvent event) {
		backgroundTaskQueue.add( event );
	}


	/////////////////////////////////////////////////////////
	// Methods to handle tasks
	/////////////////////////////////////////////////////////

	private final static String SEP_CHAR = ";";//Separates items in a file

	//Returns the file for the specified path
	private File getFile(String filename ) {
		File file =  new File(this.getExternalFilesDir(null).getAbsolutePath() + "/" + filename);//Public/debug
		//File file = new File(mContext.getFilesDir() + filename);//Private
		file.getParentFile().mkdirs();//Create directories if the parent dir doesn't exit.
		return file;
	}

	private void loadLocalMap( MapIR map ) {
		try {
			if( !getFile("maps/" + map.id + ".mapDat").exists()) {
				Toast.makeText(this, "Map '" + map.name + "' does not exist!", Toast.LENGTH_LONG).show();
				return;
			}
			BufferedReader bufReader = new BufferedReader( new InputStreamReader(new FileInputStream(getFile("maps/"+map.id+".mapDat")), "UTF-8"));

			map.name = getNextString(bufReader);
			map.id = Integer.parseInt(getNextString(bufReader));
			map.description = getNextString(bufReader);
			map.imagePath = getNextString(bufReader);
			{//Load image
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				map.image = BitmapFactory.decodeFile(getFile("mapImgs/" + map.imagePath).getPath(), options);
			}
			map.majorID = Integer.parseInt(getNextString(bufReader));

			//Beacons
			int tmpSize = Integer.parseInt(getNextString(bufReader));
			for( int i = 0 ; i < tmpSize ; i++ ) {
				MapBeaconIR beacon = new MapBeaconIR();
				beacon.name = getNextString(bufReader);
				beacon.id = Integer.parseInt(getNextString(bufReader));
				beacon.minorID = Integer.parseInt(getNextString(bufReader));
				beacon.positionX = Double.parseDouble(getNextString(bufReader));
				beacon.positionY = Double.parseDouble(getNextString(bufReader));
				beacon.description = getNextString(bufReader);
				map.beacons.put(beacon.id, beacon);

				map.beaconMap.put(beacon.minorID, beacon.id);
			}

			//Ordinary places
			tmpSize = Integer.parseInt(getNextString(bufReader));
			for( int i = 0 ; i < tmpSize ; i++ ) {
				String placeName = getNextString(bufReader);
				List<Integer> tmpList = new ArrayList<>();

				int tmpSize2 = Integer.parseInt(getNextString(bufReader));
				for( int j = 0 ; j < tmpSize2 ; j++ ) {
					tmpList.add(Integer.parseInt(getNextString(bufReader)));
				}

				map.ordinaryPlaces.put(placeName, tmpList);
			}

			//Special places
			tmpSize = Integer.parseInt(getNextString(bufReader));
			for( int i = 0 ; i < tmpSize ; i++ ) {
				String placeName = getNextString(bufReader);
				List<Integer> tmpList = new ArrayList<>();

				int tmpSize2 = Integer.parseInt(getNextString(bufReader));
				for( int j = 0 ; j < tmpSize2 ; j++ ) {
					tmpList.add(Integer.parseInt(getNextString(bufReader)));
				}

				map.specialPlaces.put(placeName, tmpList);
			}

			bufReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Failed to load map '" + map.name + "'", Toast.LENGTH_LONG).show();
		}
	}
	//Reads the next piece of data from a file (items are separated by SEP_CHAR)
	private String getNextString(BufferedReader bufReader) {
		String tmp = "";
		try {
			int c;
			boolean lastWasBS = false;//Last was backslash

			while (true) {
				c = bufReader.read();
				if (c < 0 || (!lastWasBS && (c == SEP_CHAR.charAt(0)))) break;
				if (c != '\\' || lastWasBS) tmp += (char) c;
				lastWasBS = (c == '\\' && !lastWasBS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	private void saveLocalMap( MapIR map ) {
		try {
			Writer writer = new OutputStreamWriter(
					new FileOutputStream(getFile("maps/" + map.id + ".mapDat")), "UTF-8");

			writer.write(formatOutString(map.name) + SEP_CHAR);
			writer.write(map.id + SEP_CHAR);
			writer.write(formatOutString(map.description) + SEP_CHAR);
			writer.write(formatOutString(map.imagePath) + SEP_CHAR);
			writer.write(map.majorID + SEP_CHAR);

			//Beacons
			writer.write(map.beacons.size() + SEP_CHAR);
			for (int i = 0; i < map.beacons.size(); i++) {
				MapBeaconIR beacon = map.beacons.valueAt(i);

				writer.write(formatOutString(beacon.name) + SEP_CHAR);
				writer.write(beacon.id + SEP_CHAR);
				writer.write(beacon.minorID + SEP_CHAR);
				writer.write(beacon.positionX + SEP_CHAR);
				writer.write(beacon.positionY + SEP_CHAR);
				writer.write(formatOutString(beacon.description) + SEP_CHAR);
			}

			//Ordinary places
			writer.write(map.ordinaryPlaces.size() + SEP_CHAR);
			for (Map.Entry<String, List<Integer>> entry : map.ordinaryPlaces.entrySet()) {
				writer.write(formatOutString(entry.getKey()) + SEP_CHAR);
				writer.write(entry.getValue().size() + SEP_CHAR);
				for (Integer id : entry.getValue()) {
					writer.write(id + SEP_CHAR);
				}
			}
			//Special places
			writer.write(map.specialPlaces.size() + SEP_CHAR);
			for (Map.Entry<String, List<Integer>> entry : map.specialPlaces.entrySet()) {
				writer.write(formatOutString(entry.getKey()) + SEP_CHAR);
				writer.write(entry.getValue().size() + SEP_CHAR);
				for (Integer id : entry.getValue()) {
					writer.write(id + SEP_CHAR);
				}
			}



			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Failed to save map '" + map.name + "'", Toast.LENGTH_LONG).show();
		}
	}
	private String formatOutString( String string ) {
		string = string.replace("\\", "\\\\");
		string = string.replace(SEP_CHAR, "\\"+ SEP_CHAR);
		return string;
	}

	private void downloadMap( String mapName ) {
		//todo
	}

	private void getAllLocalMaps() {
		try {
			File []files = getFile("maps/").listFiles();
			List<MapDataImpl> list = new ArrayList<>();
			for (File file : files) {
				BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				list.add(new MapDataImpl(getNextString(bufReader), Integer.parseInt(getNextString(bufReader))));
				bufReader.close();
			}
			EventBus.getDefault().post(new MapUpdateEvent(MapUpdateEvent.EVENT_AVAIL_OFFLINE_MAPS_LOADED, list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findOnlineMap( String query ) {
		//todo
	}
}
