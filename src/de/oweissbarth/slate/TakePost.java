package de.oweissbarth.slate;

import de.oweissbarth.slate.data.Scene;
import de.oweissbarth.slate.data.Shot;
import de.oweissbarth.slate.data.Take;
import de.oweissbarth.slate.support.ProjectFile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;

public class TakePost extends Activity {
	private Take take;
	private Shot shot;
	private Scene scene;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_posttake);
		this.scene = ProjectFile.project.getSceneById(getIntent().getExtras().getInt("scene"));
		this.shot = this.scene.getShotById(getIntent().getExtras().getInt("shot"));
		this.take = this.shot.getTakeById(getIntent().getExtras().getInt("take")); 
	}
	
	
	public void done(View view){
		saveTake();
		startActivity(new Intent(this, MainActivity.class));
		this.finish();
	}
	
	public void nextTake(View view){
		saveTake();
		Intent i = new Intent(this, TakeShowInfo.class);
		i.putExtras(getIntent().getExtras());
		startActivity(i);
		this.finish();
	}

	public void onBackPressed(){
		if(this.take!=null){
			this.shot.deleteTake(this.take.getID());
		}
		startActivity(new Intent(this, MainActivity.class));
	}
	
	private void saveTake(){
		String comment = ((EditText)findViewById(R.id.take_posttake_comment)).getText().toString();
		boolean usable = ((CheckBox)findViewById(R.id.take_posttake_usable)).isChecked();
		
		this.take.setComment(comment);
		this.take.setUsable(usable);
	}
}
