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

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


import de.oweissbarth.slate.support.ProjectFile;

public class MainActivity extends SherlockFragmentActivity implements TabListener{
	private ActionBar actionBar;
	public static AsyncTask<String, Void, Void> loading;
	public static SherlockListFragment projectFragment;
	public static SherlockListFragment equipmentFragment;
	ProjectTab pTab;
	EquipmentTab eTab;

	
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
		
		projectTab.setTabListener(this);
		equipmentTab.setTabListener(this);
		
		Log.d("ACTIONBAR", "Set onTabListener");
		
		actionBar.addTab(projectTab);
		actionBar.addTab(equipmentTab);
		
		Log.d("ACTIONBAR", "Added Tabs");
		
		
		if(getIntent().getExtras()!=null){
			int scene = getIntent().getExtras().getInt("scene", -15);
			int shot = getIntent().getExtras().getInt("shot", -15);
			Log.d("Back after Take", "got Values"+scene+", "+ shot);
			if(scene!=-15 && shot!=-15){
				pTab.setScene(scene);
				pTab.setShot(shot);
				pTab.setLevel(2);
				Log.d("BAck after Take", "set values");
			}
		}
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
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

	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		if (tab.getPosition()==0){
			if(this.pTab==null)
				this.pTab= new ProjectTab();
			ft.replace(android.R.id.content, pTab);
		}else if(tab.getPosition()==1){
			if(this.eTab==null)
				this.eTab=new EquipmentTab();
			ft.replace(android.R.id.content, eTab);
		}
		
	}




	@Override
	public void onTabUnselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onTabReselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean help(MenuItem item){
		startActivity(new Intent(this, Help.class));
		return true;
	}

	public boolean about(MenuItem item){
		startActivity(new Intent(this, About.class));
		return true;
	}
	
	public boolean settings(MenuItem item){
		startActivity(new Intent(this, Settings.class));
		return true;
	}




	
	
}
