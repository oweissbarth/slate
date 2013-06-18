package de.oweissbarth.slate;

import de.oweissbarth.slate.data.ProjectFile;
import de.oweissbarth.slate.data.Scene;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditScene extends Activity {
	
	private int scene;
	private boolean newObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_scene);
		checkIfNew();
		
		scene = getIntent().getExtras().getInt("scene");
	}
	
	
	private void checkIfNew(){
		Intent i = getIntent();
		
		newObject = i.getExtras().getBoolean("newObject");
		Log.d("EDITOR", "NewObject?" + newObject);
		//int id = i.getExtras().getInt("id");
		
		
		if(!newObject){
			Log.d("EDITOR", "Existing Scene");
			((EditText) findViewById(R.id.scene_name)).setText((ProjectFile.project.getSceneById(scene)).getName());
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
	
	public void saveButton(View view){
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
	}



}
