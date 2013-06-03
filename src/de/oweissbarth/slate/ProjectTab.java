package de.oweissbarth.slate;

import de.oweissbarth.slate.data.Scene;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class ProjectTab extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_project_tab, container, false);
		
		ExpandableListView list = (ExpandableListView) v.findViewById(R.id.project_list);
		
		Scene[] availableScenes = MainActivity.project.getScenes();
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.test_list_item, availableScenes.toString());
		//list.setAdapter(adapter);
		
		
		
		return v;
	}



}
