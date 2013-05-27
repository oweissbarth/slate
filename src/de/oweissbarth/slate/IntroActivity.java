package de.oweissbarth.slate;

import java.util.Arrays;

import android.R.layout;
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
		Project testproject = new Project("erstes", "Oliver");
		Log.d("File", "TestProject erstellt!");
		if(ProjectFile.save(testproject, getApplicationContext())){
			Log.d("NewProject", "Saved");
		}else{
			Log.d("NewProject", "Error");
		}
		
		setContentView(R.layout.new_project);
	}
	
	public void newProjectcreate(){
		EditText ProjectDirectorField = (EditText) findViewById(R.id.newProjectDirector);
		EditText ProjectNameField = (EditText) findViewById(R.id.newProjectName);
		
		String projectName = ProjectNameField.getText().toString();
		String projectDirector = ProjectDirectorField.getText().toString();
		
		String[] existingProjects = ProjectFile.listProjects(getApplicationContext());
		
		if(projectName.length() == 0){
			//Error no Name entered
		}
		if(projectDirector.length() == 0){
			//Error no Director entered
		}
		
		if((Arrays.asList(existingProjects)).contains(projectName)){
			//Error There is already a Project with the same name. Overwrite it?
		}
		
		Project project = new Project( ProjectNameField.getText().toString(), ProjectDirectorField.getText().toString());
		
		ProjectFile.save(project, getApplicationContext());
		
		Intent openMainView = new Intent(this, MainActivity.class);
		openMainView.putExtra("projectFileName", projectName + ".slate");
	}

}
