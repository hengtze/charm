package edu.cmu.mobility.charm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;
import edu.cmu.mobility.charm.data.ClassValues;
import edu.cmu.mobility.charm.data.DataArchiveManager;
import edu.cmu.mobility.charm.data.SensorDataValues;

public class ClassLabelActivity extends Activity implements OnClickListener {
	
	private static ListView classListView;
	private static ArrayAdapter<String> classListAdapter;
	private static ClassValues classValues;
	
	private static ToggleButton buttonEnableSensingOnLabeling;
	
	private static SensorController sensorController;
	private static SensorDataValues sensorDataValues;
	private static DataArchiveManager dataManager;
	private static boolean isSensing = false;
	
//	private static final String CLASS_NAME = "ClassName";
//	private static final String CLASS_VALUE = "ClassValue";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_label_activity);
        initUserInterface();
        
        classValues = ClassValues.getInstance();
        sensorController = SensorController.getInstance(this);        
        sensorDataValues = SensorDataValues.getInstance();
        dataManager = DataArchiveManager.getInstance(this); 
        
        initClassListView();
	}
	
	private void initUserInterface () {
		classListView = (ListView) this.findViewById(R.id.listAttributes);
		buttonEnableSensingOnLabeling = (ToggleButton) findViewById(R.id.buttonEnableSensingOnLabeling);
    	buttonEnableSensingOnLabeling.setOnClickListener(this);
	}
	
	private void initClassListView() {
		classListAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_single_choice,
    			classValues.getClassNameStrings());
	    
    	//updateAttributeValues();
    	
    	classListView.setAdapter(classListAdapter);
    	classListView.setTextFilterEnabled(true);
    	classListView.setItemsCanFocus(false);
    	classListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	classListView.setOnItemClickListener(
    			new OnItemClickListener()
    			{
    				@Override
    				public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
    					classValues.setCurrentClassLabel(position);
    					Toast.makeText(getApplicationContext(),
    							"Class label is set to: " + classValues.getCurrentClassLabel(), 
    							Toast.LENGTH_SHORT).show();    					
    				}       
    			}
    			);
    }
	
	@Override
    public void onResume() {
    	super.onResume();		
		SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
		buttonEnableSensingOnLabeling.setChecked(settings.getBoolean(getString(R.string.isSensingEnabled), false));
    }
	
	@Override
    public void onPause() {
    	SharedPreferences settings = this.getSharedPreferences("charm.settings", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(getString(R.string.isSensingEnabled), buttonEnableSensingOnLabeling.isChecked());
		editor.commit();
    	super.onPause();
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
    
    @Override
    public void onBackPressed() {        
    	if ( !isSensing ) {
    		finish();
    		//moveTaskToBack(true);
    	}
    	else {
    		moveTaskToBack(true);
    	}
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
    	sensorController.onDestroy();
    	super.onDestroy();
    }
	
	public void onClick (View clickedButton) {
    	if(clickedButton.equals(buttonEnableSensingOnLabeling)) {    					
    		if (buttonEnableSensingOnLabeling.isChecked()) {
    			startSensing();    			
    		}
    		else {			
    			stopSensing();    			
    		}    		
		}
    }
	
	private void startSensing() {
		Toast.makeText(getApplicationContext(),
				"Start Sensing...", 
				Toast.LENGTH_SHORT).show(); 
		
		sensorController.startSensing();
		isSensing = true;
		CharmSensorMonitorActivity.startThreadUpdateSensorValues();
	}
	
	private void stopSensing() {
		Toast.makeText(getApplicationContext(),
				"Stop Sensing...", 
				Toast.LENGTH_SHORT).show(); 
		
		sensorController.stopSensing();
		isSensing = false;
		CharmSensorMonitorActivity.stopThreadUpdateSensorValues();
	}
	
	public static boolean isSensing() {
		return isSensing;
	}
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id);
//		MusicRecommender.mPlayerController.moveToSelectedSongPosition( position );
//		doPlaySong = true;
//		finish();
//	}
}
