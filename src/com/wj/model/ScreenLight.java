package com.wj.model;

import android.app.Activity;
import android.view.WindowManager;

public class ScreenLight {
	
	/**
	 * 得到屏幕亮度
	 * @param activity activity所在的屏幕
	 * @return
	 */
	public static int getScreenLight(Activity activity) {
		return Brightness.getScreenBrightness(activity);
	}

	/**
	 * 设置屏幕的亮度
	 * @param activity activity所在的屏幕
	 * @param screenLight
	 */
	public static void setScreenLight(Activity activity, int screenLight) {
		WindowManager.LayoutParams params = activity.getWindow()
				.getAttributes();
		params.screenBrightness = screenLight / 255f;
		activity.getWindow().setAttributes(params);
		Brightness.saveBrightness(activity.getContentResolver(), screenLight);
	}
	
}
