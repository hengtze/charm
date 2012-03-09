package edu.cmu.mobility.charm;

import edu.cmu.mobility.charm.sensors.AccelerometerListener;
import edu.cmu.mobility.charm.sensors.CompassListener;
import edu.cmu.mobility.charm.sensors.ProximitySensorListener;
import android.content.Context;

public class CharmSensorManager {
	private static CharmSensorManager singleInstance = null;
	private static AccelerometerListener accelerometerListener;
	private static CompassListener compassListener;
	private static ProximitySensorListener proximitySensorListener;
	
	public CharmSensorManager(Context c) {
		accelerometerListener = new AccelerometerListener(c);
        compassListener = new CompassListener(c);
        proximitySensorListener = new ProximitySensorListener(c);
	}
	
	public static CharmSensorManager getInstance(Context c) {
		if (singleInstance == null) {
			singleInstance = new CharmSensorManager(c);
		}
		return singleInstance;
	}
	
	public void startSensing() {
		proximitySensorListener.startListening();
		compassListener.startListening();
		accelerometerListener.startListening();
	}
	public void stopSensing() {
		proximitySensorListener.stopListening();
		compassListener.stopListening();
		accelerometerListener.stopListening();
	}
}
