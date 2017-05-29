package com.unknown.navevent.bLogic;

import android.content.Context;

import com.unknown.navevent.interfaces.AdminAreaUI;
import com.unknown.navevent.interfaces.OptionActivityLogicInterface;
import com.unknown.navevent.interfaces.OptionActivityUI;

import org.greenrobot.eventbus.EventBus;

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
		//EventBus.getDefault().register(this);//todo delete if is not needed

		serviceInterface.onCreate(context);


		Map<String, String> settingsMap = new HashMap<>();
		settingsMap.put("autoDownloadMaps", ServiceInterface.autoDownloadMaps +"");
		mResponder.currentSettings(settingsMap);
	}

	@Override
	public void onDestroy() {
		serviceInterface.onDestroy();

		//EventBus.getDefault().unregister(this);
	}


	/////////////////////////////////////////////////////////
	// UI calls
	/////////////////////////////////////////////////////////

	@Override
	public void changeSetting(String name, String value) {

	}
}
