package com.wj.observer;

import com.wj.LightActivity.Config;
import com.wj.LightActivity.MyApplication;
import com.wj.LightActivity.SaveInstance;
import com.wj.model.LightManager;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Config.flashRate == 1)
			Config.flashRate = SaveInstance.getSaveInstance().getFlashRate(context);
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Service.TELEPHONY_SERVICE);
		tm.listen(new Call_State_Listener(),
				PhoneStateListener.LISTEN_CALL_STATE);
	}

	class Call_State_Listener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			if((!Config.isServiceOn||!Config.isCallOn)&&!Config.isOnCreate) {
				MyApplication.getMyApplication().exit();
				return;
			}
			if (Config.isOpen || Config.isFlash) {
				LightManager.releaseLight();
				return;
			}
			if (!Config.isServiceOn
					|| (Config.isCallOn && Config.audioNodel == AudioManager.RINGER_MODE_SILENT)
					|| (!Config.isCallOn && Config.audioNodel != AudioManager.RINGER_MODE_SILENT))
				return;
			
			switch (state) {
			// À´µç×´Ì¬
			case TelephonyManager.CALL_STATE_RINGING:
				LightManager.openFlash();
				break;
			default:
				if (Config.isOnCreate) {
					LightManager.releaseLight();
				} else {
					MyApplication.getMyApplication().exit();
				}
			}
		}
	}

}
