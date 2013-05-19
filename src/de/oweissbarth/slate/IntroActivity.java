package de.oweissbarth.slate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class IntroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		
		String[] availableFiles = File.listProjects();
		ArrayAdapter<String> adapter = null;
		if (availableFiles.length!=0){
			
			
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availableFiles);
			
		}else{
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"No Projects"});
		}
		Spinner projects = (Spinner) findViewById(R.id.projectsSpinner);
		projects.setAdapter(adapter);
		
	}

	public void newProject(View view){
		Intent intent = new Intent(getApplicationContext(), NewProject.class);
		startActivity(intent);
	}

}
