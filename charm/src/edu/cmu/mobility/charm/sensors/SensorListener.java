package edu.cmu.mobility.charm.sensors;

public interface SensorListener {
	
	public boolean isAvailableOnDevice();
	public void startListening();
	public void stopListening();
}
