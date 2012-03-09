package edu.cmu.mobility.charm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
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
	
	private static CharmSensorManager charmSensorManager;
	private static SensorDataValues sensorDataValues;
	
	private static boolean isSensingEnabled = false;
	private static final String SENSOR_DATA_TYPE = "SensorDataType";
	private static final String SENSOR_DATA_VALUE = "SensorDataValue";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charm_settings_activity);
        initUserInterface();
        
        charmSensorManager = CharmSensorManager.getInstance(this);        
        sensorDataValues = SensorDataValues.getInstance();
        setSensorListView();
    }
    private void initUserInterface() {
    	//textProximity = (TextView) findViewById(R.id.);
    	buttonEnableSensing = (ToggleButton) findViewById(R.id.buttonEnableSensing);
    	buttonEnableSensing.setOnClickListener(this);
    	sensorListView = (ListView) this.findViewById(R.id.listSensorData); 
    }
    
    private void setSensorListView() {
    	sensorDataList = new ArrayList<HashMap<String,String>>();
    	sensorListAdapter = new SimpleAdapter(this, sensorDataList,
                android.R.layout.simple_list_item_2, 
                new String[] { SENSOR_DATA_TYPE, SENSOR_DATA_VALUE }, 
                new int[] { android.R.id.text1, android.R.id.text2 });
	    
    	updateSensorValues();
    	
    	sensorListView.setAdapter(sensorListAdapter);
    	sensorListView.setTextFilterEnabled(true);
    }
    
    public void onClick (View clickedButton) {
    	if(clickedButton.equals(buttonEnableSensing)) {
    		if (buttonEnableSensing.isChecked()) {
    			isSensingEnabled = true;
    			startSensing();
    		}
    		else {
    			isSensingEnabled = false;    			
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
//	    	Double value = dataValues[i];
	    	
	    	row.put(SENSOR_DATA_TYPE, dataTypeStrings[i]);
	    	row.put(SENSOR_DATA_VALUE, new DecimalFormat("#.##").format(dataValues[i]) );
	    	sensorDataList.add(row);
		}
		sensorListAdapter.notifyDataSetChanged();
    }
	
	private void startSensing() {
		charmSensorManager.startSensing();
	}
	private void stopSensing() {
		charmSensorManager.stopSensing();
	}
}