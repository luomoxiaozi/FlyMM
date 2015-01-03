package com.wj.observer;

import com.wj.LightActivity.Config;
import com.wj.LightActivity.LightService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HomeKeyBroadcastReceiver extends BroadcastReceiver {
	final String SYSTEM_DIALOG_REASON_KEY = "reason";
	// ����Home��
	final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
	// ����Home��
	final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
	private Context context;

	public HomeKeyBroadcastReceiver(Context context) {
		this.context = context;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Config.isHomeLight = false;
		if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			if (!Config.isQuickOpen)
				return;
			String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
			if (reason != null && !Config.isHomeLight) {
				if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
					// ����
					lightControl();
				} else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
					// ��ʱ�䰴��
					startService();
				}
			}
		}else if(action.equals(Intent.ACTION_CAMERA_BUTTON)) {
			
		}
	}

	/**
	 * ��ʱ�䰴�°�ť
	 */
	private void startService() {
		System.out.println("˫����homeKey");
		Intent intent = new Intent(context, LightService.class);
		intent.putExtra("intent", "isLight");
		if (Config.isOpen) {
			context.stopService(intent);
//			MyApplication.getMyApplication().releaseCamera();
			Config.isOpen = false;
		} else {
			context.startService(intent);
			Config.isOpen = true;
		}
	}

	/**
	 * ���������
	 */
	private void lightControl() {
		if ((System.currentTimeMillis() - Config.homeTime) > 500) {
			Config.homeTime = System.currentTimeMillis();
		} else {
			if ((System.currentTimeMillis() - Config.homeTime) < 100)
				return;
			startService();
		}
	}
}