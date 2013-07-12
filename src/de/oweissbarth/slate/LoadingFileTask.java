package de.oweissbarth.slate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import de.oweissbarth.slate.data.ProjectFile;

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
		this.context.startActivity(loadProjectIntoMain);
		progress.dismiss();
	}
}
