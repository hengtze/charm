package edu.cmu.mobility.charm.data;

public class ClassValues {
	private static ClassValues singleInstance = null;
	//private static String [] activityTypeStrings;
	private static String [] classNameStrings;
	
	private static ClassName currentClassName = ClassName.NONE;
	
//	public enum ActivityType { 
//		GENERAL
//	};
	public enum ClassName {
		NONE ("No Activity"),
		BENCH_DIPS ("Bench Dips"),
		SQUAT_UPRIGHT_ROW ("Squat & Upright Row"),
		DB_SIDE_RAISE ("Dumbbell Side Raises"),
		DB_CURL ("Dumbbell Curl"),
		TREADMILL_RUN ("Treadmill Running"),
		TREADMILL_WALK ("Treadmill Walking"),
//		HOME_WORKING,
//		HOME_EATING,
//		HOME_COOKING,
//		HOME_ENTERTAINMENT,
//		OFFICE_WORKING,
//		OFFICE_MEETING,
//		OFFICE_CHATTING,
//		GYM_RUNNING,
//		CAR_DRIVING,
		;
		
		private final String printableName;
		ClassName(String s) { this.printableName = s; }
	    public String getPrintableName() { return printableName; }
	};
	
	public ClassValues() {
		setCurrentClassLabel(ClassName.NONE);
		classNameStrings = new String [ClassName.values().length];
		
		ClassName types[] = ClassName.values();
		for (ClassName type : types) {
			classNameStrings[type.ordinal()] = type.getPrintableName();
		}
	}
	
	public static ClassValues getInstance() {
		if (singleInstance == null) {
			singleInstance = new ClassValues();
		}
		return singleInstance;
	}
	
//	public static void setCurrentClassLabel(String label) {		
//		currentClassName = label;		
//	}
	public void setCurrentClassLabel(int id) {		
		currentClassName = ClassName.values()[id];		
	}
	public static void setCurrentClassLabel(ClassName className) {		
		currentClassName = className;		
	}
	
	public String [] getClassNameStrings() {
		return classNameStrings;
	}
	public static String getCurrentClassLabel() {
		return currentClassName.toString();
	}
	public static int getCurrentClassId() {
		return currentClassName.ordinal();
	}
}
