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
package de.oweissbarth.slate.support;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import de.oweissbarth.slate.MainActivity;

public class LoadingFileTask extends AsyncTask<String, Void, Void>{
	private ProgressDialog progress;
	private Context context;
	public LoadingFileTask(ProgressDialog progress, Context context){
		this.progress = progress;
		this.context = context;
	}

	public void onPreExecute(){
		progress.show();
	}
	protected Void doInBackground(String... params) {
		ProjectFile.project = ProjectFile.load(params[0], this.context, progress);
		return null;
	}
	public void onPostExecute(Void unused){
		Intent loadProjectIntoMain = new Intent(this.context, MainActivity.class);
		progress.dismiss();
		this.context.startActivity(loadProjectIntoMain);
	}
}
