package edu.cmu.mobility.charm.data;


public class LabelValues {
	private static LabelValues singleInstance = null;
	private static String [] activityTypeStrings;
	private static String [] activityTypeValues;
	
	
	public enum ActivityType { 
		GENERAL
	};
	public enum ActivityValue {
		HOME_WORKING,
		HOME_EATING,
		HOME_COOKING,
		OFFICE_WORKING,
		OFFICE_MEETING,
		OFFICE_CHATTING,
		
	};
}
