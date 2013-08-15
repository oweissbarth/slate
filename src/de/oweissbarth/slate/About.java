package de.oweissbarth.slate;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.widget.TextView;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		try {
			((TextView)findViewById(R.id.about_version)).setText("version " + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			((TextView)findViewById(R.id.about_version)).setText("couldn't load App version");
			e.printStackTrace();
		}
	}


}
