package de.oweissbarth.slate;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class EditLens extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lens);
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}

}
