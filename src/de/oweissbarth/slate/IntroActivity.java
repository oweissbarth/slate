package de.oweissbarth.slate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class IntroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
	}

	public void newProject(View view){
		Intent intent = new Intent(getApplicationContext(), NewProject.class);
		startActivity(intent);
	}

}
