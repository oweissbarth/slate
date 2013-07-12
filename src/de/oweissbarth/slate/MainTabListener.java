package de.oweissbarth.slate;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;

public class MainTabListener implements TabListener {

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (tab.getPosition()==0){
				ProjectTab frag = new ProjectTab();
				ft.replace(android.R.id.content, frag);
			}else if(tab.getPosition()==1){
				EquipmentTab frag = new EquipmentTab();
				ft.replace(android.R.id.content, frag);
			}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
