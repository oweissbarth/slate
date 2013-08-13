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
package de.oweissbarth.slate.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.oweissbarth.slate.R;
import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.data.Take;



public class SceneListAdapter extends BaseAdapter {
	private Context context;
	private Object[] items;
	private int level;
	
	public SceneListAdapter(Context context, Object[] items, int level ){
		this.context = context;
		this.items = items;
		this.level=level;
	}
	
	@Override
	public int getCount() {
		return this.items.length;
	}

	@Override
	public Object getItem(int position) {
		if(position<this.items.length)
			return this.items[position];
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.scene_list_item, null);
		}
		TextView itemId = (TextView) view.findViewById(R.id.listItemId);
		TextView itemName  = (TextView) view.findViewById(R.id.listItemName);
		TextView itemDetails = (TextView) view.findViewById(R.id.listItemDetails);
		TextView itemSubcategory = (TextView) view.findViewById(R.id.listItemSubcategory);
		
		Object item = this.items[position];
		
		switch(this.level){
			case 0:		Scene scene = (Scene)item;
						itemId.setText(String.valueOf(scene.getID()));
						itemName.setText(scene.getName());
						String extText = scene.getExt() ? "Ext" : "Int";
						itemDetails.setText(extText);
						itemSubcategory.setText(scene.getShots().length+" shots");
						break;
			
			case 1:		Shot shot = (Shot)item;
						itemId.setText(String.valueOf(shot.getID()));
						itemName.setText(this.context.getResources().getStringArray(R.array.fieldSize)[shot.getFieldSize()] + "|" + shot.getFocalLength());
						itemDetails.setText(this.context.getResources().getStringArray(R.array.cameraMotion)[shot.getCameraMotion()]);
						itemSubcategory.setText(shot.getTakes().length + " Takes");
						break;
						
			case 2:		Take take =(Take)item;
						itemId.setText(String.valueOf(take.getID()));
						itemName.setText(take.getUsabale()? "usable": "not usable");
						itemDetails.setText(take.getComment());
						itemSubcategory.setText(take.getDuration()+"s");
						break;
		}
		
	
		return view;
	}

}
