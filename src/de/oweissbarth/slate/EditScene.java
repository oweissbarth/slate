package de.oweissbarth.slate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class EditScene extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_scene);
		checkIfNew();
	}
	
	
	private void checkIfNew(){
		Intent i =getIntent();
		
		boolean newObject = i.getExtras().getBoolean("newObject");
		Log.d("EDITOR", "NewObject?" + newObject);
		int id = i.getExtras().getInt("id");
		
		
		if(!newObject){
			Log.d("EDITOR", "Existing Scene");
			((EditText) findViewById(R.id.scene_name)).setText((MainActivity.project.getSceneById(id)).getName());
			((EditText) findViewById(R.id.scene_description)).setText((MainActivity.project.getSceneById(id)).getDescription());
			
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



}
