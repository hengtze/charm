package edu.cmu.mobility.charm;

import java.nio.ByteBuffer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity implements OnClickListener {
	private ToggleButton buttonBluetooth;
	private Button buttonSendMsg;
	
	private BluetoothAdapter mBluetoothAdapter = null;
	private static BluetoothConnManager btConnManager = null;
	private static StringBuffer mOutStringBuffer;
	
	private static final int REQUEST_ENABLE_BT = 3;
	
	private static final String BT_ADDRESS_MYMACBOOKPRO = "E0:F8:47:3E:07:6D";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);    
        initUserInterface();
    	
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
        	Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        	finish();
        	return;
        }
    }
	
	private void initUserInterface() {
		buttonBluetooth = (ToggleButton) findViewById(R.id.buttonBluetooth);
        buttonBluetooth.setOnClickListener(this);
        buttonSendMsg = (Button) findViewById(R.id.buttonSendMsg);
        buttonSendMsg.setOnClickListener(this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
        
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (btConnManager == null) {
            	// Initialize the BluetoothChatService to perform bluetooth connections
            	btConnManager = new BluetoothConnManager(this);

                // Initialize the buffer for outgoing messages
                mOutStringBuffer = new StringBuffer("");
            }
        }
	}
	public void onClick (View clickedButton) {
    	if(clickedButton.equals(buttonBluetooth)) {    					
    		if (buttonBluetooth.isChecked()) {
    			connectDevice(true);
    			//dataManager.exportDataToFile();
    		}
    		else {			
    			
    		}    		
		}
    	else if (clickedButton.equals(buttonSendMsg)) {
    		sendMessage("abc");
    	}
    }
	
	private void connectDevice(boolean secure) {
        // Get the device MAC address
        String address = BT_ADDRESS_MYMACBOOKPRO;
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        btConnManager.connect(device, secure);
    }
	
	public void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (btConnManager == null || btConnManager.getState() != BluetoothConnManager.STATE_CONNECTED) {
            Toast.makeText(this, "Bluetooth Not Connected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            btConnManager.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }
	
	public static void sendData(double data) {
        // Check that we're actually connected before trying anything
        if (btConnManager == null || btConnManager.getState() != BluetoothConnManager.STATE_CONNECTED) {
            //Toast.makeText(this, "Bluetooth Not Connected", Toast.LENGTH_SHORT).show();
            return;
        }
        if (btConnManager != null) {
            byte[] send = ByteBuffer.allocate(8).putDouble(data).array();
            btConnManager.write(send);
        }
    }
}
