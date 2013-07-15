package de.oweissbarth.slate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.oweissbarth.slate.data.ProjectFile;
import de.oweissbarth.slate.data.Scene;

public class EditScene extends SherlockActivity {
	
	private int scene;
	private boolean newObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_scene);
		checkIfNew();
		
		scene = getIntent().getExtras().getInt("scene");
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}
	
	
	private void checkIfNew(){
		Intent i = getIntent();
		
		newObject = i.getExtras().getBoolean("newObject");
		Log.d("EDITOR", "NewObject?" + newObject);
		//int id = i.getExtras().getInt("id");
		
		
		if(!newObject){
			Log.d("EDITOR", "Existing Scene");
			((EditText) findViewById(R.id.scene_name)).setText(ProjectFile.project.getSceneById(scene).getName());
			((EditText) findViewById(R.id.scene_description)).setText((ProjectFile.project.getSceneById(scene)).getDescription());
			
		}else{
			Log.d("EDITOR", "NewScene");
			((EditText) findViewById(R.id.scene_name)).setText("");
			((EditText) findViewById(R.id.scene_description)).setText("");
		}
	}
	
	public void onResume(){
		super.onResume();
		Log.d("EDITOR", "On resume");
		checkIfNew();
	}
	
	public boolean done(MenuItem item){
		Scene scene;
		if(newObject){
			scene = ProjectFile.project.addScene();
		}else{
			scene = ProjectFile.project.getSceneById(this.scene);	
		}
		
		String name = ((EditText)findViewById(R.id.scene_name)).getText().toString();
		String description= ((EditText)findViewById(R.id.scene_description)).getText().toString();
		
		scene.setName(name);
		scene.setDescription(description);
		this.finish();
		return true;
	}
	
	public boolean discard(MenuItem item){
		this.finish();
		return true;
	}



}
