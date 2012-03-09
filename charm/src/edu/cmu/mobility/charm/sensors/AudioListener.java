package edu.cmu.mobility.charm.sensors;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioListener implements SensorListener {
	
	private static int RECORDER_SOURCE = MediaRecorder.AudioSource.MIC;
	private static int RECORDER_SAMPLERATE = 44100;
	private static int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	
	private int bufferSize = 0;
	
	private AudioRecord audioRecorder = null;	
	
	
	public AudioListener () {
		bufferSize = AudioRecord.getMinBufferSize(
        		RECORDER_SAMPLERATE,
        		RECORDER_CHANNELS,
        		RECORDER_AUDIO_ENCODING);

	    bufferSize = Math.max(bufferSize, RECORDER_SAMPLERATE*2);
	    
		audioRecorder = new AudioRecord(
	    		RECORDER_SOURCE,
				RECORDER_SAMPLERATE,
				RECORDER_CHANNELS,
				RECORDER_AUDIO_ENCODING,
				bufferSize);
	}
	
	@Override
	public boolean isAvailableOnDevice() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startListening() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopListening() {
		// TODO Auto-generated method stub
		
	}
	
}
