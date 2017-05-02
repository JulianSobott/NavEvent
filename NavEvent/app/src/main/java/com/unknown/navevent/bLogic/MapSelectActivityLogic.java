package com.unknown.navevent.bLogic;

import android.content.Context;

import com.unknown.navevent.interfaces.MainActivityUI;
import com.unknown.navevent.interfaces.MapSelectActivityLogicInterface;
import com.unknown.navevent.interfaces.MapSelectActivityUI;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MapSelectActivityLogic implements MapSelectActivityLogicInterface {


	private MapSelectActivityUI mResponder = null;

	private ServiceInterface serviceInterface = new ServiceInterface();


	public MapSelectActivityLogic(MapSelectActivityUI responder) {
		mResponder = responder;
	}


	@Override
	public void onCreate(Context context) {
		//EventBus.getDefault().register(this);

		serviceInterface.onCreate(context);
	}

	@Override
	public void onDestroy() {
		serviceInterface.onDestroy();

		//EventBus.getDefault().unregister(this);
	}

	@Override
	public List<String> loadAvailableMaps() {
		return null;
	}

	@Override
	public boolean isOnline() {
		return true;
	}

	@Override
	public void loadOnlineMaps(String name) {

	}

	@Override
	public void downloadMap(String name) {

	}

	@Override
	public boolean setActiveMap(String name) {
		return false;
	}
}
