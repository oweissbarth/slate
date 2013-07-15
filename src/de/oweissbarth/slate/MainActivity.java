package de.oweissbarth.slate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;

import de.oweissbarth.slate.data.ProjectFile;

public class MainActivity extends SherlockFragmentActivity {
	private ActionBar actionBar;
	public static AsyncTask<String, Void, Void> loading;
	public static SherlockListFragment projectFragment;
	public static SherlockListFragment equipmentFragment;

	
	public void onCreate(Bundle savedInstanceState) {
		Log.d("ACTIONBAR", "Started Main");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		actionBar = getSupportActionBar();
		
		Log.d("ACTIONBAR", "Got ActionBar");
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab projectTab = actionBar.newTab();
		ActionBar.Tab equipmentTab = actionBar.newTab();
		
		Log.d("ACTIONBAR", "Created Tabs");
		
		projectTab.setText("Project");
		projectTab.setTag("projectTab");
		equipmentTab.setText("Equipment");
		equipmentTab.setTag("equipmentTab");
		
		projectTab.setTabListener(new MainTabListener());
		equipmentTab.setTabListener(new MainTabListener());
		
		Log.d("ACTIONBAR", "Set onTabListener");
		
		actionBar.addTab(projectTab);
		actionBar.addTab(equipmentTab);
		
		Log.d("ACTIONBAR", "Added Tabs");
	}
	
	

	
	public void save(){
		ProjectFile.save(ProjectFile.project, getApplicationContext());
		Toast savedNotification = Toast.makeText(getApplicationContext(), "saved File", Toast.LENGTH_SHORT);
		savedNotification.show();
	}
	
	public void onBackPressed(){
		Log.d("Back", "Back from parent Activity");
		int activeTab = actionBar.getSelectedTab().getPosition();
		switch(activeTab){
		case 0: ((ProjectTab) projectFragment).onBackPressed();break;
		case 1: ((EquipmentTab) equipmentFragment).onBackPressed();break;
		}
		
	}
	
	protected void onStop(){
		super.onStop();
		save();
	}


}
