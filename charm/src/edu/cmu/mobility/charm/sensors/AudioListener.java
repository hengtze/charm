package edu.cmu.mobility.charm.sensors;

import java.util.Arrays;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import edu.cmu.mobility.charm.data.SensorDataValues;
import edu.cmu.mobility.charm.utils.FFT;
import edu.cmu.mobility.charm.utils.MFCC;
import edu.cmu.mobility.charm.utils.Window;

public class AudioListener implements SensorListener {
	private static AudioListener singleInstance = null;
	private AudioRecord audioRecorder = null;	
	private Thread threadRecording = null;
	private static boolean isRecording = false;	
	
	// Raw Audio WAV data
	short data16bit[] = null;
	// Output MFCC
	double featureCepstrum[] = null;
	
	private int bufferSize = 0;
	private int bufferSamples = 0;
	private static int[] freqBandIdx = null;
	
    private FFT featureFFT = null;
    private MFCC featureMFCC = null;
    private Window featureWin = null;
    
    private static int RECORDER_SOURCE = MediaRecorder.AudioSource.MIC;
	private static int RECORDER_SAMPLERATE = 44100; //44100
	private static int RECORDER_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
	private static int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    
    private static int FFT_SIZE = 65536; // 65536
	private static int MFCC_NUM_DIMENSION = 13;
	private static int MEL_BANDS = 20;
	private static int STREAM_FEATURES = 20;
	private static double[] FREQ_BANDEDGES = {50,250,500,1000,2000};
		
	
	public AudioListener () {
		bufferSize = AudioRecord.getMinBufferSize(
        		RECORDER_SAMPLERATE,
        		RECORDER_CHANNEL_CONFIG,
        		RECORDER_AUDIO_ENCODING);

	    bufferSize = Math.max(bufferSize, RECORDER_SAMPLERATE*2);	    
	    bufferSamples = bufferSize/2;
	    
	    // Output Audio Raw Data & Features
	    data16bit = new short[bufferSize];
	    featureCepstrum = new double[MFCC_NUM_DIMENSION];
	}
	
	public static AudioListener getInstance() {
		if (singleInstance == null) {
			singleInstance = new AudioListener();
		}
		return singleInstance;
	}
	
	@Override
	public boolean isAvailableOnDevice() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void startListening() {		
		if (audioRecorder == null) {					    
		    featureFFT = new FFT(FFT_SIZE);
		    featureWin = new Window(bufferSamples);
		    featureMFCC = new MFCC(FFT_SIZE, MFCC_NUM_DIMENSION, MEL_BANDS, RECORDER_SAMPLERATE);
		    
		    freqBandIdx = new int[FREQ_BANDEDGES.length];
		    for (int i = 0; i < FREQ_BANDEDGES.length; i ++)
		    {
		    	freqBandIdx[i] = Math.round((float)FREQ_BANDEDGES[i]*((float)FFT_SIZE/(float)RECORDER_SAMPLERATE));
		    	//writeLogTextLine("Frequency band edge " + i + ": " + Integer.toString(freqBandIdx[i]));
		    }		    
			audioRecorder = new AudioRecord(
		    		RECORDER_SOURCE,
					RECORDER_SAMPLERATE,
					RECORDER_CHANNEL_CONFIG,
					RECORDER_AUDIO_ENCODING,
					bufferSize);
		}
		
		if (audioRecorder != null /*&& audioRecorder.getState() == AudioRecord.STATE_INITIALIZED*/) {
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
	}	

	@Override
	public void stopListening() {
		if (audioRecorder != null) {
			audioRecorder.stop();
		}
		isRecording = false;
		if (threadRecording != null) {
			Thread t = threadRecording;
			threadRecording = null;
			t.interrupt();
		}
	}
	
	public void onDestroy() {
		isRecording = false;
		if (audioRecorder != null) {
			audioRecorder.stop();
			audioRecorder.release();
			audioRecorder = null;
		}		
	}
	
	
	public void onSensorChanged() {
		SensorDataValues.setSensorValue(SensorDataValues.DataType.AUDIO_RAW, data16bit[0]);
		SensorDataValues.setSensorValue(SensorDataValues.DataType.AUDIO_MFCC, featureCepstrum[12]);
		//CharmSensorMonitorActivity.updateSensorValues();
	}
	
	private void processAudioStream() {
		int numAudioSamplesRead = 0;		
		double fftBufferR[] = new double[FFT_SIZE];
    	double fftBufferI[] = new double[FFT_SIZE];    	    	
		
		while (isRecording && audioRecorder != null && audioRecorder.getRecordingState()==AudioRecord.RECORDSTATE_RECORDING) {
			numAudioSamplesRead = audioRecorder.read(data16bit, 0, bufferSamples);
			if (numAudioSamplesRead > 0) {								
				double accum = 0;
				// Frequency analysis
	    		Arrays.fill(fftBufferR, 0);
	    		Arrays.fill(fftBufferI, 0);
	    		
	    		// Convert audio buffer to doubles
	    		for (int i = 0; i < numAudioSamplesRead; i++)
	    		{
	    			fftBufferR[i] = data16bit[i];
	    		}

	    		// In-place windowing
	    		featureWin.applyWindow(fftBufferR);

	    		// In-place FFT
	    		featureFFT.fft(fftBufferR, fftBufferI);
	    		
//	    		// Get PSD across frequency band ranges
//	    		double[] psdAcrossFrequencyBands = new double[FREQ_BANDEDGES.length - 1];
//	    		for (int b = 0; b < (FREQ_BANDEDGES.length - 1); b ++)
//	    		{
//	    			int j = freqBandIdx[b];
//	    			int k = freqBandIdx[b+1];
//	    			accum = 0;
//	    			for (int h = j; h < k; h ++)
//	    			{
//	    				accum += fftBufferR[h]*fftBufferR[h] + fftBufferI[h]*fftBufferI[h];
//	    			}
//	    			psdAcrossFrequencyBands[b] = accum/((double)(k - j));
//	    		}
	    		// data.putDoubleArray(PSD_ACROSS_FREQUENCY_BANDS, psdAcrossFrequencyBands);

	    		// Get MFCCs
	    		featureCepstrum = featureMFCC.cepstrum(fftBufferR, fftBufferI);
	    		//data.putDoubleArray(MFCCS, featureCepstrum);
	    		onSensorChanged();
			}
			
		}
	}
}
