package de.oweissbarth.slate;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class TakeWaiting extends Activity{
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			onClapDetected(msg.what);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_waiting);
		
	}
	
	protected void onStart(){
		super.onStart();
		listenForClap();
	}
	
	
	
	private void listenForClap(){
	
	
		
		
		Runnable waitForClap = new Runnable(){
			public void run(){
				final int threshold = 10000;
				final MediaRecorder recorder = new MediaRecorder();
				Log.d("Clap", "Start waiting");
				
				long name = new Date().getTime();
			    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			    recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			    recorder.setOutputFile("/sdcard/"+Long.toString(name)+".3gp");
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
				recorder.getMaxAmplitude();
				
				for(;;){
					int maxAmplitude = recorder.getMaxAmplitude();
					
					if(maxAmplitude>threshold){
						handler.sendEmptyMessage(maxAmplitude);
						recorder.stop();
						File file = new File("/sdcard/", Long.toString(name));
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
	}
		

}
