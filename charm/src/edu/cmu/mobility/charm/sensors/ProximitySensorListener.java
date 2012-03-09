package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximitySensorListener implements SensorListener, SensorEventListener {
	
	private SensorManager sensorManager;
	private Sensor proximitySensor;
	
	private double distance;
	
	public ProximitySensorListener(Context c) {
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		distance = -1;
	}
	
	@Override
	public boolean isAvailableOnDevice(){
		return (proximitySensor != null);
	}
	
	@Override
	public void startListening() {
		sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
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
		distance = event.values[0];
		SensorDataValues.setSensorValue(SensorDataValues.DataType.PROXIMITY, distance);
		CharmSensorMonitorActivity.updateSensorValues();
	}
	
	public double getData() {
		// TODO Auto-generated method stub
		return distance;
	}

	
	
}
