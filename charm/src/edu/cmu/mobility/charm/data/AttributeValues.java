package edu.cmu.mobility.charm.data;

public class AttributeValues {
	private static AttributeValues singleInstance = null;
	private static String [] attributeTypeStrings;
	private static double [] attributeValues;
	
	public enum DataType {
		IS_HOME_RELATED ("Home Related"),
		IS_WORK_RELATED ("Work Related"),
		
		IS_INDOOR ("Indoor"),
		IS_OUTDOOR ("Outdoor"),
		
		WITH_NONE ("Alone"),
		WITH_FAMILY ("With Family"),
		WITH_COLLEAGUES ("With Colleagues"),
		WITH_FRIENDS ("With Friends"),
		
		HAS_SOUND_NONE ("Silent"),
		HAS_SOUND_MUSIC ("Music Sound"),
		HAS_SOUND_VOICE ("People Talking Sound"),
		HAS_SOUND_CROWD ("Crowd Sound"),
		HAS_SOUND_WATER ("Water Sound"),
		HAS_SOUND_MACHINE_HIFREQ ("Low Frequency Machine Sound"),
		HAS_SOUND_MACHINE_LOWFREQ ("High Freqency Machine Sound"),
		
		MOTION_NONE ("No Motion"),
		MOTION_WALK ("Walking"),
		MOTION_RUN ("Running"),
		MOTION_VEHICLE ("Vehicle"),
		MOTION_ROTATE ("Rotation"),
		;		
		
		private final String printableName;
		DataType(String s) { this.printableName = s; }
	    public String getPrintableName() { return printableName; }
	};
	
	public AttributeValues() {
		attributeValues = new double [DataType.values().length];
		attributeTypeStrings = new String [DataType.values().length];
		
		DataType types[] = DataType.values();
		for (DataType type : types) {
			attributeTypeStrings[type.ordinal()] = type.getPrintableName();
			attributeValues[type.ordinal()] = 0.0;
		}
	}
	
	public static AttributeValues getInstance() {
		if (singleInstance == null) {
			singleInstance = new AttributeValues();
		}
		return singleInstance;
	}
	
	public static void setSensorValue(DataType type, double value) {		
		attributeValues[type.ordinal()] = value;		
	}
	
	public String [] getAttributeTypeStrings() {
		return attributeTypeStrings;
	}
	public double [] getAttributeValues() {
		return attributeValues;
	}
}
