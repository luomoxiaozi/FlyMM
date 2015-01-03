package com.wj.LightActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveInstance {
	
	private static SaveInstance saveInstance = new SaveInstance();
	
	private SaveInstance()
	{
		
	}
	
	public static SaveInstance getSaveInstance()
	{
		return saveInstance;
	}
	
	private void saveInstance(Context paramContext, String paramString,
			boolean paramBoolean) {
		SharedPreferences.Editor localEditor = paramContext
				.getSharedPreferences("lightSetting", 0).edit();
		localEditor.putBoolean(paramString, paramBoolean);
		localEditor.commit();
	}
	
	/**得到闪光灯频率*/
	public int getFlashRate(Context paramContext) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getString("flashRate", "1"));
	}
	
	/**得到自动关灯时间*/
	public int getLightCloseTime(Context paramContext) {
		return (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getString("closeLightTime", "6")))*300;
	}
	
	/**来单闪光灯服务开启*/
	public boolean getServiceOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("service_on", false);
	}
	
	/**信息闪光开启*/
	public boolean getMsgOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("message_on", false);
	}
	
	/**来单闪光开启*/
	public boolean getCallOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("call_on", false);
	}
	
	
	/**滑屏调节亮度*/
	public boolean getScreenOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("screen_on", true);
	}
	
	/**home键快捷开关*/
	public boolean getQuickOpen(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("quick_open", false);
	}
	
	/**打开震动*/
	public boolean getVibratorOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("vibrator_on", false);
	}

	/**关灯退出*/
	public boolean getAutoOff(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("auto_off", false);
	}

	/**开启亮灯*/
	public boolean getAutoOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("auto_on", false);
	}
	
	/**开启动画*/
	public boolean getAnimOpen(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("anim_open", false);
	}
	
	/**开灯提示*/
	public String getOpenNotify(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getString("open_notify", "unknown");
	}
	
	/**关灯提示*/
	public String getCloseNotify(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getString("close_notify", "unknown");
	}

	public boolean getScreenStatus(Context paramContext) {
		return paramContext.getSharedPreferences("lightSetting", 0).getBoolean(
				"screen_statu", true);
	}

	public void setScreenStatu(Context paramContext, boolean paramBoolean) {
		saveInstance(paramContext, "screen_statu", paramBoolean);
	}
}