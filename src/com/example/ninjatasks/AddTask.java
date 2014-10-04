package com.example.ninjatasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddTask extends Activity{
	
private EditText editName;
private DatePicker datePick;
private TimePicker timePick;
private EditText description;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task);
		Spinner spinner = (Spinner) findViewById(R.id.defconDrop);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.defcon_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		editName=(EditText)findViewById(R.id.editName);
		datePick=(DatePicker)findViewById(R.id.datePicker1);
		timePick=(TimePicker)findViewById(R.id.timePicker1);
		description=(EditText)findViewById(R.id.description);
	}
	public void sendMessage(View view) 
	{
	    Intent intent = new Intent(AddTask.this,MainActivity.class);
	    startActivity(intent);
	}
}
