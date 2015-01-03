package com.wj.LightActivity;

import android.app.Application;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Process;

import com.wj.model.LightNotification;

public class MyApplication extends Application {
	private static MyApplication myAppliaction = null;
	private Camera camera = null;
//	private SaveInstance saveInstance;

	private MyApplication() {
		// Exists only to defeat instantiation.
	}

	/**
	 * 得到Application对象
	 * 
	 * @return
	 */
	public static MyApplication getMyApplication() {
		return myAppliaction;
	}

	/**
	 * 退出程序，处理
	 */
	public void exit() {
		try {
			LightNotification.releaseNotifation();
			// LightActivity.releaseWakeLock();
			releaseCamera();
			Process.killProcess(Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放占用相机
	 */
	public void releaseCamera() {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	/**
	 * 得到Camera对象
	 * 
	 * @return
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * 程序运行的时候，调用此函数
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		getAudioModel();
		try {
			camera = Camera.open();
			Config.hasFlash = true;
		} catch (Exception e) {
			Config.hasFlash = false;
		}
		myAppliaction = this;
//		getCallConfig();
	}

	/**
	 * 得到手机铃音状态
	 */
	private void getAudioModel() {
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		Config.audioNodel = audioManager.getRingerMode();
	}

//	/**
//	 * 初始化配置信息
//	 */
//	private void getCallConfig() {
//		Config.isServiceOn = saveInstance.getServiceOn(this);
//		Config.isMsgOn = saveInstance.getMsgOn(this);
//		Config.isCallOn = saveInstance.getCallOn(this);
//	}

//	/**
//	 * 获取存储信息对象
//	 */
//	public SaveInstance getSaveInstance() {
//		if (null == saveInstance) {
//			saveInstance = new SaveInstance();
//		}
//		return saveInstance;
//	}

}