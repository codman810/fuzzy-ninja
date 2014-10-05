package com.example.ninjatasks;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
private Button button;
private Spinner spinner;
private String defcon;
private int Id;
private String pname;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task);
		 spinner = (Spinner) findViewById(R.id.defconDrop);
		
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
		button=(Button)findViewById(R.id.taskDone);
	    button.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	defcon=String.valueOf(spinner.getSelectedItem());
	            	sendMessage(v);
	            }
	 
	        });
	    Intent updateIntent = this.getIntent();
		 Id = updateIntent.getExtras().getInt("itemContents");
		 pname = updateIntent.getExtras().getString("TaskName");
	}
	public void sendMessage(View view) 
	{
	    Intent intent = new Intent();
	    Calendar cal = Calendar.getInstance();
	    cal.set(datePick.getYear(),datePick.getMonth(),datePick.getDayOfMonth(),timePick.getCurrentHour(),timePick.getCurrentMinute());
		String[] insertResults= {editName.getText().toString(),defcon,
				description.getText().toString(),Integer.toString(Id),null};
		if(Id == 1){
			insertResults[4] =  pname;
		}
		
		
		Date d = cal.getTime();
	   long value = d.getTime();
	   Log.v("set time", Long.toString(value));
	   intent.putExtra("results",insertResults);
	   intent.putExtra("date", value);
		setResult(RESULT_OK, intent);			
		this.finish();
	}
}
