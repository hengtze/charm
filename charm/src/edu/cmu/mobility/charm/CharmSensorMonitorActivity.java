package edu.cmu.mobility.charm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cmu.mobility.charm.sensors.SensorDataValues;


public class CharmSensorMonitorActivity extends Activity implements OnClickListener {
	
	private static ToggleButton buttonEnableSensing;
	private static TextView textProximity;
	private static ListView sensorListView;
	private static SimpleAdapter sensorListAdapter;
	private static ArrayList<HashMap<String,String>> sensorDataList;
	
	private static SensorController sensorController;
	private static SensorDataValues sensorDataValues;
	private static boolean isSensing = false;
	
	private static final String SENSOR_DATA_TYPE = "SensorDataType";
	private static final String SENSOR_DATA_VALUE = "SensorDataValue";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charm_settings_activity);
        initUserInterface();
        
        sensorController = SensorController.getInstance(this);        
        sensorDataValues = SensorDataValues.getInstance();
        initSensorListView();
    }
    
    @Override
    public void onResume() {
    	super.onResume();		
		SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
		buttonEnableSensing.setChecked(settings.getBoolean(getString(R.string.isSensingEnabled), false));
    }
    
    @Override
    public void onPause() {
    	SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(getString(R.string.isSensingEnabled), buttonEnableSensing.isChecked());
		editor.commit();
    	super.onPause();
    }
    
    @Override
    protected void onDestroy() {
    	SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
    	if (isSensing){
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean(getString(R.string.isSensingEnabled), false);
			editor.commit();
			
	    	sensorController.stopSensing();
    	}
    	super.onDestroy();
    }
    
    private void initUserInterface() {
    	//textProximity = (TextView) findViewById(R.id.);
    	buttonEnableSensing = (ToggleButton) findViewById(R.id.buttonEnableSensing);
    	buttonEnableSensing.setOnClickListener(this);
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
    
    public void onClick (View clickedButton) {
    	if(clickedButton.equals(buttonEnableSensing)) {    					
    		if (buttonEnableSensing.isChecked()) {
    			startSensing();
    		}
    		else {			
    			stopSensing();
    		}    		
		}
    }
    
    public static void updateSensorValues () {
		sensorDataList.clear();
		String [] dataTypeStrings = sensorDataValues.getSensorDataTypeStrings();
		double [] dataValues = sensorDataValues.getSensorDataValues();
		
		for (int i=0; i<dataValues.length; i++) {
	    	HashMap<String,String> row = new HashMap<String,String>();
	    	row.put(SENSOR_DATA_TYPE, dataTypeStrings[i]);
	    	row.put(SENSOR_DATA_VALUE, new DecimalFormat("#.##").format(dataValues[i]) );
	    	sensorDataList.add(row);
		}
		sensorListAdapter.notifyDataSetChanged();
    }
	
	private void startSensing() {
		sensorController.startSensing();
		isSensing = true;
	}
	private void stopSensing() {
		sensorController.stopSensing();
		isSensing = false;
	}
}