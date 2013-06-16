package de.oweissbarth.slate;

import java.util.Arrays;

import de.oweissbarth.slate.data.ProjectFile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class IntroActivity extends Activity {
	private int importClicked=0;
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
		Log.d("loadFile", "Projectto load is: "+ projectName);
		
		Intent loadProjectIntoMain = new Intent(this, MainActivity.class);
		loadProjectIntoMain.putExtra("projectName", projectName);
		startActivity(loadProjectIntoMain);
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
	
	

}
