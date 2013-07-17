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

import android.app.Activity;
import android.app.ActivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
		ProjectFile.save(getApplicationContext());
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
		ProjectFile.saveIfNecessary(this);
	}
	

	
	
}
