package edu.cmu.mobility.charm;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;
import edu.cmu.mobility.charm.data.DataArchiveManager;
import edu.cmu.mobility.charm.data.SensorDataValues;


public class CharmSensorMonitorActivity extends Activity {
	
	private static ToggleButton buttonEnableSensing;
	private static ListView sensorListView;
	private static SimpleAdapter sensorListAdapter;
	private static ArrayList<HashMap<String,String>> sensorDataList = null;
	
	private static SensorController sensorController;
	private static SensorDataValues sensorDataValues;
	private static DataArchiveManager dataManager;
	private static boolean isSensing = false;
	
	private static final String SENSOR_DATA_TYPE = "SensorDataType";
	private static final String SENSOR_DATA_VALUE = "SensorDataValue";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charm_sensor_monitor_activity);
        initUserInterface();
        
        sensorController = SensorController.getInstance(this);        
        sensorDataValues = SensorDataValues.getInstance();
        dataManager = DataArchiveManager.getInstance(this);        
        initSensorListView();
    }
    
    @Override
    public void onResume() {
    	super.onResume();		
//		SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
//		buttonEnableSensing.setChecked(settings.getBoolean(getString(R.string.isSensingEnabled), false));
    }
    
    @Override
    public void onPause() {
//    	SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
//		SharedPreferences.Editor editor = settings.edit();
//		editor.putBoolean(getString(R.string.isSensingEnabled), buttonEnableSensing.isChecked());
//		editor.commit();
    	super.onPause();
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
    
    @Override
    protected void onDestroy() {
//    	SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
//    	if (isSensing){
//			SharedPreferences.Editor editor = settings.edit();
//			editor.putBoolean(getString(R.string.isSensingEnabled), false);
//			editor.commit();
//			
//	    	sensorController.stopSensing();
//    	}
//    	sensorController.onDestroy();
    	super.onDestroy();
    }
    
//    @Override
//    public void onBackPressed() {        
//    	if ( !isSensing ) {
//    		finish();
//    		//moveTaskToBack(true);
//    	}
//    	else {
//    		moveTaskToBack(true);
//    	}
//    }
    
    private void initUserInterface() {
    	//textProximity = (TextView) findViewById(R.id.);
//    	buttonEnableSensing = (ToggleButton) findViewById(R.id.buttonEnableSensing);
//    	buttonEnableSensing.setOnClickListener(this);
    	sensorListView = (ListView) this.findViewById(R.id.listSensorData); 
    }
    
    private void initSensorListView() {
    	sensorDataList = new ArrayList<HashMap<String,String>>();
    	sensorListAdapter = new SimpleAdapter(this, sensorDataList,
    			R.layout.sensor_list,
                //android.R.layout.simple_list_item_2, 
                new String[] { SENSOR_DATA_TYPE, SENSOR_DATA_VALUE }, 
                new int[] { R.id.sensorListText1, R.id.sensorListText2 });
	    
    	updateSensorValues();
    	
    	sensorListView.setAdapter(sensorListAdapter);
    	sensorListView.setTextFilterEnabled(true);
    }
    
//    public void onClick (View clickedButton) {
//    	if(clickedButton.equals(buttonEnableSensing)) {    					
//    		if (buttonEnableSensing.isChecked()) {
//    			startSensing();    			
//    		}
//    		else {			
//    			stopSensing();    			
//    		}    		
//		}
//    }
    
    public static void updateSensorValues () {
    	if (sensorDataList != null) {
			sensorDataList.clear();
			String [] dataTypeStrings = sensorDataValues.getSensorDataTypeStrings();
			double [] dataValues = sensorDataValues.getSensorDataValues();
			
			for (int i=0; i<dataValues.length; i++) {
		    	HashMap<String,String> row = new HashMap<String,String>();
		    	row.put(SENSOR_DATA_TYPE, dataTypeStrings[i]);
		    	row.put(SENSOR_DATA_VALUE, String.format("%.2f", dataValues[i]) );
		    	//row.put(SENSOR_DATA_VALUE, new DecimalFormat("#.##").format(dataValues[i]) );
		    	sensorDataList.add(row);
			}
			sensorListAdapter.notifyDataSetChanged();
    	}
    }
	
//	private void startSensing() {
//		Toast.makeText(getApplicationContext(),
//				"Start Sensing...", 
//				Toast.LENGTH_SHORT).show(); 
//		
//		sensorController.startSensing();
//		isSensing = true;
//		
//		DataArchiveManager.setSessionTimestamp();
//		dataManager.createOutputFile(Sensor.TYPE_LINEAR_ACCELERATION);
//		dataManager.createOutputFile(Sensor.TYPE_GYROSCOPE);
//		dataManager.createOutputFile(Sensor.TYPE_GRAVITY);		
//	}
//	
//	private void stopSensing() {
//		Toast.makeText(getApplicationContext(),
//				"Stop Sensing...", 
//				Toast.LENGTH_SHORT).show(); 
//		
//		sensorController.stopSensing();
//		isSensing = false;
//	}
}