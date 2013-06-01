package de.oweissbarth.slate;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		String ProjectFileName = intent.getExtras().getString("projectName");
		
		Project project = ProjectFile.load(ProjectFileName, getApplicationContext());
		
		TextView example = (TextView)findViewById(R.id.example);
		example.setText("Projectname: " + project.getName() + "\nDirector: "+ project.getDirector());
		
	}

}
