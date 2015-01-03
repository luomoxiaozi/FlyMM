package com.wj.LightActivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 滑动通知栏应用程序
 * 
 * @author wj
 *
 */
public class SlideService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent intents = new Intent(this,LightService.class);
		this.stopService(intents);
		MyApplication.getMyApplication().exit();
		return super.onStartCommand(intent, flags, startId);
	}
	
}
