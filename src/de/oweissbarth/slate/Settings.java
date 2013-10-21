package de.oweissbarth.slate;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;


public class Settings extends SherlockPreferenceActivity {
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}

