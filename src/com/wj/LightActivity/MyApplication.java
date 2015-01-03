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
	 * �õ�Application����
	 * 
	 * @return
	 */
	public static MyApplication getMyApplication() {
		return myAppliaction;
	}

	/**
	 * �˳����򣬴���
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
	 * �ͷ�ռ�����
	 */
	public void releaseCamera() {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	/**
	 * �õ�Camera����
	 * 
	 * @return
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * �������е�ʱ�򣬵��ô˺���
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
	 * �õ��ֻ�����״̬
	 */
	private void getAudioModel() {
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		Config.audioNodel = audioManager.getRingerMode();
	}

//	/**
//	 * ��ʼ��������Ϣ
//	 */
//	private void getCallConfig() {
//		Config.isServiceOn = saveInstance.getServiceOn(this);
//		Config.isMsgOn = saveInstance.getMsgOn(this);
//		Config.isCallOn = saveInstance.getCallOn(this);
//	}

//	/**
//	 * ��ȡ�洢��Ϣ����
//	 */
//	public SaveInstance getSaveInstance() {
//		if (null == saveInstance) {
//			saveInstance = new SaveInstance();
//		}
//		return saveInstance;
//	}

}