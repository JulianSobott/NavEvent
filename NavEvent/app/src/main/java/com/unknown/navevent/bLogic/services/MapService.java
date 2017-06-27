package com.unknown.navevent.bLogic.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;

import com.unknown.navevent.bLogic.events.ServiceInterfaceEvent;
import com.unknown.navevent.bLogic.events.MapServiceEvent;
import com.unknown.navevent.bLogic.events.MapUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
	private int mBackgroundThreadShouldWait = 5;//Milliseconds to sleep in thread. Will be automatically set longer after start, to stop battery drain.
	private int mBackgroundThreadSlowdownCount = 0;

	private Queue<MapServiceEvent> backgroundTaskQueue = new ConcurrentLinkedQueue<>();

	private final static String SEP_CHAR = ";";//Separates items in a file
	private final static String URL_TO_SERVER = "navevent.ddns.net";
	private final static int TIMEOUT_MILLIS = 10000;

	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate() {
		super.onCreate();


		//Thread
		mBackgroundThreadShouldStop.set(false);

		mBackgroundThread = new Thread(new Runnable() {
			@Override
			public void run() {
				MapServiceEvent task;
				try {
					mBackgroundThreadRunning.set(true);
					while (!mBackgroundThreadShouldStop.get()) {//Loop until thread should stop
						Thread.sleep(mBackgroundThreadShouldWait);//Wait to save cpu time

						if (mBackgroundThreadShouldWait < 1000) {//Slow down event. From 5 ms to 100 ms after 500 ms. From 100 ms to 1 s after 10 s.
							mBackgroundThreadSlowdownCount++;
							if (mBackgroundThreadSlowdownCount >= 100) {
								if (mBackgroundThreadShouldWait == 5)
									mBackgroundThreadShouldWait = 100;
								else mBackgroundThreadShouldWait = 1000;
								mBackgroundThreadSlowdownCount = 0;
							}
						}

						while ((task = backgroundTaskQueue.poll()) != null) {
							mBackgroundThreadShouldWait = 5;//Set to high frequency

							if (task.task == MapServiceEvent.Type.EVENT_LOAD_MAP_LOCAL) {
								Log.i(TAG, "mapThread: EVENT_LOAD_MAP_LOCAL");
								loadLocalMap(task.mapID);
							} else if (task.task == MapServiceEvent.Type.EVENT_SAVE_MAP_LOCAL) {
								Log.i(TAG, "mapThread: EVENT_SAVE_MAP_LOCAL");
								saveLocalMap(task.map);
							} else if (task.task == MapServiceEvent.Type.EVENT_DOWNLOAD_MAP) {
								Log.i(TAG, "mapThread: EVENT_DOWNLOAD_MAP");
								downloadMap(task.mapID);
							} else if (task.task == MapServiceEvent.Type.EVENT_GET_ALL_LOCAL_MAPS) {
								Log.i(TAG, "mapThread: EVENT_GET_ALL_LOCAL_MAPS");
								getAllLocalMaps();
							} else if (task.task == MapServiceEvent.Type.EVENT_FIND_ONLINE_MAP_BY_QUERY) {
								Log.i(TAG, "mapThread: EVENT_FIND_ONLINE_MAP_BY_QUERY");
								findOnlineMap(task.query);
							} else if (task.task == MapServiceEvent.Type.EVENT_FIND_ONLINE_MAP_BY_ID) {
								Log.i(TAG, "mapThread: EVENT_FIND_ONLINE_MAP_BY_ID");
								findOnlineMap(task.mapID);
							} else if (task.task == MapServiceEvent.Type.EVENT_STOP_SELF) {
								Log.i(TAG, "mapThread: EVENT_STOP_SELF");
								mBackgroundThreadShouldStop.set(true);
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

		EventBus.getDefault().register(this);

		EventBus.getDefault().post(new ServiceInterfaceEvent(ServiceInterfaceEvent.Type.EVENT_MAP_SERVICE_STARTED));
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
			while (mBackgroundThreadRunning.get()) Thread.sleep(10);
			mBackgroundThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.ASYNC)
	public void onMessageEvent(MapServiceEvent event) {
		backgroundTaskQueue.add(event);
	}


	/////////////////////////////////////////////////////////
	// Methods to handle tasks
	/////////////////////////////////////////////////////////


	//Returns the file for the specified path
	private File getFile(String filename) {
		File file = new File(this.getExternalFilesDir(null).getAbsolutePath() + "/" + filename);//Public/debug
		//File file = new File(mContext.getFilesDir() + filename);//Private
		file.getParentFile().mkdirs();//Create directories if the parent dir doesn't exit.
		return file;
	}

	private void loadLocalMap(int mapID) {
		try {
			MapIR map = new MapIR();

			if (!getFile("maps/" + mapID + ".mapDat").exists()) {
				Toast.makeText(this, "Map '" + map.name + "' does not exist!", Toast.LENGTH_LONG).show();
				return;
			}
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile("maps/" + mapID + ".mapDat")), "UTF-8"));

			map.name = getNextString(bufReader);
			map.id = Integer.parseInt(getNextString(bufReader));
			map.majorID = Integer.parseInt(getNextString(bufReader));
			map.description = getNextString(bufReader);
			map.imagePath = getNextString(bufReader);
			{//Load image
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				map.image = BitmapFactory.decodeFile(getFile("mapImgs/" + map.imagePath).getPath(), options);
			}

			//Beacons
			int tmpSize = Integer.parseInt(getNextString(bufReader));
			for (int i = 0; i < tmpSize; i++) {
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
			for (int i = 0; i < tmpSize; i++) {
				String placeName = getNextString(bufReader);
				List<Integer> tmpList = new ArrayList<>();

				int tmpSize2 = Integer.parseInt(getNextString(bufReader));
				for (int j = 0; j < tmpSize2; j++) {
					tmpList.add(Integer.parseInt(getNextString(bufReader)));
				}

				map.ordinaryPlaces.put(placeName, tmpList);
			}

			//Special places
			tmpSize = Integer.parseInt(getNextString(bufReader));
			for (int i = 0; i < tmpSize; i++) {
				String placeName = getNextString(bufReader);
				List<Integer> tmpList = new ArrayList<>();

				int tmpSize2 = Integer.parseInt(getNextString(bufReader));
				for (int j = 0; j < tmpSize2; j++) {
					tmpList.add(Integer.parseInt(getNextString(bufReader)));
				}

				map.specialPlaces.put(placeName, tmpList);
			}

			bufReader.close();

			List<MapIR> retList = new ArrayList<>();
			retList.add(map);
			EventBus.getDefault().post(new MapUpdateEvent(MapUpdateEvent.Type.EVENT_MAP_LOADED, retList));
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Failed to load map '" + mapID + "'", Toast.LENGTH_LONG).show();
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

	private void saveLocalMap(MapIR map) {
		try {
			Writer writer = new OutputStreamWriter(
					new FileOutputStream(getFile("maps/" + map.id + ".mapDat")), "UTF-8");

			writer.write(formatOutString(map.name) + SEP_CHAR);
			writer.write(map.id + SEP_CHAR);
			writer.write(map.majorID + SEP_CHAR);
			writer.write(formatOutString(map.description) + SEP_CHAR);
			writer.write(formatOutString(map.imagePath) + SEP_CHAR);

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

	private String formatOutString(String string) {
		string = string.replace("\\", "\\\\");
		string = string.replace(SEP_CHAR, "\\" + SEP_CHAR);
		return string;
	}

	private void downloadMap(int mapID) {
		try {
			MapIR newMap = new MapIR();

			//Connect
			URL url = new URL("http://" + URL_TO_SERVER + "/php/includes/app_request_get_map.php");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(TIMEOUT_MILLIS);

			OutputStream outputStream = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

			String postData = URLEncoder.encode("mapID", "UTF-8") + "=" +
					URLEncoder.encode("" + mapID, "UTF-8");

			writer.write(postData);
			writer.flush();
			writer.close();
			outputStream.close();

			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String queryRespond = reader.readLine();

			if (queryRespond.equals("found map"))//Found map id on server.
			{
				//Write data to map
				newMap.name = reader.readLine();
				newMap.id = Integer.parseInt(reader.readLine());
				newMap.majorID = Integer.parseInt(reader.readLine());
				newMap.description = reader.readLine();
				newMap.imagePath = reader.readLine();

				//Beacons
				int tmpSize = Integer.parseInt(reader.readLine());
				for (int i = 0; i < tmpSize; i++) {
					MapBeaconIR beacon = new MapBeaconIR();
					beacon.name = reader.readLine();
					beacon.id = Integer.parseInt(reader.readLine());
					beacon.minorID = Integer.parseInt(reader.readLine());
					beacon.positionX = Double.parseDouble(reader.readLine());
					beacon.positionY = Double.parseDouble(reader.readLine());
					beacon.description = reader.readLine();

					newMap.beacons.put(beacon.id, beacon);
					newMap.beaconMap.put(beacon.minorID, beacon.id);
				}

				//Ordinary places
				tmpSize = Integer.parseInt(reader.readLine());
				for (int i = 0; i < tmpSize; i++) {
					String placeName = reader.readLine();
					List<Integer> tmpList = new ArrayList<>();

					int tmpSize2 = Integer.parseInt(reader.readLine());
					for (int j = 0; j < tmpSize2; j++) {
						tmpList.add(Integer.parseInt(reader.readLine()));
					}

					newMap.ordinaryPlaces.put(placeName, tmpList);
				}

				//Special places
				tmpSize = Integer.parseInt(reader.readLine());
				for (int i = 0; i < tmpSize; i++) {
					String placeName = reader.readLine();
					List<Integer> tmpList = new ArrayList<>();

					int tmpSize2 = Integer.parseInt(reader.readLine());
					for (int j = 0; j < tmpSize2; j++) {
						tmpList.add(Integer.parseInt(reader.readLine()));
					}

					newMap.specialPlaces.put(placeName, tmpList);
				}

				//Download image
				downloadImage(newMap.imagePath);

				//Save downloaded map
				saveLocalMap(newMap);

				//Update available local maps
				getAllLocalMaps();

				List<MapIR> retList = new ArrayList<>();
				retList.add(newMap);
				EventBus.getDefault().post(new MapUpdateEvent(MapUpdateEvent.Type.EVENT_MAP_DOWNLOADED, retList));

			} else {
				//Load the map locally if connection failed
				loadLocalMap(mapID);
			}
			reader.close();
			inputStream.close();
			connection.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
			EventBus.getDefault().post(new MapUpdateEvent(MapUpdateEvent.Type.EVENT_MAP_DOWNLOAD_FAILED, e.getLocalizedMessage()));

			//Load the map locally if connection failed
			loadLocalMap(mapID);
		}
	}

	private void downloadImage(String path) {
		try {
			//Connect
			URL url = new URL("http://" + URL_TO_SERVER + "/uploads/" + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setConnectTimeout(TIMEOUT_MILLIS);

			InputStream inputStream = connection.getInputStream();

			FileOutputStream outputStream = new FileOutputStream(getFile("mapImgs/" + path));

			int bytesRead;
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();
			connection.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getAllLocalMaps() {
		try {
			File[] files = getFile("maps/").listFiles();
			List<MapIR> list = new ArrayList<>();
			if (files != null) {
				for (File file : files) {
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					list.add(new MapIR(getNextString(bufReader), Integer.parseInt(getNextString(bufReader)), Integer.parseInt(getNextString(bufReader))));
					bufReader.close();
				}
			}
			EventBus.getDefault().post(new MapUpdateEvent(MapUpdateEvent.Type.EVENT_AVAIL_OFFLINE_MAPS_LOADED, list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findOnlineMap(String query) {
		List<MapIR> foundMaps = new ArrayList<>();

		try {
			//Connect
			URL url = new URL("http://" + URL_TO_SERVER + "/php/includes/app_request_find_map.php");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(TIMEOUT_MILLIS);

			OutputStream outputStream = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

			String postData = URLEncoder.encode("query", "UTF-8") + "=" +
					URLEncoder.encode(query, "UTF-8");

			writer.write(postData);
			writer.flush();
			writer.close();
			outputStream.close();

			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			int tmpSize = Integer.parseInt(reader.readLine());
			for (int i = 0; i < tmpSize; i++) {
				MapIR map = new MapIR();
				map.name = reader.readLine();
				map.id = Integer.parseInt(reader.readLine());
				map.majorID = Integer.parseInt(reader.readLine());
				map.description = reader.readLine();

				foundMaps.add(map);
			}

			reader.close();
			inputStream.close();
			connection.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//Return map if found any
		EventBus.getDefault().post(new MapUpdateEvent(MapUpdateEvent.Type.EVENT_FOUND_ONLINE_MAPS, foundMaps));
	}

	private void findOnlineMap(int majorID) {
		List<MapIR> foundMaps = new ArrayList<>();

		try {
			//Connect
			URL url = new URL("http://" + URL_TO_SERVER + "/php/includes/app_request_find_map.php");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(TIMEOUT_MILLIS);

			OutputStream outputStream = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

			String postData = URLEncoder.encode("majorID", "UTF-8") + "=" +
					URLEncoder.encode("" + majorID, "UTF-8");

			writer.write(postData);
			writer.flush();
			writer.close();
			outputStream.close();

			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			int tmpSize = Integer.parseInt(reader.readLine());
			for (int i = 0; i < tmpSize; i++) {
				MapIR map = new MapIR();
				map.name = reader.readLine();
				map.id = Integer.parseInt(reader.readLine());
				map.majorID = Integer.parseInt(reader.readLine());
				map.description = reader.readLine();

				foundMaps.add(map);
			}

			EventBus.getDefault().post(new MapUpdateEvent(MapUpdateEvent.Type.EVENT_FOUND_ONLINE_MAP_BY_ID, foundMaps));

			reader.close();
			inputStream.close();
			connection.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		//Return map if found any
	}
}
