package de.oweissbarth.slate;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class EditShot extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_shot);
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}



}
