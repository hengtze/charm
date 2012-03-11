package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import edu.cmu.mobility.charm.data.SensorDataValues;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSListener implements SensorListener, LocationListener {

	private static LocationManager locationManager; 
	private static double latitude, longitude, altitude;
	
	public GPSListener(Context c) {
		locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		latitude = longitude = altitude = -1;
	}
	
	@Override
	public boolean isAvailableOnDevice() {
		return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				&& locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	}

	@Override
	public void startListening() {
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 50, this);
		}
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 100, this);
		}
		if (!isAvailableOnDevice()){
			// TODO: Request user to start GPS or disable location
		}
	}

	@Override
	public void stopListening() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		
		SensorDataValues.setSensorValue(SensorDataValues.DataType.LOCATION_LAT, latitude);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.LOCATION_LON, longitude);
		CharmSensorMonitorActivity.updateSensorValues();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
}
