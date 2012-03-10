package edu.cmu.mobility.charm.sensors;

public class SensorDataValues {
	private static SensorDataValues singleInstance = null;
	private static String [] sensorDataTypeStrings;
	private static double [] sensorDataValues;
	
	enum DataType {
		PROXIMITY("Proximity"),
		LIGHT("Ambient Light"),
		AUDIO_RAW ("Audio (Amplitude)"),
		ACCELERATION_X ("Acceleration (X-Axis)"),
		ACCELERATION_Y ("Acceleration (Y-Axis)"),
		ACCELERATION_Z ("Acceleration (Z-Axis)"),
		ACCELERATION_MAGNITUDE ("Acceleration (Magnitude)"),
		ROTATION_VECTOR_X ("Rotation Vector (X)"),
		ROTATION_VECTOR_Y ("Rotation Vector (Y)"),
		ROTATION_VECTOR_Z ("Rotation Vector (Z)"),
		ORIENTATION_AZIMUTH("Orientation (Azimuth)"), 
		ORIENTATION_PITCH("Orientation (Pitch)"), 
		ORIENTATION_ROLL("Orientation (Roll)"),
		GYROSCOPE_X("Gyroscope (X)"),
		GYROSCOPE_Y("Gyroscope (Y)"),
		GYROSCOPE_Z("Gyroscope (Z)"),
		LOCATION_LAT("Location (Latitude)"),
		LOCATION_LON("Location (Longitude)");
		
		private final String name;
		DataType(String s) { this.name = s; }
	    public String getPrintableName() { return name; }
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
