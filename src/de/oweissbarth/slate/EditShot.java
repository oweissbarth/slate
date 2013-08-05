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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.oweissbarth.slate.data.Camera;
import de.oweissbarth.slate.data.Lens;
import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.support.ProjectFile;

public class EditShot extends SherlockActivity implements OnSeekBarChangeListener, OnItemSelectedListener{
	private int scene;
	private int shot;
	private boolean newObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_shot);
		
		this.scene = getIntent().getExtras().getInt("scene");
		this.shot = getIntent().getExtras().getInt("shot");

		ArrayAdapter camera = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,ProjectFile.project.getEquipment().getCameraList());
		camera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner)findViewById(R.id.shot_camera)).setAdapter(camera);
		
		ArrayAdapter lens = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,ProjectFile.project.getEquipment().getLensList());
		lens.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner)findViewById(R.id.shot_lens)).setAdapter(lens);
		
		((SeekBar)findViewById(R.id.shot_focalLength)).setOnSeekBarChangeListener(this);
		((Spinner)findViewById(R.id.shot_camera)).setOnItemSelectedListener(this);
		((Spinner)findViewById(R.id.shot_lens)).setOnItemSelectedListener(this);

		
		checkIfNew();	
	}
	
	
	private void checkIfNew(){
	Intent i = getIntent();
		
		newObject = i.getExtras().getBoolean("newObject");
		Log.d("EDITOR", "NewObject?" + newObject);

		if(!newObject){
			Log.d("EDITOR", "Existing Shot");
			
			Shot shot = ProjectFile.project.getSceneById(this.scene).getShotById(this.shot);
			
			((Spinner)findViewById(R.id.shot_fieldSize)).setSelection(shot.getFieldSize());
			((Spinner)findViewById(R.id.shot_cameraMotion)).setSelection(shot.getCameraMotion());
			
			((Spinner)findViewById(R.id.shot_camera)).setSelection(shot.getCamera().getId());
			((Spinner)findViewById(R.id.shot_fps)).setSelection(shot.getFps());
			
			((Spinner)findViewById(R.id.shot_lens)).setSelection(shot.getLens().getId());
			((ProgressBar)findViewById(R.id.shot_focalLength)).setMax(shot.getLens().getMaxFocalLength()-shot.getLens().getMinFocalLength());
			((ProgressBar)findViewById(R.id.shot_focalLength)).setProgress(shot.getFocalLength()-shot.getLens().getMinFocalLength());
		
		}else{
			Log.d("EDITOR", "New Shot");
			
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		int focalLength = ProjectFile.project.getEquipment().getLensById(((Spinner)findViewById(R.id.shot_lens)).getSelectedItemPosition()).getMinFocalLength() + progress;
		
		((TextView)findViewById(R.id.shot_focalLength_value)).setText(focalLength + "mm");
		
	}


	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean done(MenuItem item){
		Shot shot = this.newObject? ProjectFile.project.getSceneById(this.scene).addShot() : ProjectFile.project.getSceneById(this.scene).getShotById(this.shot);
	
			
			short fieldSize = (short)((Spinner)findViewById(R.id.shot_fieldSize)).getSelectedItemPosition();
			short cameraMotion = (short)((Spinner)findViewById(R.id.shot_cameraMotion)).getSelectedItemPosition();
			
			Camera camera = ProjectFile.project.getEquipment().getCameraById(((Spinner)findViewById(R.id.shot_camera)).getSelectedItemPosition());
			short fps = (short)((Spinner)findViewById(R.id.shot_fps)).getSelectedItemPosition();
			
			Lens lens = ProjectFile.project.getEquipment().getLensById(((Spinner)findViewById(R.id.shot_lens)).getSelectedItemPosition());
			int focalLength = lens.getFixed()? lens.getMinFocalLength() : ((ProgressBar)findViewById(R.id.shot_focalLength)).getProgress() + lens.getMinFocalLength();
		
		shot.setFieldSize(fieldSize);
		shot.setCameraMotion(cameraMotion);
		shot.setCamera(camera);
		shot.setFps(fps);
		shot.setLens(lens);
		shot.setFocalLength(focalLength);
		
		this.finish();
		return true;
	}
	
	public boolean discard(MenuItem item){
		this.finish();
		return true;
	}


	@Override
	public void onItemSelected(AdapterView<?> adapter, View view, int id, long position) {
		Log.d("Edit Shot", "Item Selected");
		if(adapter.getId()==R.id.shot_camera){
			Log.d("Edit Shot", "Camera changed");
			String[] fps = new String[ProjectFile.project.getEquipment().getCameraById(id).getAvailableFps().length];
			int i=0;
			for(int value: ProjectFile.project.getEquipment().getCameraById(id).getAvailableFps()){
				fps[i]=""+value;
				i++;
			}
			ArrayAdapter fpsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,fps);
			fpsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			((Spinner)findViewById(R.id.shot_fps)).setAdapter(fpsAdapter);
		}
		else if(adapter.getId()==R.id.shot_lens){
			Log.d("Edit Shot", "Lens changed");

			if(ProjectFile.project.getEquipment().getLensById(id).getFixed())
				((SeekBar)findViewById(R.id.shot_focalLength)).setVisibility(View.GONE);
			else
				((SeekBar)findViewById(R.id.shot_focalLength)).setVisibility(View.VISIBLE);
				Lens lens = ProjectFile.project.getEquipment().getLensById(id);
				((ProgressBar)findViewById(R.id.shot_focalLength)).setMax(lens.getMaxFocalLength()-lens.getMinFocalLength());
				((TextView)findViewById(R.id.shot_focalLength_minValue)).setText(ProjectFile.project.getEquipment().getLensById(id).getMinFocalLength() + " mm");
				((TextView)findViewById(R.id.shot_focalLength_maxValue)).setText(ProjectFile.project.getEquipment().getLensById(id).getMaxFocalLength() + " mm");

		}
		
	}
	
	protected void onStop(){
		super.onStop();
		ProjectFile.saveIfNecessary(this);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	



}
