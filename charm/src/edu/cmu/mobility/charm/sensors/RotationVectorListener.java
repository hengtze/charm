package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class RotationVectorListener implements SensorListener, SensorEventListener {
	
	private SensorManager sensorManager;
	
	// Sensors
	private Sensor rotationVectorSensor;
	
	// Sensor Values
	private double[] rotationVector;
	
	public RotationVectorListener(Context c) {
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		rotationVector = new double [3];
	}
	@Override
	public boolean isAvailableOnDevice() {
		return (rotationVectorSensor != null);
	}
	
	@Override
	public void startListening() {
		sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
		rotationVector[0] = event.values[0];
		rotationVector[1] = event.values[1];
		rotationVector[2] = event.values[2];
		
		SensorDataValues.setSensorValue(SensorDataValues.DataType.ROTATION_VECTOR_X, rotationVector[0]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.ROTATION_VECTOR_Y, rotationVector[1]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.ROTATION_VECTOR_Z, rotationVector[2]);
		
		CharmSensorMonitorActivity.updateSensorValues();
		
	}
	public double [] getData() {
		// TODO Auto-generated method stub
		return rotationVector;
	}
}