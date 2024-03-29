package edu.cmu.mobility.charm.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import edu.cmu.mobility.charm.SettingsActivity;
import edu.cmu.mobility.charm.data.DataArchiveManager;
import edu.cmu.mobility.charm.data.SensorDataValues;

public class LinearAccelerationListener implements SensorListener, SensorEventListener {
	
	private SensorManager sensorManager;
	
	// Sensors
	private Sensor linearAccelerationSensor;
	
	// Sensor Values
	private double [] 	linearAcceleration;
	private double 		accelMagnitude = 0;
	private double [] 	accelMagnitudeHistory;
	private double		accelMagnitudeMean = 0;
	int					historyWriteIdx;
	int 				HISTORY_WINDOWSIZE = 10;
	
	public LinearAccelerationListener(Context c) {
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		
		linearAcceleration = new double [3];
		accelMagnitudeHistory = new double [HISTORY_WINDOWSIZE];
		historyWriteIdx = 0;
	}
	@Override
	public boolean isAvailableOnDevice() {
		// TODO Auto-generated method stub
		return (linearAccelerationSensor != null);
	}
	@Override
	public void startListening() {
		sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_FASTEST);
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
		linearAcceleration[0] = event.values[0];
		linearAcceleration[1] = event.values[1];
		linearAcceleration[2] = event.values[2];
		
//		accelMagnitude = Math.sqrt( acceleration[0]*acceleration[0]
//					+ acceleration[1]*acceleration[1] + acceleration[2]*acceleration[2] );		
		
		SensorDataValues.setSensorValue(SensorDataValues.DataType.TIMESTAMP, timestamp);		
		SensorDataValues.setSensorValue(SensorDataValues.DataType.LIN_ACCELERATION_X, linearAcceleration[0]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.LIN_ACCELERATION_Y, linearAcceleration[1]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.LIN_ACCELERATION_Z, linearAcceleration[2]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.LIN_ACCELERATION_MAGNITUDE, accelMagnitude);
		
//		CharmSensorMonitorActivity.updateSensorValues();
		DataArchiveManager.writeSensorData(timestamp, linearAcceleration, Sensor.TYPE_LINEAR_ACCELERATION);
		
		//SettingsActivity.sendData(acceleration[0]);
		
//		accelMagnitudeHistory[historyWriteIdx] = accelMagnitude;
//		historyWriteIdx++;
//		if (historyWriteIdx >= HISTORY_WINDOWSIZE) {
//			historyWriteIdx = 0;
//		}
		
//		RunTheWorldMainActivity.setOutputMotionClass(getMotionClass());
	}
	public double [] getData() {
		// TODO Auto-generated method stub
		return linearAcceleration;
	}
	public double getAccelMagnitudeMean() {
		// TODO Auto-generated method stub
		return accelMagnitudeMean;
	}
	
	public int getMotionClass() {
		double sum = 0;
		
		for (int i=0; i < HISTORY_WINDOWSIZE; i++){
			sum += accelMagnitudeHistory[i];
		}
		accelMagnitudeMean = sum / HISTORY_WINDOWSIZE;
				
		if ( accelMagnitudeMean >= 3.5 ) return 2;
		if ( accelMagnitudeMean >= 1.5 && accelMagnitudeMean < 3.5 ) return 1;
		else return 0;
	}

	
}
