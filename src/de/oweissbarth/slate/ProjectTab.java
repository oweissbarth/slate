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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

import de.oweissbarth.slate.support.ProjectFile;
import de.oweissbarth.slate.support.SceneListAdapter;

public class ProjectTab extends SherlockListFragment implements OnClickListener{

	private byte level = 0;
	
	private int scene;
	
	private int shot;
	
	private Object[] items;
	
	private Button footer;
		
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		MainActivity.projectFragment= this;
		this.footer = new Button(getActivity());
		this.footer.setText("Add Scene");
		this.footer.setOnClickListener(this);
		getListView().addFooterView(this.footer);
		this.listItems();
	}
		
	public void onListItemClick(ListView parent, View view, int position, long id){ 
		Log.d("CLICK", "id = "+ id + ", pos="+ position);

			switch(this.level){
				case 0:		this.level++;
							this.scene = (int)id;
							break;
							
				case 1:		this.level++;
							this.shot = (int)id;
							break;
			}
		listItems();
	}
	
	private void listItems(){	
		
		switch(this.level){
		case 0:  	items =	ProjectFile.project.getScenes();this.footer.setText("Add Scene");break;
		case 1:	  	items = ProjectFile.project.getSceneById(scene).getShots();this.footer.setText("Add Shot");break;
		case 2:		items = ProjectFile.project.getSceneById(scene).getShotById(shot).getTakes();this.footer.setText("Add Take");break;
		default:	items = null;break;
		}
		setListAdapter(new SceneListAdapter(this.getActivity(), items, this.level));
		
		registerForContextMenu(getListView());
	}
	
	@Override
	public void onClick(View view) {
		Class editActivity = chooseEditActivity();
		Intent intent = new Intent(getActivity(), editActivity);
		intent.putExtra("newObject", true);
		if(this.level>0)
			intent.putExtra("scene", this.scene);
		if(this.level>1)
			intent.putExtra("shot", this.shot);
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
			
			case R.id.delete_item:
										switch(level){
											case 0:		ProjectFile.project.deleteScene(listItem);
														break;
											
											case 1:		ProjectFile.project.getSceneById(scene).deleteShot(listItem);
														break;
											
											case 2:		ProjectFile.project.getSceneById(scene).getShotById(shot).deleteTake(listItem);
														break;
		}
										listItems();
										return true;
										
		}
		return false;
	}
	
	private Class chooseEditActivity(){
		switch(this.level){
			case 0: 	return EditScene.class;
			case 1:		return EditShot.class;
			case 2:		return TakeShowInfo.class;
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
	
	public void setScene(int scene){
		this.scene=scene;
	}
	
	public void setShot(int shot){
		this.shot=shot;
	}
	
	public void setLevel(int level){
		this.level=(byte) level;
	}
}
