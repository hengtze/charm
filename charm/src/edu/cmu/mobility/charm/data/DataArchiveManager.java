package edu.cmu.mobility.charm.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Environment;
import android.telephony.TelephonyManager;

public class DataArchiveManager {
	
	private static DataArchiveManager singleInstance = null;
	private static TelephonyManager telManager;
	private static final String FOLDER_NAME = "charm/data";
	private static File dataFile;
	private static FileWriter foutLinAcc;
	private static FileWriter foutGravity;
	private static FileWriter foutGyro;
	private static FileWriter foutAcc;
	private static FileWriter foutRotationVec;
	private static FileWriter foutAll;
	private static SensorManager sensorManager;
	
	private static long sessionTimestamp = 0;
	
	public DataArchiveManager (Context c) {
		telManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
	}
	
	public static DataArchiveManager getInstance (Context c) {
		if (singleInstance == null) {
			singleInstance = new DataArchiveManager(c);
		}
		return singleInstance;
	}
	
	public static void setSessionTimestamp() {
		sessionTimestamp = System.currentTimeMillis();
	}
	
	public void createOutputFile(int sensorType){
		String 	folderName = Environment.getExternalStorageDirectory().toString() 
				+ File.separator + FOLDER_NAME;
		String dataFilePath = folderName + File.separator 
				+ telManager.getDeviceId() + "_"
				+ sessionTimestamp + "_"
				+ sensorManager.getDefaultSensor(sensorType).getName().replaceAll("\\s", "")
				+ ".csv";
		File 	folder = new File(folderName);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		dataFile = new File(dataFilePath);
		try {
			if (sensorType == Sensor.TYPE_LINEAR_ACCELERATION) {
				foutLinAcc = new FileWriter(dataFile);
				String headerStr = "TIMESTAMP,LINEAR_ACC_X,LINEAR_ACC_Y,LINEAR_ACC_Z,CLASS_ID,CLASS_NAME" + "\n";
				foutLinAcc.append(headerStr);
				foutLinAcc.flush();
			}
			else if (sensorType == Sensor.TYPE_GYROSCOPE) {
				foutGyro = new FileWriter(dataFile);
				String headerStr = "TIMESTAMP,GYRO_X,GYRO_Y,GYRO_Z,CLASS_ID,CLASS_NAME" + "\n";
				foutGyro.append(headerStr);
				foutGyro.flush();
			}
			else if (sensorType == Sensor.TYPE_GRAVITY) {
				foutGravity = new FileWriter(dataFile);
				String headerStr = "TIMESTAMP,GRAVITY_X,GRAVITY_Y,GRAVITY_Z,CLASS_ID,CLASS_NAME" + "\n";
				foutGravity.append(headerStr);
				foutGravity.flush();
			}
			else if (sensorType == Sensor.TYPE_ACCELEROMETER) {
				foutAcc = new FileWriter(dataFile);
				String headerStr = "TIMESTAMP,ACC_X,ACC_Y,ACC_Z,CLASS_ID,CLASS_NAME" + "\n";
				foutAcc.append(headerStr);
				foutAcc.flush();
			}
			else { foutAll = new FileWriter(dataFile); }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeSensorData(long timestamp, double[] data, int sensorType) {
		FileWriter fout;
		if (sensorType == Sensor.TYPE_LINEAR_ACCELERATION) { fout = foutLinAcc; }
		else if (sensorType == Sensor.TYPE_GYROSCOPE) { fout = foutGyro; }
		else if (sensorType == Sensor.TYPE_GRAVITY) { fout = foutGravity; }
		else if (sensorType == Sensor.TYPE_ACCELEROMETER) { fout = foutAcc; }
		else { fout = foutAll; }
		try {
			// fout = new FileWriter(dataFile);
			String str = String.valueOf(timestamp) + ","
						+ String.valueOf(data[0]) + ","
						+ String.valueOf(data[1]) + ","
						+ String.valueOf(data[2]) + ","
						+ String.valueOf(ClassValues.getCurrentClassId()) + ","
						+ ClassValues.getCurrentClassLabel()
						+ "\n";
			fout.append(str);
			fout.flush();
	        //fout.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportDataToFile(){		    
//		try {
//			fout = new FileWriter(dataFile);
//			fout.append("test");
//	        fout.flush();
//	        fout.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
