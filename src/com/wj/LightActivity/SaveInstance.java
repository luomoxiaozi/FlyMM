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
	
	/**�õ������Ƶ��*/
	public int getFlashRate(Context paramContext) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getString("flashRate", "1"));
	}
	
	/**�õ��Զ��ص�ʱ��*/
	public int getLightCloseTime(Context paramContext) {
		return (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getString("closeLightTime", "6")))*300;
	}
	
	/**��������Ʒ�����*/
	public boolean getServiceOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("service_on", false);
	}
	
	/**��Ϣ���⿪��*/
	public boolean getMsgOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("message_on", false);
	}
	
	/**�������⿪��*/
	public boolean getCallOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("call_on", false);
	}
	
	
	/**������������*/
	public boolean getScreenOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("screen_on", true);
	}
	
	/**home����ݿ���*/
	public boolean getQuickOpen(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("quick_open", false);
	}
	
	/**����*/
	public boolean getVibratorOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("vibrator_on", false);
	}

	/**�ص��˳�*/
	public boolean getAutoOff(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("auto_off", false);
	}

	/**��������*/
	public boolean getAutoOn(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("auto_on", false);
	}
	
	/**��������*/
	public boolean getAnimOpen(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getBoolean("anim_open", false);
	}
	
	/**������ʾ*/
	public String getOpenNotify(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext)
				.getString("open_notify", "unknown");
	}
	
	/**�ص���ʾ*/
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