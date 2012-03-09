package edu.cmu.mobility.charm.sensors;

public class SensorDataValues {
	private static SensorDataValues singleInstance = null;
	private static String [] sensorDataTypeStrings;
	private static double [] sensorDataValues;
	
	enum DataType {
		PROXIMITY("Proximity"),
		ACCELERATION_X ("Acceleration (X-Axis)"),
		ACCELERATION_Y ("Acceleration (Y-Axis)"),
		ACCELERATION_Z ("Acceleration (Z-Axis)"),
		ACCELERATION_MAGNITUDE ("Acceleration (Magnitude)"),
		ORIENTATION_AZIMUTH("Orientation (Azimuth)"), 
		ORIENTATION_PITCH("Orientation (Pitch)"), 
		ORIENTATION_ROLL("Orientation (Roll)");
		
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
