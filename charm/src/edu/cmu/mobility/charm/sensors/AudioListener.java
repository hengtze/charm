package edu.cmu.mobility.charm.sensors;

import edu.cmu.mobility.charm.CharmSensorMonitorActivity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioListener implements SensorListener {
	
	private static int RECORDER_SOURCE = MediaRecorder.AudioSource.MIC;
	private static int RECORDER_SAMPLERATE = 44100;
	private static int RECORDER_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
	private static int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	
	private int audioBufferSize = 0;
	
	private AudioRecord audioRecorder = null;	
	private Thread threadRecording = null;
	private static boolean isRecording = false;
	
	public AudioListener () {
		audioBufferSize = AudioRecord.getMinBufferSize(
        		RECORDER_SAMPLERATE,
        		RECORDER_CHANNEL_CONFIG,
        		RECORDER_AUDIO_ENCODING);

	    audioBufferSize = Math.max(audioBufferSize, RECORDER_SAMPLERATE);	    		
	}
	
	@Override
	public boolean isAvailableOnDevice() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startListening() {
		audioRecorder = new AudioRecord(
	    		RECORDER_SOURCE,
				RECORDER_SAMPLERATE,
				RECORDER_CHANNEL_CONFIG,
				RECORDER_AUDIO_ENCODING,
				audioBufferSize);
		audioRecorder.startRecording();
		isRecording = true;
		
		threadRecording = new Thread(new Runnable()
	    {
	        @Override
	        public void run()
	        {
	            processAudioStream();
	        }
	    }, "Audio Recording Thread");
	    threadRecording.start();
	}

	

	@Override
	public void stopListening() {
		audioRecorder.stop();
		audioRecorder.release();
		audioRecorder = null;
		isRecording = false;
		if(threadRecording != null){
			Thread t = threadRecording;
			threadRecording = null;
			t.interrupt();
		}
	}
	
	
	public void onSensorChanged(short[] data) {
		SensorDataValues.setSensorValue(SensorDataValues.DataType.AUDIO_RAW, data[0]);
		//CharmSensorMonitorActivity.updateSensorValues();
	}
	
	private void processAudioStream() {
		int numSamplesRead = 0;
		short data16bit[] = new short[audioBufferSize];
		while (isRecording) {
			numSamplesRead = audioRecorder.read(data16bit, 0, audioBufferSize);
			if (numSamplesRead > 0) {
				onSensorChanged(data16bit);
			}
			
		}
	}
}
