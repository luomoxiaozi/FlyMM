package com.wj.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.wj.LightActivity.SlideService;
import com.wj.LightActivity.Config;
import com.wj.LightActivity.R;

public class LightNotification {
	
	private static NotificationManager nm;
	
	/**
	 * 得到通知栏对象
	 * @param context
	 * @return
	 */
	private static NotificationManager getNotificationManager(Context context) {
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		return nm;
	}
	
	/**
	 * 显示通知栏
	 * @param context
	 * @param clickClass 点击转向的类
	 * @param slideClass 滑动转向的类
	 */
	public static void showNotification(Context context,Class<?>clickClass) {
		nm = getNotificationManager(context);
		Notification.Builder builder = new Notification.Builder(
				context);
		builder.setAutoCancel(false);
		int defult = Config.isVibratorOn?-1:32;
		builder.setDefaults(defult);
		builder.setTicker("FlyMM手电已开启");
		builder.setContentTitle("FlyMM手电");
		builder.setContentText("FlyMM手电正在为您服务");
		builder.setWhen(System.currentTimeMillis());
		
		builder.setSmallIcon(R.drawable.ic_launcher);
		// 点击通知栏时候的事件
		Intent intent = new Intent(context, clickClass);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		builder.setContentIntent(pendingIntent);

		// 滑动清除通知栏时候的事件
		Intent deleteIntent = new Intent(context, SlideService.class);
		int deleteCode = (int) SystemClock.uptimeMillis();//避免冲突，重新创建一个
		PendingIntent deletePendingIntent = PendingIntent.getService(context,
				deleteCode, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setDeleteIntent(deletePendingIntent);
		nm.notify(1, builder.getNotification());
	}
	
	/**
	 * 关闭通知栏
	 */
	public static void releaseNotifation() {
		if (nm != null)
			nm.cancel(1);
	}
}
