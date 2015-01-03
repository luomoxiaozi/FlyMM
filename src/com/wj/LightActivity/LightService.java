package com.wj.LightActivity;

import com.wj.model.LightManager;
import com.wj.model.LightNotification;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

public class LightService extends Service {

	private static Handler stopHandler;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		stopHandler = LightActivity.myHandler;
		LightNotification.showNotification(this,LightActivity.class);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle bundle = intent.getExtras();
		String str = bundle.getString("intent");
		if (str.equals("isLight")) {
			LightManager.startLight();
			stopHandler.sendEmptyMessage(3);
		} else if (str.equals("isFlash")) {
			LightManager.openFlash();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopHandler.sendEmptyMessage(2);
		LightManager.releaseLight();
	}

}
