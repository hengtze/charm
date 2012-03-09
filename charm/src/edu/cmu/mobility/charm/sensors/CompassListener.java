package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CompassListener implements SensorListener, SensorEventListener {
	
	private SensorManager sensorManager;
	
	// Sensors
	private Sensor orientationSensor;
	
	// Sensor Values
	private double[] orientation;
	
	public CompassListener(Context c) {
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		orientation = new double [3];
	}
	@Override
	public boolean isAvailableOnDevice() {
		return (orientationSensor != null);
	}
	
	@Override
	public void startListening() {
		sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	public void stopListening() {
		try {
			sensorManager.unregisterListener(this);
		}
		catch(IllegalArgumentException e) {			
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		orientation[0] = event.values[0];
		orientation[1] = event.values[1];
		orientation[2] = event.values[2];
		
		SensorDataValues.setSensorValue(SensorDataValues.DataType.ORIENTATION_AZIMUTH, orientation[0]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.ORIENTATION_PITCH, orientation[1]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.ORIENTATION_ROLL, orientation[2]);
		
		CharmSensorMonitorActivity.updateSensorValues();
		
	}
	public double [] getData() {
		// TODO Auto-generated method stub
		return orientation;
	}
}