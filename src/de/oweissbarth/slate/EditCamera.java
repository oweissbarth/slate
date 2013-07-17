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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.oweissbarth.slate.data.Camera;
import de.oweissbarth.slate.data.ProjectFile;

public class EditCamera extends SherlockActivity implements OnItemClickListener{
	private boolean[] availableFpsBool;
	private short numberChecked =0;
	private boolean newObject;
	private int camera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_camera);
		ListView list = (ListView) findViewById(R.id.fpsList);
		String[] items = getResources().getStringArray(R.array.fps);
		this.availableFpsBool = new boolean[items.length];
		ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, items);
		list.setAdapter(adpater);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setOnItemClickListener(this);
		checkIfNew();
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}
	
	private void checkIfNew(){
		Intent i = getIntent();
		this.newObject = i.getExtras().getBoolean("newObject");
		
		if(!this.newObject){
			this.camera = i.getExtras().getInt("id");
			((EditText)findViewById(R.id.cameraName)).setText(ProjectFile.project.getEquipment().getCameraById(this.camera).getName());
			
		}
	}
	


	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int id, long position) {
		CheckedTextView item = (CheckedTextView) view;
		item.toggle();
		Log.d("Checklist", "set " + id + " on " + item.isChecked());
		this.availableFpsBool[id] = item.isChecked();
		if(item.isChecked())
			this.numberChecked++;
		else
			this.numberChecked--;
	}
	
	public boolean done(MenuItem item){
		Camera camera;
		if(!this.newObject){
			camera = ProjectFile.project.getEquipment().getCameraById(this.camera);
		}else{
			camera = ProjectFile.project.getEquipment().addCamera();
		}
		
		
		String name = ((EditText)findViewById(R.id.cameraName)).getText().toString();
		camera.setName(name);
		
		int[] availableFps = new int[this.numberChecked];
		short n=0;
		for(int i=0; i<this.availableFpsBool.length; i++){
			if(this.availableFpsBool[i]==true){
				availableFps[n]=i;
				n++;
			}
		}
		
		camera.setAvailableFps(availableFps);
		
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
