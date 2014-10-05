package com.fuzzyninja.tasker;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Task {
	//Name
	private String Name = new String();
	private String Parent = new String();
	//Time Due
	private long due;
	//Subtasks
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private int id = 0, numTasks = 0;
	//% done
	private double numDone = 0;
	private boolean completed;
	//notes
	private ArrayList<String> notes = new ArrayList<String>();
	//Defcon
	private int defcon;
	//Location
	//Alarm
	
	//Constructors
	public Task(){
		this.Name = "You should put something here...";
		this.due = 0;
		this.defcon = 2;
		this.Parent = null;
	}
	
	public Task(String name){
		this.Name = name;
		this.Parent = null;
	}
	
	
	//Setters
	public void setName(String name){
		this.Name = name;
	}
	
	public void setDefcon(int defcon){
		this.defcon = defcon;
	}
	
	public void setDue(long due){
		this.due = due;
	}
	
	public void addNote(String note){
		this.notes.add(note);
	}
	
	public void addSubTask(Task sub){
		if(id < 1){
			numTasks++;
			sub.id = this.id+1;
			sub.Parent = this.Name;
			this.tasks.add(sub);
		}
	}
	
	public void toggleComplete(){
		this.completed = !this.completed;
	}
	
	public void add(){
		//(Name, Due, Id, Parent, Subtasks, Subdone, Completed, Notes, Defcon)
		SQLiteDatabase db = MainActivity.getItemDatabase().getWritableDatabase();
		ContentValues cv = new ContentValues();
		//this.due, this.Parent, this.numTasks, this.numDone,
		//this.completed, this.notes, this.defcon
		cv.put("Name", this.Name);
		cv.put("Due", this.due);
		cv.put("Id", this.id);
		cv.put("Parent", this.Parent);
		cv.put("Subtasks", this.numTasks);
		cv.put("Subdone", this.numDone);
		cv.put("Completed", this.completed);
		cv.put("Notes", this.notes.toString());
		cv.put("Defcon", this.defcon);
		db.insert("Tasker",null,cv);
	}
	
	public void delete(){
		SQLiteDatabase db = MainActivity.getItemDatabase().getWritableDatabase();
		db.delete("Tasker", "Name = '" + this.Name + "';", null);
	}
	
	public void update(){
		SQLiteDatabase db = MainActivity.getItemDatabase().getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("Name", this.Name);
		cv.put("Due", this.due);
		cv.put("Id", this.id);
		cv.put("Parent", this.Parent);
		cv.put("Subtasks", this.numTasks);
		cv.put("Subdone", this.numDone);
		cv.put("Completed", this.completed);
		cv.put("Notes", this.notes.toString());
		cv.put("Defcon", this.defcon);
		db.update("Tasker", cv, "Name = '" + this.Name + "';", null);
	}
	
	public static void sendAlertToPebble(final Context context) {
	    final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

	    final Map<String, String> data = new HashMap<String, String>();
	    data.put("title", "Test Message");
	    data.put("body", "Whoever said nothing was impossible never tried to slam a revolving door.");
	    final JSONObject jsonData = new JSONObject(data);
	    final String notificationData = new JSONArray().put(jsonData).toString();

	    i.putExtra("messageType", "PEBBLE_ALERT");
	    i.putExtra("sender", "MyAndroidApp");
	    i.putExtra("notificationData", notificationData);
	    Log.d("Pebble", "About to send a modal alert to Pebble: " + notificationData);
	    context.sendBroadcast(i);
	}
	
	//Getters
	public Date timeLeft(){
		long now = System.currentTimeMillis();
		Date d;
		if(now > due){
			d = new Date(now-due);
		}
		else{
			d = null;
		}
		return d;
	}
	
	public boolean completed(){
		return this.completed;
	}
	
	public int getDefcon(){
		return this.defcon;
	}
	
	public String getName(){
		return this.Name;
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks;
	}
	
	public float percentComplete(){
		return this.numTasks == 0?0:(float)(this.numDone/this.numTasks);
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getParentName(){
		return this.Parent;
	}
	
}