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

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.data.Take;
import de.oweissbarth.slate.support.ProjectFile;

public class TakeWaiting extends Activity{
	private Handler handler;
	private Take take;
	private Shot shot;
	private Scene scene;
	private int threshold;
	
	private boolean isRecording;
	private AudioRecord recorder;
	short[] buffer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_waiting);
		this.scene = ProjectFile.project.getSceneById(getIntent().getExtras().getInt("scene"));
		this.shot = this.scene.getShotById(getIntent().getExtras().getInt("shot"));
		this.take = this.shot.getTakeById(getIntent().getExtras().getInt("take"));
		this.threshold = PreferenceManager.getDefaultSharedPreferences(this).getInt("threshold_clap", 25000);
		
		this.handler = new Handler(){
			public void handleMessage(Message msg){
				if((int)Math.sqrt(Math.abs(msg.what))>= threshold){
					onClapDetected(msg.what);
				}
			}
		};
		
		
		
	}
	
	protected void onStart(){
		super.onStart();
		listenForClap();
	}
	
	
	
	private void listenForClap(){		
		final int AUDIO_SOURCE = AudioSource.MIC;
		final int SAMPLE_RATE = 44100;
		final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
		final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
		final int BUFFERSIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, ENCODING);
		
		buffer = new short[BUFFERSIZE];
		
		this.recorder = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE, CHANNEL_CONFIG,ENCODING, BUFFERSIZE);
		
		this.recorder.startRecording();
		this.isRecording=true;
		
		Runnable listener = new Runnable(){
			public void run(){
				while(isRecording){
					int readBytes = recorder.read(buffer,0 , buffer.length);
					
					short max = 0; // maximum amplitude in buffer
					
					for(int i =0; i < readBytes; i++){
						max = max < buffer[i]? max : buffer[i];
					}
					handler.sendEmptyMessage((int) max);
				}
			}
		};
		Thread listenThread = new Thread(listener);
		listenThread.start();
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
