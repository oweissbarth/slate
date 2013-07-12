package de.oweissbarth.slate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

import de.oweissbarth.slate.data.ProjectFile;

public class EquipmentTab extends SherlockListFragment {
	
	private byte category = -1;
	private String[] items;

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		MainActivity.equipmentFragment = this;
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
			if(position==(items.length-1))
				i.putExtra("newObject", true);
				
			i.putExtra("id", position);
			startActivity(i);
		}
	}

	private void listItems(){
		ArrayAdapter<String> a = null;
		switch(category){
			case -1:	this.items =  new String[]{"Cameras", "Lenses", "Media"};break;
			
			case 0:		this.items = ProjectFile.project.getEquipment().getCameraList();break;
						
			case 1:		this.items = ProjectFile.project.getEquipment().getLensList();break;
			
			case 2:		this.items = ProjectFile.project.getEquipment().getMediaList();break;
		}
		a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, this.items);
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
			startActivity(i);
			getActivity().finish();
		}else{
			Log.d("Equipment", "Back from lower level");
			category=-1;
			listItems();
		}
	}
}
