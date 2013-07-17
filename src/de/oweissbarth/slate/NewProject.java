/*******************************************************************************
 * Copyright 2013 Oliver Weissbarth
 * 
 * This file is part of Slate.
 * 
 *     Slate is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *      Slate is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with  Slate.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.oweissbarth.slate;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;

import de.oweissbarth.slate.data.Project;
import de.oweissbarth.slate.support.ProjectFile;

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
		
		ProjectFile.save(getApplicationContext());
		
		Intent openMainView = new Intent(this, MainActivity.class);
		openMainView.putExtra("projectName", projectName);
		startActivity(openMainView);
	}


}
