/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package org.selfkleptomaniac.mod.tibrightness;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiWindowProxy;
import org.appcelerator.kroll.common.Log;

import android.view.Window;
import android.view.WindowManager;

@Kroll.module(name="Tibrightnessandroid", id="org.selfkleptomaniac.mod.tibrightness")
public class TibrightnessandroidModule extends KrollModule
{

	// Standard Debugging variables
	private static final String TAG = "TibrightnessandroidModule";
	private float newBrightness;

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;
	
	public TibrightnessandroidModule()
	{
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		Log.d(TAG, "inside onAppCreate");
		// put module init code that needs to run when the application is created
	}

	// Methods
	@Kroll.method
	public Float getBrightness(TiWindowProxy win){
		Window window = win.getActivity().getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		float brightness = lp.screenBrightness;
		return brightness;
	}
	
	@Kroll.method
	public float setBrightness(KrollDict dict){
		newBrightness = 0.5f;
		if (dict.containsKey("brightness")) {
			newBrightness = Float.valueOf(dict.get("brightness").toString());
			if(newBrightness > 1.0f){
				newBrightness = 1.0f;
			}else if(newBrightness < 0.0f){
				newBrightness = 0.0f;
			}			
		}
		TiWindowProxy win = (TiWindowProxy)dict.get("window");
		win.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				Window window = getActivity().getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.screenBrightness = newBrightness;
				window.setAttributes(lp);
			}
		});
		return newBrightness;
	}
	
	@Kroll.method
	public float up(TiWindowProxy win){
		float brightness = getBrightness(win) + 0.1f;
		return update(brightness, win);
	}
	
	@Kroll.method
	public float down(TiWindowProxy win){
		float brightness = getBrightness(win) - 0.1f;
		return update(brightness, win);
	}
	
	private float update(float brightness, TiWindowProxy win){
		KrollDict dict = new KrollDict();
		dict.put("brightness", brightness);
		dict.put("window", win);
		return setBrightness(dict);
	}
}

