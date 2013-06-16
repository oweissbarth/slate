package de.oweissbarth.slate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.oweissbarth.slate.data.Scene;

public class ListScenes extends ListFragment {

	private int level = 0;
	
	private int scene;
	
	private int shot;
	
	private String[] items;
		
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
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
		registerForContextMenu(getListView());
		
		switch(this.level){
		case 0:  	items = MainActivity.project.getSceneList();break;
		case 1:	  	items = MainActivity.project.getSceneById(scene).getShotList();break;
		case 2:		items = MainActivity.project.getSceneById(scene).getShotById(shot).getTakeList();break;
		default:	items = null;break;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
		setListAdapter(adapter);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, view, menuInfo);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		if(info.id != (items.length-1)){
		menu.setHeaderTitle("Edit");
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.project_tab, menu);
		}
	}
	
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int listItem = (int) menuInfo.id;
		Class editActivity = chooseEditActivity();
		Log.d("Context", "Exspected id =" + R.id.edit_item+ " , but found id = " + item.getItemId() );
		
		switch(item.getItemId()){
		case R.id.edit_item: 		Log.d("LIST", "Item Clicked");
									Intent intent = new Intent(getActivity(), editActivity);
									intent.putExtra("newObject", false);
									intent.putExtra("Id", listItem);
									Log.d("LIST", "id="+listItem+", position="+ listItem);
									Log.d("LIST", "About to start editor");
									startActivity(intent);
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
			Log.d("Back", "Back from upper level");
			Intent i = new Intent(getActivity(), IntroActivity.class);
			startActivity(i);
			getActivity().finish();
		}else{
			Log.d("Back", "Back from lower level");
			level--;
			listItems();
		}
	}
}
