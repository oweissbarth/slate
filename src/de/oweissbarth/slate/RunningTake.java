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

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import de.oweissbarth.slate.support.ProjectFile;

public class RunningTake extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_running_take);
		  this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				  
	}
	
	protected void onStop(){
		super.onStop();
		ProjectFile.saveIfNecessary(this);
	}


}
