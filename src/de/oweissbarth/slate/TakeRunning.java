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

import java.sql.Time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.data.Take;
import de.oweissbarth.slate.support.ProjectFile;

public class TakeRunning extends Activity {
	private Take take;
	private Shot shot;
	private Scene scene;
	private Chronometer chronometer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_running);
		Bundle extras = getIntent().getExtras();
		this.scene = ProjectFile.project.getSceneById(getIntent().getExtras().getInt("scene"));
		this.shot = this.scene.getShotById(getIntent().getExtras().getInt("shot"));
		this.take = this.shot.getTakeById(getIntent().getExtras().getInt("take")); 
		this.take = ProjectFile.project.getSceneById(extras.getInt("scene")).getShotById(extras.getInt("shot")).getTakeById(extras.getInt("take"));
		this.chronometer=((Chronometer)findViewById(R.id.chronometer));
		this.chronometer.setBase(SystemClock.elapsedRealtime());
		this.chronometer.start();
	}
	
	public void onCut(View view){
		this.chronometer.stop();
		long duration = SystemClock.elapsedRealtime() - this.chronometer.getBase();
		String durationString = (int) ((duration / (1000*60*60)) % 24)+":"+(int) ((duration / (1000*60)) % 60)+":"+(int) (duration / 1000) % 60 ;
		this.take.setDuration(Time.valueOf(durationString));
		Intent i = new Intent(this, TakePost.class);
		i.putExtras(getIntent().getExtras());
		startActivity(i);
	}

	public void onBackPressed(){
		if(this.take!=null){
			this.shot.deleteTake(this.take.getID());
		}
		startActivity(new Intent(this, MainActivity.class));
	}



}
