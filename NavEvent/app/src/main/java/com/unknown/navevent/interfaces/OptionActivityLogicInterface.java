package com.unknown.navevent.interfaces;

import android.content.Context;

public interface OptionActivityLogicInterface {

	//Should be called on creation.
	void onCreate(Context context);
	//Should be called on destruction.
	void onDestroy();

	//Should be called on start.
	void onStart();
	//Should be called on stop.
	void onStop();

	//Will change the a single setting to a new value
	void changeSetting(String name, String value);
}
