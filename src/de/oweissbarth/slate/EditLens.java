package de.oweissbarth.slate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.oweissbarth.slate.data.Lens;
import de.oweissbarth.slate.data.ProjectFile;

public class EditLens extends SherlockActivity {
	private boolean newObject;
	private int lens;
	private boolean fixed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lens);
		checkIfNew();
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}
	
	public void onResume(){
		super.onResume();
		checkIfNew();
	}
	
	
	private void checkIfNew(){
		Intent i = getIntent();
		
		newObject = i.getExtras().getBoolean("newObject");
		Log.d("EDITOR", "NewObject?" + newObject);
		
		
		if(!newObject){
			Log.d("EDITOR", "Existing Lens");
			this.lens=i.getExtras().getInt("id");
			this.fixed = ProjectFile.project.getEquipment().getLensById(lens).getFixed();
			((EditText) findViewById(R.id.lensName)).setText(ProjectFile.project.getEquipment().getLensById(lens).getName());
			((ToggleButton) findViewById(R.id.focusType)).setChecked(ProjectFile.project.getEquipment().getLensById(lens).getFixed());
			((EditText) findViewById(R.id.minFocalLengthValue)).setText(ProjectFile.project.getEquipment().getLensById(lens).getMinFocalLength());
			if(!fixed){
			((LinearLayout)findViewById(R.id.maxFocalLength)).setVisibility(View.GONE);
			((EditText) findViewById(R.id.maxFocalLengthValue)).setText(ProjectFile.project.addEquipment().getLensById(lens).getMaxFocalLength());
			}
		}else{
			((EditText) findViewById(R.id.lensName)).setText("");
			((EditText) findViewById(R.id.minFocalLengthValue)).setText("");
			((LinearLayout)findViewById(R.id.maxFocalLength)).setVisibility(View.GONE);
			((EditText) findViewById(R.id.maxFocalLengthValue)).setText("");
			((ToggleButton)findViewById(R.id.focusType)).setChecked(true);
		}
	}
	
	public boolean discard(MenuItem item){
		this.finish();
		return true;
	}
	
	public boolean done(MenuItem item){
		Lens lens;
		if(this.newObject){
			lens = ProjectFile.project.getEquipment().addLens();
		}else{
			lens = ProjectFile.project.getEquipment().getLensById(this.lens);
		}
		Log.d("SAVE", "fixed:"+this.fixed);
		String name = ((EditText) findViewById(R.id.lensName)).getText().toString();
		boolean fixed = !((ToggleButton) findViewById(R.id.focusType)).isChecked();
		int minFocalLength = Integer.parseInt(((EditText) findViewById(R.id.minFocalLengthValue)).getText().toString());
		int maxFocalLength=0;
		if(!this.fixed){
			 maxFocalLength = Integer.parseInt(((EditText) findViewById(R.id.maxFocalLengthValue)).getText().toString());
		}
		
		lens.setName(name);
		lens.setFixed(fixed);
		lens.setMinFocalLength(minFocalLength);
		if(!this.fixed)
			lens.setMaxFocalLength(maxFocalLength);
		
		this.finish();
		return true;
	}
	
	public void changeFocusType(View view){
		ToggleButton focusType = (ToggleButton)findViewById(R.id.focusType);
		LinearLayout maxFocalLength = (LinearLayout)findViewById(R.id.maxFocalLength);
		
		if(focusType.isChecked()){
			//fixed Focus
			maxFocalLength.setVisibility(View.GONE);
			((EditText)findViewById(R.id.minFocalLengthValue)).setHint("focal length");

		}else{
			//variable Focus
			maxFocalLength.setVisibility(View.VISIBLE);
			((EditText)findViewById(R.id.minFocalLengthValue)).setHint("minimal focal length");
		}
	}

}
