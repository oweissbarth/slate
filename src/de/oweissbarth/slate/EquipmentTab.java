package de.oweissbarth.slate;

import de.oweissbarth.slate.data.ProjectFile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

public class EquipmentTab extends ListFragment {
	
	private byte category = -1;

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		listItems();	
	}
	
	public void onListItemClick(ListView parent, View view, int position, long id){
		if(category == -1){
			this.category = (byte) position;
			listItems();
			
		}else{
			Intent i;
			switch(category){
				case 0:		i = new Intent(getActivity(), EditCamera.class);break;
				case 1:		i = new Intent(getActivity(), EditLens.class);break;
				case 2:		i = new Intent(getActivity(), EditMedia.class);break;
				default:	i = null;break;
			}
			i.putExtra("id", position);
			startActivity(i);
		}
	}

	private void listItems(){
		ArrayAdapter<String> a = null;
		switch(category){
			case -1:	a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"Cameras", "Lenses", "Media"});break;
			
			case 0:		a = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, ProjectFile.project.getEquipment().getCameraList());break;
						
			case 1:		a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ProjectFile.project.getEquipment().getLensList());break;
			
			case 2:		a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ProjectFile.project.getEquipment().getMediaList());break;
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
			startActivity(i);
			getActivity().finish();
		}else{
			Log.d("Equipment", "Back from lower level");
			category=-1;
			listItems();
		}
	}
}
