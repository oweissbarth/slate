package de.oweissbarth.slate.support;

import java.io.File;
import java.io.IOException;

import de.oweissbarth.slate.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class ThresholdPreference extends DialogPreference implements OnSeekBarChangeListener{
	private int threshold;
	private ImageView icon;
	private SeekBar slider;
	private ProgressBar levels;
	private Context context;
	private Handler handler;
	private int maxAmplitude;
	private Thread listenThread;
	
	public ThresholdPreference(Context context, AttributeSet attrs){
		super(context, attrs);
		
		setDialogLayoutResource(R.layout.settings_threshold);
		
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
		
		this.context= context;
		
		setDialogIcon(null);
	}
	
	protected void onBindDialogView(View view){
		super.onBindDialogView(view);
		
		this.icon = (ImageView)view.findViewById(R.id.settings_threshold_icon);
		this.icon.setTag(R.drawable.slate_open);
		
		this.levels = (ProgressBar)view.findViewById(R.id.settings_threshold_level);
		
		this.slider = (SeekBar) view.findViewById(R.id.settings_threshold_slider);
		this.slider.setOnSeekBarChangeListener(this);
		this.slider.setMax(32767);
		this.slider.setProgress(this.threshold);
		
		
		
		handler = new Handler(){
			public void handleMessage(Message msg){
				if(msg.what== -1){
					toggleIcon();
				}
			}
		};
		
		soundListener();
	}
	
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue){
		if(restorePersistedValue){
			this.threshold = this.getPersistedInt(25000);
		}else{
			this.threshold = (Integer) defaultValue;
			persistInt(threshold);
		}
	}
	
	protected Object onGetDefaultValue(TypedArray a, int index){
		return a.getInteger(index, 25000);
	}
	
	
	protected void onDialogClosed(boolean positiveResult){
		this.listenThread.interrupt();
		if(positiveResult){
			persistInt(this.threshold);
		}
	}
	
	private void toggleIcon(){
		if((Integer)this.icon.getTag()== R.drawable.slate_open){
			this.icon.setImageResource(R.drawable.slate_closed);
			this.icon.setTag(R.drawable.slate_closed);
			return;
		}
		if((Integer)this.icon.getTag()==R.drawable.slate_closed){
			this.icon.setImageResource(R.drawable.slate_open);
			this.icon.setTag(R.drawable.slate_open);
			return;
		}		
	}
	
	private void soundListener(){
		Runnable listen = new Runnable(){
			MediaRecorder recorder;
			public void run(){
				
				recorder = new MediaRecorder();
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
					if(Thread.interrupted()){
						recorder.stop();
						recorder.reset();
						recorder.release();
						recorder=null;
						File file = new File("/mnt/sdcard/tmp.3gp");
						if(file.exists())
							file.delete();
						return;
					}
					maxAmplitude = recorder.getMaxAmplitude();
							
					if(maxAmplitude>threshold){
						handler.sendEmptyMessage(-1);
					}
				}
			}
			
		};
		
		Runnable updater = new Runnable(){
			public void run(){
				levels.setProgress(maxAmplitude);
				if(maxAmplitude>1000){
					handler.postDelayed(this, 500);
				}else{
					handler.post(this);
				}
			}
		};
		
		Thread updateThread = new Thread(updater);
		updateThread.start();
		
		listenThread = new Thread(listen);
		listenThread.start();
	}

	@Override
	public void onProgressChanged(SeekBar view, int progress, boolean arg2) {
		this.threshold = progress;
		this.levels.setMax(progress);
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}