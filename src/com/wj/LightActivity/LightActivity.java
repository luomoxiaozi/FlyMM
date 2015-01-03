package com.wj.LightActivity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.meizu.smartbar.SmartBarUtils;
import com.wj.model.Brightness;
import com.wj.model.ScreenLight;
import com.wj.model.VerticalSeekBar;
import com.wj.observer.HomeKeyBroadcastReceiver;

public class LightActivity extends Activity implements OnTouchListener,
		TabListener {
	private long exitTime = 0;
	private int width;// 屏幕的宽度

	private Animation operatingAnim;
	private int colorNumber;

	private static WakeLock mWakeLock;
	private static VerticalSeekBar verticalSeekBar;
	public static MyHandler myHandler;

	private HomeKeyBroadcastReceiver homeReceiver;
	private GestureDetector mGesture = null;

	private View background;
	private ImageButton light;

	private Tab flashTab;
	private Tab setTab;
	private Tab screenLightTab;

	private boolean autoOff;// 关灯退出
	private boolean animOpen;// 开启动画
	private boolean getScreenOn;// 开启屏幕灯

	SaveInstance saveInstance = SaveInstance.getSaveInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light_layout);
		initView();
		myHandler = new MyHandler();
		verticalSeekBar.setProgress(Brightness
				.getScreenBrightness(LightActivity.this));
		final IntentFilter homeFilter = new IntentFilter(
				Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		homeReceiver = new HomeKeyBroadcastReceiver(LightActivity.this);
		registerReceiver(homeReceiver, homeFilter);
		// 保持屏幕高亮
		PowerManager pm = (PowerManager) this
				.getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
				getClass().getName());
	}

	/**
	 * 解除锁屏
	 */
	public static void acquireWakeLock() {
		if (mWakeLock != null) {
			mWakeLock.acquire();
		}
	}

	/**
	 * 开启锁屏
	 */
	public static void releaseWakeLock() {
		if (mWakeLock != null) {
			mWakeLock.release();
		}
	}

	public class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				// 隐藏调节灯光条
				verticalSeekBar.setVisibility(View.INVISIBLE);
				break;
			case 1:
				closeLight();
				break;
			case 2:
				closeLightBack();
				break;
			case 3:
				openLightBack();
			}
		}
	}

	/**
	 * 获取View组件对象
	 */
	private void initView() {
		width = getWindowManager().getDefaultDisplay().getWidth();
		// mAudioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		mGesture = new GestureDetector(this, new ChangeGestureDetector());
		final ActionBar bar = getActionBar();
		// bar.setBackgroundDrawable(getResources().getDrawable(android.R.color.black));
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 设置ActionBar Tab显示在底栏
		SmartBarUtils.setActionBarTabsShowAtBottom(bar, true);
		// 设置顶栏不可见
		SmartBarUtils.setActionBarViewCollapsable(bar, true);
		// 设置返回键图片
		SmartBarUtils.setBackIcon(bar,
				getResources().getDrawable(R.drawable.ic_back));
		bar.setDisplayOptions(0);
		SmartBarUtils.setActionModeHeaderHidden(bar, true);

		flashTab = bar.newTab();
		flashTab.setIcon(R.drawable.flashoff);
		flashTab.setTabListener(this);

		screenLightTab = bar.newTab();
		screenLightTab.setIcon(R.drawable.screen_light_black);
		screenLightTab.setTabListener(this);

		setTab = bar.newTab();
		setTab.setIcon(R.drawable.ic_setting);
		setTab.setTabListener(this);

		bar.addTab(screenLightTab, false);
		bar.addTab(flashTab, false);
		bar.addTab(setTab, false);

		light = (ImageButton) findViewById(R.id.light);
		background = (View) findViewById(R.id.background);
		verticalSeekBar = (VerticalSeekBar) findViewById(R.id.verticalSeekBar);
		verticalSeekBar.setMax(255);
		light.setOnClickListener(new ButtonListener());
		saveInstance.getScreenStatus(this);
		operatingAnim = AnimationUtils.loadAnimation(this, R.anim.tip);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		if (saveInstance.getAutoOn(this)) {
			animOpen = saveInstance.getAnimOpen(this);
			openLight();
		}
		Config.isOnCreate = true;
		Config.isAutoBrightness = Brightness.isAutoBrightness(this);
		// System.out.println("oncreate。。。。。。。"+Config.isAutoBrightness);
	}

	@Override
	protected void onResume() {
		super.onResume();
		autoOff = saveInstance.getAutoOff(this);
		animOpen = saveInstance.getAnimOpen(this);
		getScreenOn = saveInstance.getScreenOn(this);
		Config.isQuickOpen = saveInstance.getQuickOpen(this);
		Config.isVibratorOn = saveInstance.getVibratorOn(this);

		Config.isServiceOn = saveInstance.getServiceOn(this);
		Config.isMsgOn = saveInstance.getMsgOn(this);
		Config.isCallOn = saveInstance.getCallOn(this);

		Config.flashRate = saveInstance.getFlashRate(this);
		Config.closeLightTime = saveInstance.getLightCloseTime(this);
		if (Config.closeLightTime == 1800) {
			Config.closeLightTime = 24 * 60 * 60;
		}
		if (!saveInstance.getOpenNotify(this).equals("unknown")) {
			Config.openToast = saveInstance.getOpenNotify(this);
		}
		if (!saveInstance.getCloseNotify(this).equals("unknown")) {
			Config.closeToast = saveInstance.getCloseNotify(this);
		}
		if (!Config.hasFlash) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage("请先关闭相机或手电筒程序");
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							finish();
						}
					}).create();
			dialog.show();
		}
		// 如果是自动调节模式
		if (Config.isAutoBrightness) {
			// System.out.println("onResume:------Brightness ");
			Brightness.stopAutoBrightness(this);
		}

		acquireWakeLock();
		setScreenBrightness(ScreenLight.getScreenLight(LightActivity.this));
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 如果是自动调节模式
		if (Config.isAutoBrightness) {
			Brightness.startAutoBrightness(this);
			// System.out.println("LightActivity------>onPause");
		}
		releaseWakeLock();
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			Resources res = getResources();
			TransitionDrawable transition3 = (TransitionDrawable) res
					.getDrawable(R.drawable.transitonbg1);
			transition3.setCrossFadeEnabled(true);
			transition3.startTransition(500);
			if (view == light) {
				if (!Config.isOpen) {
					if (Config.isFlash)
						stopFlash();
					openLight();
				} else {
					closeLight();
				}
			}
		}
	}

	// private boolean checkCameraPermission()
	// {
	// PackageManager pm = this.getPackageManager();
	// int hasPerm = pm.checkPermission(
	// android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
	// this.getPackageName());
	// return hasPerm == PackageManager.PERMISSION_GRANTED;
	// }

	/**
	 * 打开闪动模式
	 */
	private void openFlash() {
		flashTab.setIcon(R.drawable.flashon);
		background.setBackgroundColor(Color.parseColor(Config.color[0]));
		light.clearAnimation();
		Intent intent = new Intent(LightActivity.this, LightService.class);
		intent.putExtra("intent", "isFlash");
		startService(intent);
	}

	/**
	 * 关闭闪动模式
	 */
	private void stopFlash() {
		flashTab.setIcon(R.drawable.flashoff);
		Intent intent = new Intent(LightActivity.this, LightService.class);
		stopService(intent);
	}

	private void openLight() {
		openLightBack();
		Intent intent = new Intent(LightActivity.this, LightService.class);
		intent.putExtra("intent", "isLight");
		startService(intent);
	}

	/**
	 * 打开手电筒背景
	 */
	private void openLightBack() {
		Toast.makeText(LightActivity.this, Config.openToast, Toast.LENGTH_SHORT)
				.show();
		background.setBackgroundResource(R.drawable.light);
		if (operatingAnim != null && animOpen) {
			light.startAnimation(operatingAnim);
		}
	}

	private void closeLight() {
		closeLightBack();
		Intent intent = new Intent(LightActivity.this, LightService.class);
		stopService(intent);
	}

	/**
	 * 去除背景动画
	 */
	private void closeLightBack() {
		if (Config.isOpen)
			Toast.makeText(LightActivity.this, Config.closeToast,
					Toast.LENGTH_SHORT).show();
		System.out.println("Config: " + Config.color[0]);
		background.setBackgroundColor(Color.parseColor(Config.color[0]));
		light.clearAnimation();
		if (autoOff) {
			finish();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGesture.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mGesture != null && mGesture.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	class ChangeGestureDetector extends SimpleOnGestureListener {

		private float temp = 0;

		/** 按了一会儿 */
		// @Override
		// public void onShowPress(MotionEvent e) {
		// super.onShowPress(e);
		// verticalSeekBar.setVisibility(View.VISIBLE);
		// }
		//
		// /**点击一下不滑动，抬起*/
		// public boolean onSingleTapUp(MotionEvent e) {
		// verticalSeekBar.setVisibility(View.INVISIBLE);
		// return super.onSingleTapUp(e);
		// }
		//
		// /**长时间按*/
		// @Override
		// public void onLongPress(MotionEvent e) {
		// super.onShowPress(e);
		// verticalSeekBar.setVisibility(View.VISIBLE);
		// }

		/** 滑动一段松开 */
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			verticalSeekBar.setVisibility(View.INVISIBLE);
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		/** 滑动 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			if (!getScreenOn)
				return false;
			final int distance = 3;// 滑动距离
			if (e1.getX() <= width / 3) {
				verticalSeekBar.setVisibility(View.VISIBLE);
				int nowLight = ScreenLight.getScreenLight(LightActivity.this);
				int light = 0;
				if (e1.getY() - e2.getY() - temp > distance) {
					light = (int) ((e1.getY() - e2.getY() - temp) / 3)
							+ nowLight;
					setScreenBrightness(light);
				} else if (e2.getY() - e1.getY() - temp > distance) {
					light = (int) (-(e2.getY() - e1.getY() - temp) / 3)
							+ nowLight;
					setScreenBrightness(light);
				}
			}
			temp = Math.abs(e2.getY() - e1.getY());
			Message msg = myHandler.obtainMessage();
			msg.what = 0;
			myHandler.sendMessageDelayed(msg, 500);
			return super.onFling(e1, e2, velocityX, velocityY);

			// else if(e1.getX() >= 2*width/3){
			// //降低音量，调出系统音量控制 15
			// if(e2.getY() - e1.getY() - temp > distance){
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,
			// AudioManager.FLAG_PLAY_SOUND);
			// }
			// //增加音量，调出系统音量控制
			// else if(e1.getY() - e2.getY() - temp > distance){
			// mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,
			// AudioManager.FLAG_PLAY_SOUND);
			// }
			// }
		}
	}

	/**
	 * 设置屏幕的亮度
	 * 
	 * @param nowLight
	 *            当前屏幕亮度
	 */
	private void setScreenBrightness(int screenLight) {
		verticalSeekBar.setProgress(screenLight);
		if (screenLight >= 255) {
			Toast.makeText(LightActivity.this, "最大亮度", Toast.LENGTH_SHORT)
					.show();
			screenLight = 255;
		}
		if (screenLight <= 0) {
			Toast.makeText(LightActivity.this, "最小亮度", Toast.LENGTH_SHORT)
					.show();
			screenLight = 0;
		}
		ScreenLight.setScreenLight(LightActivity.this, screenLight);
	}

	@Override
	public void onTabSelected(Tab tab,
			FragmentTransaction paramFragmentTransaction) {
		if (tab == setTab) {
			Intent intent = new Intent(LightActivity.this, SetActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_left);
		} else if (tab == flashTab) {
			if (!Config.isFlash) {
				openFlash();
			} else {
				stopFlash();
			}
		} else if (tab == screenLightTab) {
			colorNumber++;
			if (colorNumber >= Config.color.length) {
				colorNumber = 0;
			}
			screenLight();
			// background.setBackgroundColor(Color.parseColor(Config.color[colorNumber]));
		}
	}

	TransitionDrawable transition1;
	TransitionDrawable transition2;
	private boolean isTouch = false;
	private boolean isSecondBack = false;

	private void screenLight() {
		if (!isSecondBack) {
			if (!isTouch) {
				Resources res = getResources();
				transition1 = (TransitionDrawable) res
						.getDrawable(R.drawable.transitonbg1);
				background.setBackgroundDrawable(transition1);
				transition1.setCrossFadeEnabled(true);
				transition1.startTransition(500);
				isTouch = true;
				// System.out.println("第一次");
				screenLightTab.setIcon(R.drawable.screen_light_red);
			} else {
				// System.out.println("第二次");
				screenLightTab.setIcon(R.drawable.screen_light_black);
				transition1.reverseTransition(400);
				isTouch = false;
				isSecondBack = true;
			}
		} else {
			if (!isTouch) {
				Resources res = getResources();
				transition2 = (TransitionDrawable) res
						.getDrawable(R.drawable.transitonbg2);
				background.setBackgroundDrawable(transition2);
				transition2.setCrossFadeEnabled(true);
				// System.out.println("第三次");
				screenLightTab.setIcon(R.drawable.screen_light_write);
				transition2.startTransition(500);
				isTouch = true;
			} else {
				// System.out.println("第四次");
				screenLightTab.setIcon(R.drawable.screen_light_black);
				transition2.reverseTransition(400);
				isTouch = false;
				isSecondBack = false;
			}
		}
	}

	@Override
	public void onTabUnselected(Tab tab,
			FragmentTransaction paramFragmentTransaction) {
	}

	@Override
	public void onTabReselected(Tab tab,
			FragmentTransaction paramFragmentTransaction) {
		onTabSelected(tab, paramFragmentTransaction);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exits();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 退出应用程序
	 */
	private void exits() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			exitTime = System.currentTimeMillis();
			Toast.makeText(LightActivity.this, "再按一次退出FlyMM手电",
					Toast.LENGTH_SHORT).show();
		} else {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(homeReceiver);
		// 如果是自动调节模式
		if (Config.isAutoBrightness) {
			Brightness.startAutoBrightness(this);
			// System.out.println("LightActivity-----onDestory>");
		}
		MyApplication.getMyApplication().exit();
	}

}
