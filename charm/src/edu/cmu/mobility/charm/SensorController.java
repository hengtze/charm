package edu.cmu.mobility.charm;

import android.content.Context;
import edu.cmu.mobility.charm.sensors.AccelerometerListener;
import edu.cmu.mobility.charm.sensors.AudioListener;
import edu.cmu.mobility.charm.sensors.CompassListener;
import edu.cmu.mobility.charm.sensors.GPSListener;
import edu.cmu.mobility.charm.sensors.ProximitySensorListener;

public class SensorController {
	private static SensorController singleInstance = null;
	private static AccelerometerListener accelerometerListener;
	private static CompassListener compassListener;
	private static ProximitySensorListener proximitySensorListener;
	private static GPSListener gpsListener;
	private static AudioListener audioListener;
	
	public SensorController(Context c) {
		accelerometerListener = new AccelerometerListener(c);
        compassListener = new CompassListener(c);
        proximitySensorListener = new ProximitySensorListener(c);
        gpsListener = new GPSListener(c);
        audioListener = new AudioListener();
	}
	
	public static SensorController getInstance(Context c) {
		if (singleInstance == null) {
			singleInstance = new SensorController(c);
		}
		return singleInstance;
	}
	
	public void startSensing() {
		proximitySensorListener.startListening();
		compassListener.startListening();
		accelerometerListener.startListening();
		gpsListener.startListening();
		audioListener.startListening();
	}
	public void stopSensing() {
		proximitySensorListener.stopListening();
		compassListener.stopListening();
		accelerometerListener.stopListening();
		gpsListener.stopListening();
		audioListener.stopListening();
	}
}
