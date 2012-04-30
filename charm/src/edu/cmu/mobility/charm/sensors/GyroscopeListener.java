package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import edu.cmu.mobility.charm.SettingsActivity;
import edu.cmu.mobility.charm.data.DataArchiveManager;
import edu.cmu.mobility.charm.data.SensorDataValues;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GyroscopeListener implements SensorListener, SensorEventListener {
	
	private SensorManager sensorManager;
	
	// Sensors
	private Sensor gyroscope;
	
	// Sensor Values
	private double[] rotationRates;
	
	public GyroscopeListener(Context c) {
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		rotationRates = new double [3];
	}
	@Override
	public boolean isAvailableOnDevice() {
		return (gyroscope != null);
	}
	
	@Override
	public void startListening() {
		sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
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
		long timestamp = System.currentTimeMillis();
		rotationRates[0] = event.values[0];
		rotationRates[1] = event.values[1];
		rotationRates[2] = event.values[2];
		
		SensorDataValues.setSensorValue(SensorDataValues.DataType.GYROSCOPE_X, rotationRates[0]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.GYROSCOPE_Y, rotationRates[1]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.GYROSCOPE_Z, rotationRates[2]);
		
//		CharmSensorMonitorActivity.updateSensorValues();
//		SettingsActivity.sendData(rotationRates[0]);
		DataArchiveManager.writeSensorData(timestamp, rotationRates, Sensor.TYPE_GYROSCOPE);				
		
	}
	public double [] getData() {
		// TODO Auto-generated method stub
		return rotationRates;
	}
}