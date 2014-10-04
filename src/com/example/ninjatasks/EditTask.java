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
	
public class EditTask extends Activity{

	
	private EditText editNameEdit;
	private DatePicker datePickEdit;
	private TimePicker timePickEdit;
	private EditText descriptionEdit;
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
			
			editNameEdit=(EditText)findViewById(R.id.editNameEdit);
			datePickEdit=(DatePicker)findViewById(R.id.datePicker1Edit);
			timePickEdit=(TimePicker)findViewById(R.id.timePicker1Edit);
			descriptionEdit=(EditText)findViewById(R.id.descriptionEdit);
			
			
			
		}
		public void sendMessage(View view) 
		{
		    Intent intent = new Intent(EditTask.this,MainActivity.class);
		    startActivity(intent);
		}
}