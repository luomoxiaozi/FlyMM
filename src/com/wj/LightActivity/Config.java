package com.wj.LightActivity;

public class Config {
	public static boolean isOpen = false;//闪光灯是否打开
	public static boolean hasFlash = false;//闪光灯是否可用
	public static boolean isOnCreate = false;//是否创建了LightActivity,用于判断来电灯光的关闭
	public static boolean isFlash;//是否闪光
	public static boolean isQuickOpen;//快捷开关是否打开
	public static boolean isVibratorOn;//是否开启震动
	public static boolean isHomeLight;//是否可以点击Home键点亮屏幕
	public static boolean isServiceOn;//是否开启了来电闪光服务
	public static boolean isMsgOn;//是否开启了短信闪光服务
	public static boolean isCallOn;//是否开启了来电闪光服务
	public static boolean isAutoBrightness;//是否为自动调节亮度
	public static int number = 0;//栈中碎片的数目
	public static int flashRate = 1;//每秒闪动次数
	public static int closeLightTime = 600;//自动关灯时间
	public static long homeTime = 0;//按下home键的时间
	public static int audioNodel;
	public static String openToast = "有你陪着的夜晚，真的好温暖";
	public static String closeToast = "没有你的日子里，真的好孤单";  //#C5ECCA保护  #FF0000红色 "#ECFFFF"
	public final static String color[] = {"#000000","#BC8F8F","#FFFBF0" };//象牙白：#FFFBF0  玫瑰红：#BC8F8F 象牙黑：#585756
	public static final long[] pattern = { 100, 400}; // 停止 开启 停止 开启
	public static final int MSG_FLASH_TIME = 3;//短信闪光灯次数
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";//收到短信的广播事件
}