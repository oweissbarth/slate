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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import de.oweissbarth.slate.support.LoadingFileTask;
import de.oweissbarth.slate.support.ProjectFile;

public class IntroActivity extends SherlockActivity {
	private int importClicked=0;
	private ProgressDialog progress;
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
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		projects.setAdapter(adapter);
		
		if(this.getIntent().getExtras()!= null && this.getIntent().getExtras().getBoolean("back", false))
			ProjectFile.save(getApplicationContext());
		
	}

	
	protected void onRestart(){
		super.onRestart();

		Log.d("Intro", "IntroActivity restarted");
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
	
	protected void onStop(){
		super.onStop();
		Log.d("Intro", "IntroActivity Stopped");
	}

	public void newProjectCreationDialog(View view){
		Intent i = new Intent(this, NewProject.class);
		startActivity(i);
		
	}
	
	public void loadButtonEvent(View view){
		Spinner projects = (Spinner)findViewById(R.id.projectsSpinner);
		String projectName =  projects.getSelectedItem().toString();
		
		if(projectName.equals("No Projects")){
			Toast.makeText(this, "There is no project to load", Toast.LENGTH_LONG).show();
			return;
		}
		Log.d("loadFile", "Project to load is: "+ projectName);
		
		
		this.progress = new ProgressDialog(this);
		this.progress.setMessage("Loading file...");
		this.progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		this.progress.setIndeterminate(false);
		MainActivity.loading = new LoadingFileTask(this.progress, this);
		MainActivity.loading.execute(projectName);
	}
	
	public void importProjectButtonEvent(View view){
		if(this.importClicked==3){
			Toast notific = Toast.makeText(getApplicationContext(), "Don't you get it? \nThis isn't yet implemented!", Toast.LENGTH_LONG);
			notific.show();
			this.importClicked=-1;
			Button button = (Button)findViewById(R.id.import_project_button);
			button.setText("Do not Click again!");
		}else{
			if(this.importClicked == -1 ){
				Button button = (Button)findViewById(R.id.import_project_button);
				button.setVisibility(View.GONE);
			}
			this.importClicked++;
		}
	}
	
	protected void onDestroy(){
		super.onDestroy();
		if(this.progress!=null){
			this.progress.dismiss();
			this.progress=null;
		}
	}

	
	

}
