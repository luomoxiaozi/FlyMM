package com.wj.LightActivity;

public class Config {
	public static boolean isOpen = false;//������Ƿ��
	public static boolean hasFlash = false;//������Ƿ����
	public static boolean isOnCreate = false;//�Ƿ񴴽���LightActivity,�����ж�����ƹ�Ĺر�
	public static boolean isFlash;//�Ƿ�����
	public static boolean isQuickOpen;//��ݿ����Ƿ��
	public static boolean isVibratorOn;//�Ƿ�����
	public static boolean isHomeLight;//�Ƿ���Ե��Home��������Ļ
	public static boolean isServiceOn;//�Ƿ����������������
	public static boolean isMsgOn;//�Ƿ����˶����������
	public static boolean isCallOn;//�Ƿ����������������
	public static boolean isAutoBrightness;//�Ƿ�Ϊ�Զ���������
	public static int number = 0;//ջ����Ƭ����Ŀ
	public static int flashRate = 1;//ÿ����������
	public static int closeLightTime = 600;//�Զ��ص�ʱ��
	public static long homeTime = 0;//����home����ʱ��
	public static int audioNodel;
	public static String openToast = "�������ŵ�ҹ����ĺ���ů";
	public static String closeToast = "û������������ĺùµ�";  //#C5ECCA����  #FF0000��ɫ "#ECFFFF"
	public final static String color[] = {"#000000","#BC8F8F","#FFFBF0" };//�����ף�#FFFBF0  õ��죺#BC8F8F �����ڣ�#585756
	public static final long[] pattern = { 100, 400}; // ֹͣ ���� ֹͣ ����
	public static final int MSG_FLASH_TIME = 3;//��������ƴ���
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";//�յ����ŵĹ㲥�¼�
}