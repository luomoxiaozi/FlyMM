package com.wj.model;

import android.app.Activity;
import android.view.WindowManager;

public class ScreenLight {
	
	/**
	 * �õ���Ļ����
	 * @param activity activity���ڵ���Ļ
	 * @return
	 */
	public static int getScreenLight(Activity activity) {
		return Brightness.getScreenBrightness(activity);
	}

	/**
	 * ������Ļ������
	 * @param activity activity���ڵ���Ļ
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
