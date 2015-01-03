package com.wj.observer;

import java.util.Timer;
import java.util.TimerTask;

import com.wj.LightActivity.Config;
import com.wj.LightActivity.MyApplication;
import com.wj.LightActivity.SaveInstance;
import com.wj.model.LightManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	private int config;
	private static Timer msg_Time;

	@Override
	public void onReceive(Context context, Intent intent) {
		config = 0;
		if (intent.getAction().equals(Config.SMS_RECEIVED_ACTION)) {
			if (Config.isServiceOn && Config.isMsgOn && !Config.isOpen
					&& !Config.isFlash) {
				// 收到短信dd
				if (Config.flashRate == 1)
					Config.flashRate = SaveInstance.getSaveInstance().getFlashRate(context);
				LightManager.openFlash();
				messageFlash();
			}
			//没有开启服务，占用手电筒
			if((!Config.isServiceOn||!Config.isMsgOn)&&!Config.isOnCreate) {
				MyApplication.getMyApplication().exit();
			}
		}
	}

	/**
	 * 收到信息闪光
	 */
	private void messageFlash() {
		msg_Time = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (config == Config.MSG_FLASH_TIME) {
					msg_Time.cancel();
					if (Config.isOnCreate) {
						LightManager.releaseLight();
					} else {
						MyApplication.getMyApplication().exit();
					}
				}
				config++;
			}
		};
		msg_Time.schedule(task, 0, 1000 / Config.flashRate);
	}

}
