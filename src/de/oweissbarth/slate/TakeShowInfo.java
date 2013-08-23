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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;
import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.data.Take;
import de.oweissbarth.slate.support.ProjectFile;

public class TakeShowInfo extends Activity{
	Scene scene;
	Shot shot;
	Take take;
	ViewFlipper flipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_infoswitcher);
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.scene = ProjectFile.project.getSceneById(getIntent().getExtras().getInt("scene"));
		this.shot = this.scene.getShotById(getIntent().getExtras().getInt("shot"));
		this.take = this.shot.addTake();
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ProjectFile.project.getEquipment().getMediaList());
		((Spinner)findViewById(R.id.take_pretake_media)).setAdapter(adapter);
		this.flipper = ((ViewFlipper)findViewById(R.id.takeviewflipper));
	}
	
	public void onBackPressed(){
		if(this.take!=null){
			this.shot.deleteTake(this.take.getID());
		}
		startActivity(new Intent(this, MainActivity.class));
	}
	
	public void startTake(View view){
		setValues();
		this.take.setMedia(ProjectFile.project.getEquipment().getMediaById(((Spinner)findViewById(R.id.take_pretake_media)).getSelectedItemPosition()));
		int interval = 5000;
		flipToEnd(interval, this.flipper);
		
	}
	
	private void setValues(){

		//Set values for take_overview
		((TextView)findViewById(R.id.take_overview_projectName)).setText(ProjectFile.project.getName());
		((TextView)findViewById(R.id.take_overview_scene)).setText(String.valueOf(this.scene.getID()));
		((TextView)findViewById(R.id.take_overview_shot)).setText(String.valueOf((char)(this.shot.getID()+97)));
		((TextView)findViewById(R.id.take_overview_take)).setText(String.valueOf(this.take.getID()+1));
		//Date date = new Date();
		//String dateString = date.getDay() +"-" + date.getMonth() +"-" +date.getYear();
		((TextView)findViewById(R.id.take_overview_date)).setText(DateFormat.getDateInstance().format(new Date()));
		((TextView)findViewById(R.id.take_overview_director)).setText(ProjectFile.project.getDirector());
		

		//Set values for take_sceninfo
		((TextView)findViewById(R.id.take_sceneinfo_sceneid)).setText(String.valueOf(this.scene.getID()));
		((TextView)findViewById(R.id.take_sceneinfo_scenename)).setText(this.scene.getName());
		((TextView)findViewById(R.id.take_sceneinfo_ext)).setText(this.scene.getExt()?"Ext":"Int");
		((TextView)findViewById(R.id.take_sceneinfo_scenedescription)).setText(this.scene.getDescription());
			
	
		//set values for take_shotinfo
		((TextView)findViewById(R.id.take_shotinfo_shotid)).setText(String.valueOf((char)(this.shot.getID()+97)));
		((TextView)findViewById(R.id.take_shotinfo_shotfieldsize)).setText(getResources().getStringArray(R.array.fieldSize)[this.shot.getFieldSize()]);
		((TextView)findViewById(R.id.take_shotinfo_shotcameramotion)).setText(getResources().getStringArray(R.array.cameraMotion)[this.shot.getCameraMotion()]);
		((TextView)findViewById(R.id.take_shotinfo_shotcamera)).setText(this.shot.getCamera().toString());
		((TextView)findViewById(R.id.take_shotinfo_shotfps)).setText(this.shot.getFps()+" fps");
		((TextView)findViewById(R.id.take_shotinfo_shotlens)).setText(this.shot.getLens().toString());
		((TextView)findViewById(R.id.take_shotinfo_shotfocallength)).setText(this.shot.getFocalLength() + " mm");
		
	}
	
	private void flipToEnd(int mills, final ViewFlipper flipper ){
		int children = flipper.getChildCount();
		int delay = mills;
		
		final Handler handler = new Handler();
		Runnable runnable= new Runnable(){
            @Override
            public void run() {
            	flipper.showNext();
            }
		};
		
		Runnable doAfter = new Runnable(){
			public void run(){
				Intent i = new Intent(getApplicationContext(), TakeWaiting.class);
				i.putExtras(getIntent().getExtras());
				i.putExtra("take" , take.getID());
				startActivity(i);
			}
		};
        int i;
		for(i=1; i<children; i++){
			handler.postDelayed(runnable, i*delay);
		}
		handler.postDelayed(doAfter, (i+1)*delay);
        

	}
}
