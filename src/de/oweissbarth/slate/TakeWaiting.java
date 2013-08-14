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

import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.data.Take;
import de.oweissbarth.slate.support.ProjectFile;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class TakeWaiting extends Activity{
	private Handler handler;
	private Take take;
	private Shot shot;
	private Scene scene;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_waiting);
		this.scene = ProjectFile.project.getSceneById(getIntent().getExtras().getInt("scene"));
		this.shot = this.scene.getShotById(getIntent().getExtras().getInt("shot"));
		this.take = this.shot.getTakeById(getIntent().getExtras().getInt("take"));
		this.handler = new Handler(){
			public void handleMessage(Message msg){
				onClapDetected(msg.what);
			}
		};
		
		
	}
	
	protected void onStart(){
		super.onStart();
		listenForClap();
	}
	
	
	
	private void listenForClap(){
	
	
		
		
		Runnable waitForClap = new Runnable(){
			public void run(){
				
				final int threshold = 30000;
				
				MediaRecorder recorder = new MediaRecorder();
				Log.d("Clap", "Start waiting");
							    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			    recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			    recorder.setOutputFile("/sdcard/tmp.3gp");
				try {
					recorder.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recorder.start();
				
				for(;;){
					
					int maxAmplitude = recorder.getMaxAmplitude();
						
					if(maxAmplitude>threshold){
						handler.sendEmptyMessage(maxAmplitude);
						recorder.stop();
						recorder.reset();
						recorder.release();
						recorder=null;
						File file = new File("/mnt/sdcard/tmp.3gp");
						if(file.exists())
							file.delete();
						return;
					}
				}
				

			}
			
		};
		
		Thread myThread = new Thread(waitForClap);
		myThread.start();
	
	}
	
	private void onClapDetected(int value){
		Toast.makeText(this, "Detected Clap with Amplitude: "+ value, Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, TakeRunning.class);
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
