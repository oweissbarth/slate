package de.oweissbarth.slate;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class RunningTake extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_running_take);
		  this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				  
	}


}
