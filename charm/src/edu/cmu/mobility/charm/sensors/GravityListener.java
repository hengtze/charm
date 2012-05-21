package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import edu.cmu.mobility.charm.data.DataArchiveManager;
import edu.cmu.mobility.charm.data.SensorDataValues;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GravityListener implements SensorListener, SensorEventListener {
	
	private SensorManager sensorManager;
	
	// Sensors
	private Sensor gravitySensor;
	
	// Sensor Values
	private double[] gravity;
	
	public GravityListener(Context c) {
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		gravity = new double [3];
	}
	@Override
	public boolean isAvailableOnDevice() {
		return (gravitySensor != null);
	}
	
	@Override
	public void startListening() {
		sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);
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
		long timestamp = System.currentTimeMillis();
		gravity[0] = event.values[0];
		gravity[1] = event.values[1];
		gravity[2] = event.values[2];
		
		SensorDataValues.setSensorValue(SensorDataValues.DataType.GRAVITY_X, gravity[0]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.GRAVITY_Y, gravity[1]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.GRAVITY_Z, gravity[2]);
		
//		CharmSensorMonitorActivity.updateSensorValues();
		DataArchiveManager.writeSensorData(timestamp, gravity, Sensor.TYPE_GRAVITY);
	}
	public double [] getData() {
		// TODO Auto-generated method stub
		return gravity;
	}
}