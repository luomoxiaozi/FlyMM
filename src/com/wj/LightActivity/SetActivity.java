package com.wj.LightActivity;

import com.meizu.smartbar.SmartBarUtils;
import com.wj.fragment.SettingFragment;
import com.wj.model.Brightness;
import com.wj.model.ScreenLight;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class SetActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_layout);
//		getActionBar().setDisplayHomeAsUpEnabled(true);//�����Ͻ�ͼ�����߼���һ�����ص�ͼ��
		final ActionBar bar = getActionBar();
		//���÷��ؼ�ͼƬ
		SmartBarUtils.setBackIcon(bar, getResources().getDrawable(R.drawable.ic_back));
		getFragmentManager().beginTransaction().add(android.R.id.content, new SettingFragment()).commit();
		Config.number++;
//		System.out.println("SetActivity------->onCreate");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//������Զ�����ģʽ
		if(Config.isAutoBrightness) {
//			System.out.println("SetActivity------->onResume");
			Brightness.stopAutoBrightness(this);
		}
		ScreenLight.setScreenLight(this, ScreenLight.getScreenLight(this));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//������Զ�����ģʽ
		if(Config.isAutoBrightness) {
//			System.out.println("SetActivity------->onPause");
			Brightness.startAutoBrightness(this);
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Config.number--;
		if(Config.number<=0) {
			Intent intentHome = new Intent(this, LightActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
