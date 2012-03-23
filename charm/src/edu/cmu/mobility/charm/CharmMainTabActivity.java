package edu.cmu.mobility.charm;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TabHost;

public class CharmMainTabActivity extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_tab_activity);
	    
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    
	    intent = new Intent().setClass(this, ClassLabelActivity.class);
	    spec = tabHost.newTabSpec("ClassLabeling").setIndicator("Activity",
	                      res.getDrawable(R.drawable.icon_tab_user_label))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, CharmSensorMonitorActivity.class);
	    spec = tabHost.newTabSpec("SensorMonitor").setIndicator("Dashboard",
	                      res.getDrawable(R.drawable.icon_tab_sensor_monitor))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    
//	    intent = new Intent().setClass(this, AttributeLabelActivity.class);
//	    spec = tabHost.newTabSpec("AttributeLabeling").setIndicator("Attribute",
//	                      res.getDrawable(R.drawable.icon_tab_user_label))
//	                  .setContent(intent);
//	    tabHost.addTab(spec);	    
	    
	    intent = new Intent().setClass(this, SettingsActivity.class);
	    spec = tabHost.newTabSpec("Settings").setIndicator("Settings",
	                      res.getDrawable(R.drawable.icon_tab_sensor_monitor))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(3);
	}
}
