package edu.cmu.mobility.charm;

import android.content.Context;
import android.hardware.Sensor;
import edu.cmu.mobility.charm.data.DataArchiveManager;
import edu.cmu.mobility.charm.sensors.AccelerometerListener;
import edu.cmu.mobility.charm.sensors.LinearAccelerationListener;
import edu.cmu.mobility.charm.sensors.AudioListener;
import edu.cmu.mobility.charm.sensors.GPSListener;
import edu.cmu.mobility.charm.sensors.GravityListener;
import edu.cmu.mobility.charm.sensors.GyroscopeListener;
import edu.cmu.mobility.charm.sensors.LightSensorListener;
import edu.cmu.mobility.charm.sensors.OrientationListener;
import edu.cmu.mobility.charm.sensors.ProximitySensorListener;
import edu.cmu.mobility.charm.sensors.RotationVectorListener;

public class SensorController {
	private static SensorController singleInstance = null;
	
	private static DataArchiveManager dataManager;
	
	private static AccelerometerListener accelerometerListener;
	private static LinearAccelerationListener linearAccelerationListener;
	private static GravityListener gravityListener;
	private static RotationVectorListener rotationVectorListener;
	private static OrientationListener compassListener;
	private static GyroscopeListener gyroscopeListener;
	private static ProximitySensorListener proximitySensorListener;
	private static LightSensorListener lightSensorListener;
	private static GPSListener gpsListener;
	private static AudioListener audioListener;
	
	public SensorController(Context c) {
        dataManager = DataArchiveManager.getInstance(c); 
		
		accelerometerListener = new AccelerometerListener(c);
		linearAccelerationListener = new LinearAccelerationListener(c);
		gravityListener = new GravityListener(c);
		rotationVectorListener = new RotationVectorListener(c);
        compassListener = new OrientationListener(c);
        gyroscopeListener = new GyroscopeListener(c);
        proximitySensorListener = new ProximitySensorListener(c);
        lightSensorListener = new LightSensorListener(c);
        gpsListener = new GPSListener(c);
        audioListener = AudioListener.getInstance();
	}
	
	public static SensorController getInstance(Context c) {
		if (singleInstance == null) {
			singleInstance = new SensorController(c);
		}
		return singleInstance;
	}
	
	public void startSensing() { 
		DataArchiveManager.setSessionTimestamp();		
		
		if (proximitySensorListener.isAvailableOnDevice()) proximitySensorListener.startListening();

		if (accelerometerListener.isAvailableOnDevice()) {
			dataManager.createOutputFile(Sensor.TYPE_ACCELEROMETER);
			accelerometerListener.startListening();
		}
		if (linearAccelerationListener.isAvailableOnDevice()) {
			dataManager.createOutputFile(Sensor.TYPE_LINEAR_ACCELERATION);
			linearAccelerationListener.startListening();
		}
		if (gravityListener.isAvailableOnDevice()) {
			dataManager.createOutputFile(Sensor.TYPE_GRAVITY);
			gravityListener.startListening();
		}
		if (gyroscopeListener.isAvailableOnDevice()) {
			dataManager.createOutputFile(Sensor.TYPE_GYROSCOPE);
			gyroscopeListener.startListening();
		}
//		lightSensorListener.startListening();
//		rotationVectorListener.startListening();
//		compassListener.startListening();
//		gpsListener.startListening();
//		audioListener.startListening();
	}
	public void stopSensing() {
		if (proximitySensorListener.isAvailableOnDevice()) proximitySensorListener.stopListening();
//		lightSensorListener.stopListening();
//		rotationVectorListener.stopListening();
//		compassListener.stopListening();
		if (accelerometerListener.isAvailableOnDevice()) accelerometerListener.stopListening();
		if (linearAccelerationListener.isAvailableOnDevice()) linearAccelerationListener.stopListening();
		if (gravityListener.isAvailableOnDevice()) gravityListener.stopListening();
		if (gyroscopeListener.isAvailableOnDevice()) gyroscopeListener.stopListening();
//		gpsListener.stopListening();
//		audioListener.stopListening();
	}

	public void onDestroy() {
//		audioListener.onDestroy();
	}
}
