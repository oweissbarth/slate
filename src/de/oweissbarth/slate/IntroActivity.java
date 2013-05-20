package de.oweissbarth.slate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

	public void newProject(View view){
		Project testproject = new Project("erstes", "Oliver");
		Log.d("File", "TestProject erstellt!");
		if(ProjectFile.save(testproject, getApplicationContext())){
			Log.d("NewProject", "Saved");
		}else{
			Log.d("NewProject", "Error");
		}
		
		Intent intent = new Intent(getApplicationContext(), NewProject.class);
		startActivity(intent);
	}

}
