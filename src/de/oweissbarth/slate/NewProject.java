package de.oweissbarth.slate;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;

import de.oweissbarth.slate.data.Project;
import de.oweissbarth.slate.data.ProjectFile;

public class NewProject extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_project);
	}
	
	
	public void newProjectcreate(View view){
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
		
		Project project = new Project();
		project.setName( ProjectNameField.getText().toString());
		project.setDirector(ProjectDirectorField.getText().toString());
		
		ProjectFile.save(project, getApplicationContext());
		
		Intent openMainView = new Intent(this, MainActivity.class);
		openMainView.putExtra("projectName", projectName);
		startActivity(openMainView);
	}


}
