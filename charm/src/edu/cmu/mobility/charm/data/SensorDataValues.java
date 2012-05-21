package edu.cmu.mobility.charm.data;

public class SensorDataValues {
	private static SensorDataValues singleInstance = null;
	private static String [] sensorDataTypeStrings;
	private static double [] sensorDataValues;
	
	public enum DataType {		
		PROXIMITY("Proximity"),
		ACCELERATION_X ("Acceleration (X)"),
		ACCELERATION_Y ("Acceleration (Y)"),
		ACCELERATION_Z ("Acceleration (Z)"),
		LIN_ACCELERATION_X ("Linear Acceleration (X)"),
		LIN_ACCELERATION_Y ("Linear Acceleration (Y)"),
		LIN_ACCELERATION_Z ("Linear Acceleration (Z)"),
		LIN_ACCELERATION_MAGNITUDE ("Linear Acceleration (Magnitude)"),
		GRAVITY_X ("Gravity (X)"),
		GRAVITY_Y ("Gravity (Y)"),
		GRAVITY_Z ("Gravity (Z)"),
		ROTATION_VECTOR_X ("Rotation Vector (X)"),
		ROTATION_VECTOR_Y ("Rotation Vector (Y)"),
		ROTATION_VECTOR_Z ("Rotation Vector (Z)"),
		ORIENTATION_AZIMUTH("Orientation (Azimuth)"), 
		ORIENTATION_PITCH("Orientation (Pitch)"), 
		ORIENTATION_ROLL("Orientation (Roll)"),
		GYROSCOPE_X("Gyroscope (X)"),
		GYROSCOPE_Y("Gyroscope (Y)"),
		GYROSCOPE_Z("Gyroscope (Z)"),
		LIGHT("Ambient Light"),
		AUDIO_RAW ("Audio (Amplitude)"),
		AUDIO_MFCC ("Audio MFCC"),		
		LOCATION_LAT("Location (Latitude)"),
		LOCATION_LON("Location (Longitude)"),
		TIMESTAMP ("Timestamp"),
		;
		
		private final String printableName;
		DataType(String s) { this.printableName = s; }
	    public String getPrintableName() { return printableName; }
	};
	
	public SensorDataValues() {
		sensorDataValues = new double [DataType.values().length];
		sensorDataTypeStrings = new String [DataType.values().length];
		
		DataType types[] = DataType.values();
		for (DataType type : types) {
			sensorDataTypeStrings[type.ordinal()] = type.getPrintableName();
			sensorDataValues[type.ordinal()] = 0.0;
		}
	}
	
	public static SensorDataValues getInstance() {
		if (singleInstance == null) {
			singleInstance = new SensorDataValues();
		}
		return singleInstance;
	}
	
	public static void setSensorValue(DataType type, double value) {		
		sensorDataValues[type.ordinal()] = value;		
	}
	
	public String [] getSensorDataTypeStrings() {
		return sensorDataTypeStrings;
	}
	public double [] getSensorDataValues() {
		return sensorDataValues;
	}
}
