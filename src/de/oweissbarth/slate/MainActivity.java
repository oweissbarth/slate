package de.oweissbarth.slate;

import de.oweissbarth.slate.data.Project;
import de.oweissbarth.slate.data.ProjectFile;
import de.oweissbarth.slate.data.Scene;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.content.Intent;

public class MainActivity extends FragmentActivity {
	static  Project project;
	private FragmentTabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		String ProjectFileName = intent.getExtras().getString("projectName");
		
		project = ProjectFile.load(ProjectFileName, getApplicationContext());
		
		Scene testScene = project.addScene();
		testScene.setDescription("Das ist meine erste TestSzene");
		testScene.setName("DerTest");
		
		
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.tabFragmentLayout);
		
		
		TabSpec projectTab = tabHost.newTabSpec("project").setIndicator("Project");
		TabSpec equipmentTab  = tabHost.newTabSpec("equipment").setIndicator("Equipment");
		Log.d("TABS", "Before ListScenes");
		tabHost.addTab(projectTab, ListScenes.class, null);
		Log.d("TABS", "After ListScenes");
			
		tabHost.addTab(equipmentTab, EquipmentTab.class, null);
		Log.d("TABS", "Before Equipment");
	}
	
	public void saveButton(View view){
		ProjectFile.save(project, getApplicationContext());
	}

}
