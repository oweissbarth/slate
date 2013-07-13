package de.oweissbarth.slate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.oweissbarth.slate.data.Media;
import de.oweissbarth.slate.data.ProjectFile;

public class EditMedia extends SherlockActivity {
	private boolean newObject;
	private int media;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_media);
		checkIfNew();
		Spinner storageType = (Spinner)findViewById(R.id.storageType);
		
		 storageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			 public void onItemSelected(AdapterView<?> adapter, View view, int i, long position){
				 Spinner storageFormat = (Spinner)findViewById(R.id.storageFormat);
				 
				if(position<=4)
					 storageFormat.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.storageFormatByte)));
				else if(position<=8)
					 storageFormat.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.storageFormatFilm)));
				else if (position<=14)
					 storageFormat.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.storageFormatTime)));
						
			 }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		 });
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}

	private void checkIfNew(){
		Intent i = getIntent();
		
		newObject = i.getExtras().getBoolean("newObject");
		Log.d("EDITOR", "NewObject?" + newObject);
		
		
		if(!newObject){
			Log.d("EDITOR", "Existing Media");
			this.media=i.getExtras().getInt("id");
			((EditText) findViewById(R.id.mediaName)).setText(ProjectFile.project.getEquipment().getMediaById(media).getName());
			((Spinner) findViewById(R.id.storageType)).setSelection(ProjectFile.project.getEquipment().getMediaById(media).getType());
			((EditText) findViewById(R.id.storageSize)).setText(ProjectFile.project.getEquipment().getMediaById(media).getStorage());
			((Spinner) findViewById(R.id.storageFormat)).setSelection(ProjectFile.project.addEquipment().getMediaById(media).getStorageFormat());
			
		}else{
			Log.d("EDITOR", "New Media");
			((EditText) findViewById(R.id.mediaName)).setText("");
			((Spinner) findViewById(R.id.storageType)).setSelection(0);
			((EditText) findViewById(R.id.storageSize)).setText("");
			((Spinner) findViewById(R.id.storageFormat)).setSelection(0);
		}
	}
	
	public void onResume(){
		super.onResume();
		Log.d("EDITOR", "On resume");
		checkIfNew();
	}
	
	public boolean done(MenuItem item){
		Media media;
		if(newObject){
			media = ProjectFile.project.getEquipment().addMedia();
		}else{
			media = ProjectFile.project.getEquipment().getMediaById(this.media);	
		}
		
		String name = ((EditText)findViewById(R.id.mediaName)).getText().toString();
		int type = ((Spinner) findViewById(R.id.storageType)).getSelectedItemPosition();
		int storageSize= Integer.parseInt(((EditText)findViewById(R.id.storageSize)).getText().toString());
		int format = ((Spinner) findViewById(R.id.storageFormat)).getSelectedItemPosition();
		
		media.setName(name);
		media.setType((short)type);
		media.setStorage(storageSize);
		media.setStorageFormat(format);
		this.finish();
		return true;
	}
	
	public boolean discard(MenuItem item){
		this.finish();
		return true;
	}
	

}
