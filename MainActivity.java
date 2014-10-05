package com.example.test;

import java.util.UUID;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;
import com.getpebble.android.kit.util.PebbleDictionary;


public class MainActivity extends Activity {

	private PebbleDataReceiver mReceiver;
	private TextView buttonView;

	private static final int
    KEY_BUTTON_EVENT = 0,
    BUTTON_EVENT_UP = 1,
    BUTTON_EVENT_DOWN = 2,
    BUTTON_EVENT_SELECT = 3,
    KEY_VIBRATION = 4,
    BUTTON_EVENT_SELECT_HOLD = 5;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        buttonView = new TextView(this);
        buttonView.setText("No button yet!");
        
        setContentView(buttonView);
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	
    	mReceiver = new PebbleDataReceiver(UUID.fromString("263fed08-b5a9-4bba-bfeb-6353b98b610d")) {
    		 
            @Override
            public void receiveData(Context context, int transactionId, PebbleDictionary data) {
            	//ACK the message
                PebbleKit.sendAckToPebble(context, transactionId);
             
                //Check the key exists
                if(data.getUnsignedInteger(KEY_BUTTON_EVENT) != null) {
                    int button = data.getUnsignedInteger(KEY_BUTTON_EVENT).intValue();
             
                    switch(button) {
                    case BUTTON_EVENT_UP:
                        //The UP button was pressed
                        buttonView.setText("UP button pressed!");
                        break;
                    case BUTTON_EVENT_DOWN:
                        //The DOWN button was pressed
                        buttonView.setText("DOWN button pressed!");
                        break;
                    case BUTTON_EVENT_SELECT:
                        //The SELECT button was pressed
                        buttonView.setText("SELECT button pressed!");
                        break;
                    case BUTTON_EVENT_SELECT_HOLD:
                        //The SELECT button was pressed
                        buttonView.setText("SELECT button held!");
                        break;
                    }
                  //Make the watch vibrate
                    PebbleDictionary dict = new PebbleDictionary();
                    dict.addInt32(KEY_VIBRATION, 4);
                    PebbleKit.sendDataToPebble(context, UUID.fromString("263fed08-b5a9-4bba-bfeb-6353b98b610d"), dict);
                }
            }
     
        };
     
        PebbleKit.registerReceivedDataHandler(this, mReceiver);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
         
        unregisterReceiver(mReceiver);
    }
    
}
