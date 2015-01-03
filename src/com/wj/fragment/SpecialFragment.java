package com.wj.fragment;

import com.wj.LightActivity.Config;
import com.wj.LightActivity.R;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

public class SpecialFragment extends PreferenceFragment implements OnPreferenceChangeListener,OnPreferenceClickListener {
	private SwitchPreference sp_service_on;
	private SwitchPreference sp_message_on;
	private SwitchPreference sp_call_on;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.set_special);
		sp_service_on = (SwitchPreference)findPreference("service_on");
		sp_message_on = (SwitchPreference)findPreference("message_on");
		sp_call_on = (SwitchPreference)findPreference("call_on");
		sp_service_on.setOnPreferenceClickListener(this);
		sp_service_on.setOnPreferenceChangeListener(this);
	}

	public void onStart() {
		super.onStart();
		preferenceStateSet(sp_service_on,sp_service_on.isChecked());
		getActivity().getActionBar().setTitle(R.string.set_special);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if ("set_details".equalsIgnoreCase(preference.getKey())) {
			getActivity().getFragmentManager().beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(android.R.id.content, new DetailsFragment())
					.addToBackStack(null).commit();
			Config.number++;
			return true;
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	public static class DetailsFragment extends PreferenceFragment {
		public void onCreate(Bundle paramBundle) {
			super.onCreate(paramBundle);
			addPreferencesFromResource(R.xml.set_details);
		}

		public void onStart() {
			super.onStart();
			getActivity().getActionBar().setTitle(R.string.set_details);
		}
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object obj) {
		if(preference.getKey().equals("service_on")) {
			preference.setDefaultValue(obj);//重点
			preferenceStateSet((SwitchPreference)preference, (Boolean)obj);
		}
		return true;
	}
	
	/**
	 * 改变preference状态
	 */
	private void preferenceStateSet(SwitchPreference sp, boolean isChecked) {
		if(!isChecked) {
			sp_message_on.setEnabled(false);
			sp_call_on.setEnabled(false);
		}else {
			sp_call_on.setEnabled(true);
			sp_message_on.setEnabled(true);
		}
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		SwitchPreference sp = ((SwitchPreference)preference);
		return false;
	}

}
