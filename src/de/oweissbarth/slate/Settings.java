package de.oweissbarth.slate;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import android.os.Bundle;


public class Settings extends SherlockPreferenceActivity {
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}

