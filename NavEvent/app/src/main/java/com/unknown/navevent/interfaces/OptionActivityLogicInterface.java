package com.unknown.navevent.interfaces;

import android.content.Context;

public interface OptionActivityLogicInterface {

	//Should be called on creation.
	void onCreate(Context context);
	//Should be called on destruction.
	void onDestroy();

	//Will change the a single setting to a new value
	void changeSetting(String name, String value);
}
