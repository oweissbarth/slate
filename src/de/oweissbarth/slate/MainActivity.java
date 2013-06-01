package de.oweissbarth.slate;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.content.Intent;

public class MainActivity extends FragmentActivity {
	private Project project;
	private FragmentTabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		String ProjectFileName = intent.getExtras().getString("projectName");
		
		project = ProjectFile.load(ProjectFileName, getApplicationContext());
		
		tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		tabHost.setup(getApplicationContext(), getSupportFragmentManager());
		
		
		TabSpec projectTab = tabHost.newTabSpec("project").setIndicator("Project");
		TabSpec equipmentTab  = tabHost.newTabSpec("equipment").setIndicator("Equipment");
		
		tabHost.addTab(projectTab, ProjectTab.class, null);
			
		tabHost.addTab(equipmentTab, EquipmentTab.class, null);

	}
	
	public void saveButton(View view){
		ProjectFile.save(project, getApplicationContext());
	}

}
