package edu.cmu.mobility.charm;

import edu.cmu.mobility.charm.sensors.AccelerometerListener;
import edu.cmu.mobility.charm.sensors.CompassListener;
import android.app.Activity;
import android.os.Bundle;



public class CharmSettingsActivity extends Activity {
	
	private AccelerometerListener accelerometerListener;
	private CompassListener compassListener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charm_settings_activity);
        
        initUserInterface();
        
        accelerometerListener = new AccelerometerListener(this);
        compassListener = new CompassListener(this);
    }
    private void initUserInterface() {
    	
    }
}