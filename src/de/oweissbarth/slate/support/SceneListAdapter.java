package de.oweissbarth.slate.support;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.oweissbarth.slate.R;
import de.oweissbarth.slate.data.Scene;



public class SceneListAdapter extends BaseAdapter {
	private Context context;
	private Scene[] scenes;
	
	public SceneListAdapter(Context context, Scene[] scenes ){
		this.context = context;
		this.scenes = scenes;
	}
	
	@Override
	public int getCount() {
		return this.scenes.length;
	}

	@Override
	public Object getItem(int position) {
		if(position<this.scenes.length)
			return this.scenes[position];
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
		TextView sceneNumber = (TextView) view.findViewById(R.id.sceneNumber);
		TextView sceneName  = (TextView) view.findViewById(R.id.sceneName);
		TextView int_ext = (TextView) view.findViewById(R.id.intExt);
		TextView numberOfShots = (TextView) view.findViewById(R.id.numberOfShots);
		
		Scene scene = this.scenes[position];
		sceneNumber.setText(String.valueOf(scene.getID()));
		sceneName.setText(scene.getName());
		String extText = scene.getExt() ? "Ext" : "Int";
		int_ext.setText(extText);
		numberOfShots.setText(scene.getShots().length+" shots");
		return view;
	}

}
