package edu.cmu.mobility.charm;

import android.content.Context;
import edu.cmu.mobility.charm.sensors.AccelerometerListener;
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
	
	private static AccelerometerListener accelerometerListener;
	private static GravityListener gravityListener;
	private static RotationVectorListener rotationVectorListener;
	private static OrientationListener compassListener;
	private static GyroscopeListener gyroscopeListener;
	private static ProximitySensorListener proximitySensorListener;
	private static LightSensorListener lightSensorListener;
	private static GPSListener gpsListener;
	private static AudioListener audioListener;
	
	public SensorController(Context c) {
		accelerometerListener = new AccelerometerListener(c);
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
//		proximitySensorListener.startListening();
//		lightSensorListener.startListening();
//		rotationVectorListener.startListening();
//		compassListener.startListening();
		accelerometerListener.startListening();
		gravityListener.startListening();
		gyroscopeListener.startListening();
//		gpsListener.startListening();
//		audioListener.startListening();
	}
	public void stopSensing() {
//		proximitySensorListener.stopListening();
//		lightSensorListener.stopListening();
//		rotationVectorListener.stopListening();
//		compassListener.stopListening();
		accelerometerListener.stopListening();
		gravityListener.stopListening();
		gyroscopeListener.stopListening();
//		gpsListener.stopListening();
//		audioListener.stopListening();
	}

	public void onDestroy() {
		audioListener.onDestroy();
	}
}
