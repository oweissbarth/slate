package de.oweissbarth.slate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		String ProjectFileName = intent.getExtras().getString("projectFileName");
		
		Project project = ProjectFile.load(ProjectFileName, getApplicationContext());
		
	}

}
