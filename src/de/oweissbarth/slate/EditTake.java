/*******************************************************************************
 * Copyright 2013 Oliver Weissbarth
 * 
 * This file is part of Slate.
 * 
 *     Slate is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *      Slate is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with  Slate.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.oweissbarth.slate;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.oweissbarth.slate.data.Take;
import de.oweissbarth.slate.support.ProjectFile;

public class EditTake extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_take);
		
		Bundle extras = getIntent().getExtras();
		Take take = ProjectFile.project.getSceneById(extras.getInt("scene")).getShotById(extras.getInt("shot")).getTakeById(extras.getInt("take"));
		
		((TextView)findViewById(R.id.edit_take_comment)).setText(take.getComment());
		((CheckBox)findViewById(R.id.edit_take_usable)).setChecked(take.getUsable());
		((TextView)findViewById(R.id.edit_take_duration)).setText("duration: "+ take.getDuration());
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}
	
	public boolean discard(MenuItem item){
		this.finish();
		return true;
	}
	
	public boolean done(MenuItem item){
		String comment = ((TextView)findViewById(R.id.edit_take_comment)).getText().toString();
		boolean usable = ((CheckBox)findViewById(R.id.edit_take_usable)).isChecked();
		
		Bundle extras = getIntent().getExtras();
		Take take = ProjectFile.project.getSceneById(extras.getInt("scene")).getShotById(extras.getInt("shot")).getTakeById(extras.getInt("take"));
		
		take.setComment(comment);
		take.setUsable(usable);
		
		this.finish();
		return true;
	}
	
	protected void onStop(){
		super.onStop();
		ProjectFile.saveIfNecessary(this);
	}
}
