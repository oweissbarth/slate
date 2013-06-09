package de.oweissbarth.slate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.oweissbarth.slate.data.Scene;

public class ListScenes extends ListFragment {

	private String[] scenes = MainActivity.project.getSceneList();
		
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, scenes);
		setListAdapter(adapter);
	}
		
	public void onListItemClick(ListView parent, View view, int position, long id){
		Log.d("LIST", "Item Clicked");
		Intent intent;
		if((position+1) == scenes.length){
			intent = new Intent(getActivity(), EditScene.class);
			intent.putExtra("newObject", true);
					
		}else{
			intent = new Intent(getActivity(), EditScene.class);
			intent.putExtra("newObject", false);
			intent.putExtra("Id", id);
			Log.d("LIST", "id="+id+", position="+ position);
		}
		Log.d("LIST", "About to start editor");
		startActivity(intent);
	}
}
