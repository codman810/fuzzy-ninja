package com.example.ninjatasks;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class Task implements Comparable<Task>{
	//Name
	private String Name = new String();
	private String Parent;
	//Time Due
	private long due;
	//Subtasks
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private int id = 0, numTasks = 0;
	//% done
	private double numDone = 0;
	private int completed = 0;
	//notes
	private String notes;
	//Defcon
	private int defcon;
	//Location
	//Alarm
	
	//Constructors
	public Task(){
		this.Name = "You should put something here...";
		this.due = 0;
		this.defcon = 2;
		this.id = 0;
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
	public void setId(int id){
		this.id = id;
	}
	public void setParent(String parent){
		this.Parent= parent;
	}
	public void addNote(String note){
		this.notes=note;
	}
	
	public void addSubTask(Task sub){
		if(id < 1){
			numTasks++;
			sub.id = this.id+1;
			sub.Parent = this.Name;
			this.tasks.add(sub);
		}
	}
	
	public void setCompleted(int x){
		this.completed = x;
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
		Cursor items = db.query(Database.TABLE_NAME, Database.COLUMNS, null,null,null,null,"Id ASC");
		db.close();
	}
	
	public void delete(){
		SQLiteDatabase db = MainActivity.getItemDatabase().getWritableDatabase();
		db.delete("Tasker", "Name = '" + this.Name + "';", null);
		Cursor items = db.query(Database.TABLE_NAME, Database.COLUMNS, null,null,null,null,"Id ASC");
		db.close();
	}
	
	public void update(String OldName){
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
		db.update("Tasker", cv, "Name = '" + OldName + "';", null);
		Cursor items = db.query(Database.TABLE_NAME, Database.COLUMNS, null,null,null,null,"Id ASC");
		db.close();
	}
	
	//Getters
	public String timeLeft(){
		long now = new Date().getTime();

		String s = null;
		if(now < due){
			long timeLeft = due-now;
			if(timeLeft<(2*86400000)){
				s= timeLeft/86400000+"d:";
				timeLeft %=86400000;
				s = timeLeft/3600000+"h:";
				timeLeft %=3600000;
				s+=timeLeft/60000+"m";
			}
			else{
				s= (timeLeft/86400000+1)+"d";
			}
		}
		else{
			s = "Invalid Due date";
		}
		return s;
	}
	public double completion(){
		double done = 0;
		double total = 0;
		if(this.getId()==0){
			List<Task> childs = Task.getChildTasks();
			for(Task t: childs){
				if(this.getName().equals(t.getParentName())){
					total++;
					if(t.completed() == 1)
						done++;
				}
			}
			if(done == 0|| total == 0)
				return 0;
			else
				return (done*100)/total;
		}
		else{
			return this.completed();
		}
	}
	public int completed(){
		return this.completed;
	}
	
	public int getDefcon(){
		return this.defcon;
	}
	public long getDue(){
		return this.due;
	}
	public String getNote(){
		return this.notes;
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
	public static Task getTask(String name){
		 
	    // 1. get reference to readable DB
	    SQLiteDatabase db = MainActivity.getItemDatabase().getWritableDatabase();
	 
	    // 2. build query
	    Cursor cursor = 
	            db.query(Database.TABLE_NAME, // a. table
	            Database.COLUMNS, // b. column names
	            " Name = ?", // c. selections 
	            new String[] { String.valueOf(name) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            "Id ASC", // g. order by
	            null); // h. limit
	 
	    
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	   Log.v("getTask-name",cursor.getString(0));
	    Task task = new Task(cursor.getString(0));
	    task.setDefcon(Integer.parseInt(cursor.getString(8)));
	    task.setId(Integer.parseInt(cursor.getString(2)));
		task.setDue(Long.parseLong(cursor.getString(1)));
		task.addNote(cursor.getString(7));
		task.setParent(cursor.getString(3));
		task.setCompleted(Integer.valueOf(cursor.getString(6)));

	 
	    // 5. return task
	    return task;
	}
	public static List<Task> getParentTasks() {
		List<Task> taskParent = new LinkedList<Task>();
		String query = "SELECT  * FROM " + Database.TABLE_NAME;
		 
	       // 2. get reference to writable DB
	       SQLiteDatabase db = MainActivity.getItemDatabase().getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       // 3. go over each row, build book and add it to list
	       Task task = null;
	       if (cursor.moveToFirst()) {
	           do {
	               task = new Task(cursor.getString(1));
	               task.setDefcon(Integer.parseInt(cursor.getString(9)));
	       	    	task.setId(Integer.parseInt(cursor.getString(3)));
	       	    	task.setDue(Long.parseLong(cursor.getString(2)));
	       	    	task.addNote(cursor.getString(8));
	       	    	task.setCompleted(cursor.getInt(7));
	       	    	if(task.getId() == 0){	       	    	
	       	    		taskParent.add(task);
	       	    	}
	              
	               
	           } while (cursor.moveToNext());
	       }
	 
	       Log.v("getAllTasks()", taskParent.toString());
	 
	       // return t
	       return taskParent;
	   }
	
	public static List<Task> getChildTasks() {
	       
	       List<Task> taskChild = new LinkedList<Task>();
	       // 1. build the query
	       String query = "SELECT  * FROM " + Database.TABLE_NAME;
	 
	       // 2. get reference to writable DB
	       SQLiteDatabase db = MainActivity.getItemDatabase().getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       // 3. go over each row, build book and add it to list
	       Task task = null;
	       if (cursor.moveToFirst()) {
	           do {
	               task = new Task(cursor.getString(1));
	               task.setDefcon(Integer.parseInt(cursor.getString(9)));
	       	    	task.setId(Integer.parseInt(cursor.getString(3)));
	       	    	task.setDue(Long.parseLong(cursor.getString(2)));
	       	    	task.addNote(cursor.getString(8));
	       	    	task.setCompleted(cursor.getInt(7));
	       	    	if(task.getId() == 1){
	       	    		task.setParent(cursor.getString(4));
	       	    		List<Task> taskParent = Task.getParentTasks();
	       	    		for(Task t: taskParent ){
	       	    			Log.v("parent",task.getParentName());
	       	    			if(task.getParentName().equals(t.getName())){
	       	    				t.addSubTask(task);
	       	    			}			
	       	    		}
	       	    		taskChild.add(task);
	       	    	}
	       	    	
	              
	               
	           } while (cursor.moveToNext());
	       }
	 
	      
	 
	       // return tasks
	       return taskChild;
	   }

	@Override
    public int compareTo(Task t) {
        return Comparators.DUE.compare(this, t);
    }


    public static class Comparators {

        public static Comparator<Task> NAME = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        public static Comparator<Task> DUE = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return (int)((int)(o1.getDue()*1000) - (int)(o2.getDue()*1000));
            }
        };
        
    }

}