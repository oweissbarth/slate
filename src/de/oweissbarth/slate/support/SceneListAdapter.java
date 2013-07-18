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
						//itemName.setText(shot.getView() + "|" +);
						//itemDetails.setText(this.context.getResources().getStringArray(R.id.));
						itemSubcategory.setText(shot.getTakes().length + "Takes");
						break;
						
			case 2:		/*Take take =(Take)item;
						itemId.setText(String.valueOf(take.getID()));
						itemName.setText(scene.getName());
						String ext = scene.getExt() ? "Ext" : "Int";
						itemDetails.setText(extText);
						itemSubcategory.setText(scene.getShots().length+" shots");*/
						break;
		}
		
	
		return view;
	}

}
