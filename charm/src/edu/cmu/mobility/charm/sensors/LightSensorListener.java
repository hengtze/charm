package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightSensorListener implements SensorListener, SensorEventListener {
	
	private SensorManager sensorManager;
	private Sensor lightSensor;
	
	private double illuminance;
	
	public LightSensorListener(Context c) {
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		illuminance = -1;
	}
	
	@Override
	public boolean isAvailableOnDevice(){
		return (lightSensor != null);
	}
	
	@Override
	public void startListening() {
		sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
		illuminance = event.values[0];
		SensorDataValues.setSensorValue(SensorDataValues.DataType.LIGHT, illuminance);
		CharmSensorMonitorActivity.updateSensorValues();
	}
	
	public double getData() {
		// TODO Auto-generated method stub
		return illuminance;
	}

	
	
}
