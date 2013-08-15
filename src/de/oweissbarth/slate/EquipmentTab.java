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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

import de.oweissbarth.slate.support.ProjectFile;
import de.oweissbarth.slate.support.ThreePosListAdapter;

public class EquipmentTab extends SherlockListFragment implements OnClickListener {
	
	private byte category = -1;
	private Object[] items;
	private Button footer;

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		MainActivity.equipmentFragment = this;
		
		this.footer= new Button(getActivity());
		
		listItems();
	}
	
	public void onListItemClick(ListView parent, View view, int position, long id){
		if(category == -1){
			this.category = (byte) position;
			Log.d("Equipment", "Set category to " + this.category);
			listItems();
			
		}else{
			Intent i;
			switch(category){
				case 0:		i = new Intent(getActivity(), EditCamera.class);break;
				case 1:		i = new Intent(getActivity(), EditLens.class);break;
				case 2:		i = new Intent(getActivity(), EditMedia.class);break;
				default:	i = null;break;
			}
			Log.d("Equipment", "(items.length; position)=("+items.length+";"+position+")");
			i.putExtra("newObject", false);
			i.putExtra("id", position);
			startActivity(i);
		}
	}

	private void listItems(){
		ThreePosListAdapter a = null;
		
		switch(category){
			case -1:	setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"Cameras", "Lenses", "Media"}));getListView().removeFooterView(this.footer);return;
			
			case 0:		this.items = ProjectFile.project.getEquipment().getAvailableCameras();this.footer.setText("Add Camera");break;
						
			case 1:		this.items = ProjectFile.project.getEquipment().getAvailableLenses();this.footer.setText("Add Lens");break;
			
			case 2:		this.items = ProjectFile.project.getEquipment().getAvailableMedia();this.footer.setText("Add Media");break;
		}
		a = new ThreePosListAdapter(getActivity(), this.items, this.category);
		getListView().removeFooterView(this.footer);
		if(this.category!=-1 && getListView().getFooterViewsCount()==0){
			this.footer.setOnClickListener(this);
			getListView().addFooterView(this.footer);
		}
		setListAdapter(a);
	}


	public void onResume(){
		super.onResume();
		listItems();
	}
	
	public void onBackPressed(){
		if(category == -1){
			Log.d("Equipment", "Back from upper level");
			Intent i = new Intent(getActivity(), IntroActivity.class);
			i.putExtra("back", true);
			startActivity(i);
			getActivity().finish();
		}else{
			Log.d("Equipment", "Back from lower level");
			category=-1;
			listItems();
		}
	}

	@Override
	public void onClick(View view) {
		Intent i;
		switch(category){
			case 0:		i = new Intent(getActivity(), EditCamera.class);break;
			case 1:		i = new Intent(getActivity(), EditLens.class);break;
			case 2:		i = new Intent(getActivity(), EditMedia.class);break;
			default:	i = null;break;
		}
			i.putExtra("newObject", true);
		startActivity(i);
	}

}
