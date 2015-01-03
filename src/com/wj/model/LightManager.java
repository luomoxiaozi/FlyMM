package com.wj.model;

import java.util.Timer;
import java.util.TimerTask;

import com.wj.LightActivity.Config;
import com.wj.LightActivity.LightActivity;
import com.wj.LightActivity.MyApplication;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LightManager {
	private static Parameters p;
	private static Timer timer;
	private static Timer closeLightTime;
	private static Camera camera = MyApplication.getMyApplication().getCamera();

	private static Handler handler;

	static class CloseThread extends Thread {
		public void run() {
			Looper.prepare();
			handler = new Handler() {
				public void handleMessage(Message msg) {
					LightManager.processOffFlash();
				}
			};
			Looper.loop();
		}
	}

	static {
		new CloseThread().start();
	}

	// 手电筒闪光开启
	public static void processOnFlash() {
		closeLightTime = new Timer();
		System.out.println("camera:   " + camera);
		try {
			if (camera == null)
				camera = Camera.open();
			p = camera.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(p);
			camera.startPreview();
			closeLightWhen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 手电筒闪光关闭
	public static void processOffFlash() {
		System.out.println("processOffFlash"+camera);
		try {
			if (camera == null)
				camera = Camera.open();
			p = camera.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(p);
			camera.stopPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开启手电筒
	 */
	public static void startLight() {
		processOnFlash();
		Config.isFlash = false;
		Config.isOpen = true;
	}

	public static void openFlash() {
		Config.isFlash = true;
		Config.isOpen = false;
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				processOnFlash();
				handler.sendEmptyMessageDelayed(0, 500 / Config.flashRate);
			}
		};
		timer.schedule(task, 10, 1000 / Config.flashRate);
	}

	/**
	 * 定时关灯
	 */
	private static void closeLightWhen() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// 退出应用程序
				MyApplication.getMyApplication().exit();
			}
		};
		if (closeLightTime != null)
			closeLightTime.schedule(task, Config.closeLightTime * 1000);
	}

	/**
	 * 释放手电筒
	 */
	public static void releaseLight() {
		if(LightActivity.myHandler != null)
		LightActivity.myHandler.sendEmptyMessage(2);
		processOffFlash();
		LightNotification.releaseNotifation();
		if (timer != null)
			timer.cancel();
		if (closeLightTime != null)
			closeLightTime.cancel();
		handler.removeMessages(0);
		Config.isOpen = false;
		Config.isFlash = false;
	}
}
