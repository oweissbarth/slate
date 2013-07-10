package de.oweissbarth.slate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import de.oweissbarth.slate.data.ProjectFile;

public class MainActivity extends FragmentActivity {
	private FragmentTabHost tabHost;
	public static AsyncTask<String, Void, Void> loading;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.tabFragmentLayout);

		TabSpec projectTab = tabHost.newTabSpec("project").setIndicator("Project");
		TabSpec equipmentTab  = tabHost.newTabSpec("equipment").setIndicator("Equipment");
		Log.d("TABS", "Before ListScenes");
		tabHost.addTab(projectTab, ProjectTab.class, null);
		Log.d("TABS", "After ListScenes");
		tabHost.addTab(equipmentTab, EquipmentTab.class, null);
		Log.d("TABS", "Before Equipment");
	}

	
	public void saveButton(View view){
		ProjectFile.save(ProjectFile.project, getApplicationContext());
		Toast savedNotification = Toast.makeText(getApplicationContext(), "saved File", Toast.LENGTH_SHORT);
		savedNotification.show();
	}
	
	public void onBackPressed(){
		Log.d("Back", "Back from parent Activity");
		//super.onBackPressed();
		ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.tabFragmentLayout);
	
		if(fragment instanceof EquipmentTab)
			((EquipmentTab)fragment).onBackPressed();
		else
			((ProjectTab)fragment).onBackPressed();
	}


}
