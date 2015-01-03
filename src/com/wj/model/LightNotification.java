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
	 * �õ�֪ͨ������
	 * @param context
	 * @return
	 */
	private static NotificationManager getNotificationManager(Context context) {
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		return nm;
	}
	
	/**
	 * ��ʾ֪ͨ��
	 * @param context
	 * @param clickClass ���ת�����
	 * @param slideClass ����ת�����
	 */
	public static void showNotification(Context context,Class<?>clickClass) {
		nm = getNotificationManager(context);
		Notification.Builder builder = new Notification.Builder(
				context);
		builder.setAutoCancel(false);
		int defult = Config.isVibratorOn?-1:32;
		builder.setDefaults(defult);
		builder.setTicker("FlyMM�ֵ��ѿ���");
		builder.setContentTitle("FlyMM�ֵ�");
		builder.setContentText("FlyMM�ֵ�����Ϊ������");
		builder.setWhen(System.currentTimeMillis());
		
		builder.setSmallIcon(R.drawable.ic_launcher);
		// ���֪ͨ��ʱ����¼�
		Intent intent = new Intent(context, clickClass);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		builder.setContentIntent(pendingIntent);

		// �������֪ͨ��ʱ����¼�
		Intent deleteIntent = new Intent(context, SlideService.class);
		int deleteCode = (int) SystemClock.uptimeMillis();//�����ͻ�����´���һ��
		PendingIntent deletePendingIntent = PendingIntent.getService(context,
				deleteCode, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setDeleteIntent(deletePendingIntent);
		nm.notify(1, builder.getNotification());
	}
	
	/**
	 * �ر�֪ͨ��
	 */
	public static void releaseNotifation() {
		if (nm != null)
			nm.cancel(1);
	}
}
