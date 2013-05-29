package de.oweissbarth.slate;

import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class IntroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		String[] availableFiles = ProjectFile.listProjects(getApplicationContext());
		ArrayAdapter<String> adapter = null;
		if (availableFiles.length!=0){
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availableFiles);
		}else{
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"No Projects"});
		}
		Spinner projects = (Spinner) findViewById(R.id.projectsSpinner);
		projects.setAdapter(adapter);
		
	}

	public void newProjectCreationDialog(View view){
		Intent i = new Intent(this, NewProject.class);
		startActivity(i);
		
	}
	
	

}
