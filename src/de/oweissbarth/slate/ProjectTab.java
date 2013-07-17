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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

import de.oweissbarth.slate.support.ProjectFile;
import de.oweissbarth.slate.support.SceneListAdapter;

public class ProjectTab extends SherlockListFragment implements OnClickListener{

	private byte level = 0;
	
	private int scene;
	
	private int shot;
	
	private String[] items;
		
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		MainActivity.projectFragment= this;
		Button footer = new Button(getActivity());
		footer.setText("Add Scene");
		footer.setOnClickListener(this);
		getListView().addFooterView(footer);
		this.listItems();
	}
		
	public void onListItemClick(ListView parent, View view, int position, long id){ 
		Log.d("CLICK", "id = "+ id + ", pos="+ position);
		int listLength = this.items.length;

		if((id+1) == listLength){
			Class editActivity = chooseEditActivity();
			Intent intent = new Intent(getActivity(), editActivity);
			intent.putExtra("newObject", true);
			startActivity(intent);
		}else{
			switch(this.level){
				case 0:		this.level++;
							this.scene = (int)id;
							break;
							
				case 1:		this.level++;
							this.shot = (int) id;
							break;
			}
		listItems();
		}
	}
	
	private void listItems(){	

		switch(this.level){
		case 0:  	setListAdapter(new SceneListAdapter(this.getActivity(), ProjectFile.project.getScenes()));break;
		case 1:	  	items = ProjectFile.project.getSceneById(scene).getShotList();break;
		case 2:		items = ProjectFile.project.getSceneById(scene).getShotById(shot).getTakeList();break;
		default:	items = null;break;
		}
		registerForContextMenu(getListView());
	}
	
	@Override
	public void onClick(View view) {
		Class editActivity = chooseEditActivity();
		Intent intent = new Intent(getActivity(), editActivity);
		intent.putExtra("newObject", true);
		startActivity(intent);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, view, menuInfo);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Edit");
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.project_tab, menu);
	}
	
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int listItem = (int) menuInfo.id;
		Class editActivity = chooseEditActivity();
		
		switch(item.getItemId()){
			case R.id.edit_item: 		Log.d("LIST", "Item Clicked");
										Intent intent = new Intent(getActivity(), editActivity);
										intent.putExtra("newObject", false);
										intent.putExtra("level", level);
										switch(level){
											case 0:		intent.putExtra("scene", listItem);
														break;
											
											case 1:		intent.putExtra("scene", scene);
														intent.putExtra("shot", listItem);
														break;
										}
										Log.d("LIST", "id="+listItem+", position="+ listItem);
										Log.d("LIST", "About to start editor");
										startActivity(intent);
										return true;
			
			case R.id.delete_item:		/*Builder confirmation = new AlertDialog.Builder(getActivity());
										confirmation.setMessage("Do you really want to delete this? I warned you, so don't blame me later");
										confirmation.setCancelable(true);
										confirmation.setPositiveButton("Confirm", this);
										confirmation.setNegativeButton("Cancel", this);
										AlertDialog dialog = confirmation.create();*/
										ProjectFile.project.deleteScene(listItem);
										listItems();
										return true;
										
		}
		return false;
	}
	
	private Class chooseEditActivity(){
		switch(this.level){
			case 0: 	return EditScene.class;
			case 1:		return EditShot.class;
			default:	return null;
		}
	}
	
	public void onBackPressed(){
		if(level == 0){
			Log.d("Project", "Back from upper level");
			Intent i = new Intent(getActivity(), IntroActivity.class);
			i.putExtra("back", true);
			startActivity(i);
			getActivity().finish();
		}else{
			Log.d("Project", "Back from lower level");
			level--;
			listItems();
		}
	}
	
	public void onResume(){
		super.onResume();
		listItems();
	}
}
