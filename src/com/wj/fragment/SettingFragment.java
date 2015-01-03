package com.wj.fragment;

import com.wj.LightActivity.Config;
import com.wj.LightActivity.R;
import com.wj.fragment.SpecialFragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

public class SettingFragment extends PreferenceFragment {
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.set_main);
	}

	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if ("ABOUT_ME".equalsIgnoreCase(preference.getKey())) {
			getActivity().getFragmentManager().beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(android.R.id.content, new AboutFragment())
					.addToBackStack(null).commit();
			Config.number++;
			return true;
		} else if ("set_special".equalsIgnoreCase(preference.getKey())) {
			getActivity().getFragmentManager().beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(android.R.id.content, new SpecialFragment())
					.addToBackStack(null).commit();
			Config.number++;
			return true;
		} else if ("share".equalsIgnoreCase(preference.getKey())) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			intent.putExtra(Intent.EXTRA_TEXT,
					"我正在使用FlyMM手电，到魅族应用商店搜索 FlyMM手电 就可以下载啦，超级棒！");
			startActivity(Intent.createChooser(intent, "分享到"));
		} 
		return false;
	}

	public void onStart() {
		super.onStart();
		getActivity().getActionBar().setTitle(R.string.light_set);
	}

	public static class AboutFragment extends PreferenceFragment {
		public void onCreate(Bundle paramBundle) {
			super.onCreate(paramBundle);
			addPreferencesFromResource(R.xml.set_about);
		}

		public void onStart() {
			super.onStart();
			getActivity().getActionBar().setTitle(R.string.about_me);
		}
	}
}
