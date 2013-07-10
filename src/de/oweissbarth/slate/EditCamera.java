package de.oweissbarth.slate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class EditCamera extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_camera);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_camera, menu);
		return true;
	}

}
