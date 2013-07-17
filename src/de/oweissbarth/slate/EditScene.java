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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	
	protected void onStop(){
		super.onStop();
		ProjectFile.saveIfNecessary(this);
	}



}
