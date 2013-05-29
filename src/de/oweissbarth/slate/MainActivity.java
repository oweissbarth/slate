package de.oweissbarth.slate;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		String ProjectFileName = intent.getExtras().getString("projectFileName");
		
		Project project = ProjectFile.load(ProjectFileName, getApplicationContext());
		
	}

}
