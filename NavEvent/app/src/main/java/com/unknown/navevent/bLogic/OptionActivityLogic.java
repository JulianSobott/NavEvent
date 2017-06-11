package com.unknown.navevent.bLogic;

import android.content.Context;

import com.unknown.navevent.bLogic.events.ServiceToActivityEvent;
import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.OptionActivityLogicInterface;
import com.unknown.navevent.interfaces.OptionActivityUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;


public class OptionActivityLogic implements OptionActivityLogicInterface {
	private static final String TAG = "OptionActivityLogic";


	private OptionActivityUI mResponder = null;
	private ServiceInterface serviceInterface = ServiceInterface.getInstance();


	public OptionActivityLogic(OptionActivityUI responder) {
		mResponder = responder;
	}


	/////////////////////////////////////////////////////////
	// Lifecycle methods
	/////////////////////////////////////////////////////////

	@Override
	public void onCreate(Context context) {
		serviceInterface.onCreate(context);
	}

	@Override
	public void onDestroy() {
		serviceInterface.onDestroy();
	}

	@Override
	public void onStart() {
		EventBus.getDefault().register(this);

		Map<String, String> settingsMap = new HashMap<>();
		settingsMap.put("autoDownloadMaps", ServiceInterface.autoDownloadMaps + "");
		mResponder.currentSettings(settingsMap);
	}

	@Override
	public void onStop() {
		EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public void changeSetting(String name, String value) {

	}


	/////////////////////////////////////////////////////////
	// Event handling
	/////////////////////////////////////////////////////////

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ServiceToActivityEvent event) {
	}
}
