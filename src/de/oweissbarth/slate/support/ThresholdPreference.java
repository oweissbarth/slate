package de.oweissbarth.slate.support;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import de.oweissbarth.slate.R;

public class ThresholdPreference extends DialogPreference implements OnSeekBarChangeListener{
	private int threshold;			//represents the amplitude that's needed to be reached to trigger an action
	private ImageView icon;			//slate icon that changes it's look on clap to give visual response
	private SeekBar slider;			//slider to adjust the current threshold value
	private ProgressBar levels;		//progressbar to display the current audio amplitude relative to the threshold
	private Handler handler;		//handles the listening thread
	private AudioRecord recorder;	//Audiorecord object to read the audio from hardware
	private boolean isRecording=false;//status variable to stop the recording 
	private short[] buffer;		//buffer to temporarily save the audio samples
	
		
	public ThresholdPreference(Context context, AttributeSet attrs){
		super(context, attrs);
		
		setDialogLayoutResource(R.layout.settings_threshold);
		
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
				
		setDialogIcon(null);
	}
	
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue){
	    super.onSetInitialValue(restorePersistedValue, defaultValue);
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
	
	protected void onBindDialogView(View view){
		super.onBindDialogView(view);
		
		this.threshold = this.getPersistedInt(25000); //reload threshold on every reopen of th Dialog to keep the value consistent
		
		this.icon = (ImageView)view.findViewById(R.id.settings_threshold_icon);
		this.icon.setTag(R.drawable.slate_open);		//set Tag for easy identifying in toggleIcon()
		
		this.levels = (ProgressBar)view.findViewById(R.id.settings_threshold_level);
		this.slider = (SeekBar) view.findViewById(R.id.settings_threshold_slider);
		this.slider.setOnSeekBarChangeListener(this);
		this.slider.setMax(32767);			//setting slider max to max of signed short
		this.slider.setProgress(this.threshold*this.threshold);
		
		
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
				listen();
			}
		};
		Thread listenThread = new Thread(listener);
		listenThread.start();
		
		this.handler = new Handler(){
			public void handleMessage(Message msg){
				levels.setProgress((int)Math.sqrt(Math.abs(msg.what)));
				if((int)Math.sqrt(Math.abs(msg.what))>= threshold){
					//toggle Icon if clap detected
					toggleIcon();
				}
			}
		};
		

	}
	
	private void listen() {
		while(this.isRecording){
			int readBytes = this.recorder.read(this.buffer,0 , this.buffer.length);
			
			short max = 0; // maximum amplitude in buffer
			
			for(int i =0; i < readBytes; i++){
				max = max < buffer[i]? max : buffer[i];
			}
			handler.sendEmptyMessage((int) max);
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
	

	@Override
	public void onProgressChanged(SeekBar view, int progress, boolean arg2) {
		this.threshold = (int) Math.sqrt(progress);
		this.levels.setMax((int) Math.sqrt(progress));		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onDialogClosed(boolean positiveResult){
		this.isRecording=false;
		recorder.stop();
		recorder.release();
		if(positiveResult){
			persistInt(this.threshold);
		}
	}	
}