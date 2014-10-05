package com.example.ninjatasks;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	private Button button;
	private String defcon;
	private Spinner spinner2;
	private String tName;
		@Override
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.edit);
		 spinner2 = (Spinner) findViewById(R.id.defconDropEdit);
			// Create an ArrayAdapter using the string array and a default spinner layout
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			        R.array.defcon_array, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinner2.setAdapter(adapter);
			 Intent updateIntent = this.getIntent();
			tName = updateIntent.getExtras().getString("TaskName");
			Task task =Task.getTask(tName);
			editNameEdit=(EditText)findViewById(R.id.editNameEdit);
			datePickEdit=(DatePicker)findViewById(R.id.datePicker1Edit);
			timePickEdit=(TimePicker)findViewById(R.id.timePicker1Edit);
			descriptionEdit=(EditText)findViewById(R.id.descriptionEdit);
			spinner2.setSelection(task.getDefcon()-1);
			editNameEdit.setText(task.getName());
			long time = task.getDue();
			String NormalDate = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:").format(new Date(time)); 
			Log.v("tester",NormalDate.substring(0,2));
			datePickEdit.updateDate(Integer.valueOf(NormalDate.substring(6,10)),Integer.valueOf(NormalDate.substring(3,5))-1,
					Integer.valueOf(NormalDate.substring(0,2)));
	
			timePickEdit.setCurrentHour(Integer.valueOf(NormalDate.substring(11,13)));
			timePickEdit.setCurrentMinute(Integer.valueOf(NormalDate.substring(14,16)));
			descriptionEdit.setText(task.getNote());
			
			button=(Button)findViewById(R.id.taskDoneEdit);
		    button.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	defcon=String.valueOf(spinner2.getSelectedItem());
		            	sendMessage(v);
		            }
		 
		        });
			
		}
		public void sendMessage(View view) 
		{

			Intent intent = new Intent();
		    Calendar cal = Calendar.getInstance();
		    cal.set(datePickEdit.getYear(),datePickEdit.getMonth(),datePickEdit.getDayOfMonth()-1,timePickEdit.getCurrentHour(),timePickEdit.getCurrentMinute());
			String[] insertResults = { editNameEdit.getText().toString(),defcon,
					descriptionEdit.getText().toString()};
			Date d = cal.getTime();
		   long value = d.getTime();
		   intent.putExtra("results",insertResults);
		   intent.putExtra("date", value);
			setResult(RESULT_OK, intent);	
			
			
			
			Task task = Task.getTask(tName);
			task.setName(editNameEdit.getText().toString());
			task.setDefcon(Integer.parseInt(insertResults[1]));
			task.setDue(value);
			task.addNote(descriptionEdit.getText().toString());

			task.update(tName);	
			

			
			this.finish();
		}
}