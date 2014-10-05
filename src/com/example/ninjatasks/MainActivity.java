package com.example.ninjatasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import android.os.Bundle;
import android.provider.BaseColumns;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.CursorAdapter;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;


public class MainActivity extends Activity {


	
	//Expandable List
	private ExpandableListAdapter listAdapter;
	private ExpandableListView expListView;
	private List<Task> listDataHeader;
	private HashMap<Task, List<Task>> listDataChild;

	
	private static Database itemDatabase = null;
	protected static int REQUEST_UPDATE = 1;	
	protected static int REQUEST_INSERT = 2;
	protected static int REQUEST_DELETE = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setItemDatabase(new Database(this));
		
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		
		// preparing list data
		prepareListData();
		
		
		registerForContextMenu(expListView);
		
		
	}
	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data){
		if(reqCode==REQUEST_UPDATE && data!=null){
			if(resCode==RESULT_OK){
				String[] results = data.getExtras().getStringArray("results");
				
				prepareListData();		
			}
		}
		else if(reqCode==REQUEST_INSERT && data!=null){
			if(resCode==RESULT_OK){
				String[] results = data.getExtras().getStringArray("results");
				long time = data.getExtras().getLong("date");
				List<Task> temp = new ArrayList<Task>();
				Task task = new Task(results[0]);
				task.setDefcon(Integer.parseInt(results[1]));
				if(results[3].equals("1")){
					task.setId(1);
					task.setParent(results[4]);
				}
				task.setDue(time);
				task.addNote(results[2]);
				task.add();
				if(results[3].equals("0")){
					listDataHeader.add(task);					
				}
				else{
						Task parent = Task.getTask(task.getParentName());
						parent.addSubTask(task);
						if(listDataChild.get(parent) == null){							
							temp.add(task);
							listDataChild.put(parent, temp);
						}
						else{
							temp=listDataChild.get(parent);
							temp.add(task);
							listDataChild.put(parent,temp);
						}
					}
				
				
				}
			prepareListData();	
			}
			
	}		
	//make list
		private void prepareListData() {
			Log.v("here","pepp");
			listDataHeader = new ArrayList<Task>();
			listDataChild = new HashMap<Task, List<Task>>();
			List<Task> parents =Task.getParentTasks();
			List<Task> children =Task.getChildTasks();
			for(Task t: parents){
				listDataHeader.add(t);
				Log.v("namesees",t.getName());
				List<Task> temp = new ArrayList<Task>();
					for(Task s: children){
						if(s.getParentName().equals(t.getName())){
							temp.add(s);
						}
					}
				listDataChild.put(t, temp);
			}
			listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
			expListView.setAdapter(listAdapter);
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void sendMessage(View view) 
	{
	    Intent intent = new Intent(MainActivity.this, AddTask.class);
	    intent.putExtra("TaskName","NA");
	    intent.putExtra("itemContents",0);
	    startActivityForResult(intent,REQUEST_INSERT);
	}

	private void exampleData(){
		
		// Adding child data
		Task task = new Task("Example");
		Task subtask = new Task("SubTask");
        task.setDefcon(0);
	    task.setId(1);
	    Calendar cal = Calendar.getInstance();
	    cal.set(2014, Calendar.OCTOBER, 20,9,30);
	    Date d = cal.getTime();
	    task.setDue(d.getTime());
	    task.addNote("Hello World!");
	    subtask.setDefcon(2);
	    subtask.setId(1);
	    Calendar cal2 = Calendar.getInstance();
	    cal2.set(2014, Calendar.OCTOBER, 18,10,15);
	    Date d2 = cal.getTime();
	    subtask.setDue(d2.getTime());
	    subtask.setParent(task.getName());
	    subtask.addNote("Hola!");
	    task.addSubTask(subtask);
	    	    	
		listDataHeader.add(task);
		// Adding child data
		List<Task> ex1 = new ArrayList<Task>();
		ex1.add(subtask);
		listDataChild.put(listDataHeader.get(0), ex1); // Header, Child data
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		  MenuInflater inflater = getMenuInflater();
		  ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

		    int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		    int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
		    int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

		    // Show context menu for groups
		    if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
		    	inflater.inflate(R.menu.context_menu, menu);
		    } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
		    	inflater.inflate(R.menu.context_menusub, menu);
		    }
		  
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item
	            .getMenuInfo();

	    int type = ExpandableListView.getPackedPositionType(info.packedPosition);
	    int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
	    int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

	    if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
	    	switch (item.getItemId()) {
		    case R.id.edit:
		      Intent i = new Intent(MainActivity.this,EditTask.class);
		      i.putExtra("TaskName", ((Task)listAdapter.getGroup(groupPosition)).getName());		     
		      startActivityForResult(i,REQUEST_UPDATE);
		      return true;
		    case R.id.delete:
		    	((Task)listAdapter.getGroup(groupPosition)).delete();
		    	prepareListData();
		    	//delete subs
		    	 return true;
		    case R.id.add:
		    	Intent intent = new Intent(MainActivity.this, AddTask.class);
		    	intent.putExtra("itemContents",1);
		    	intent.putExtra("TaskName", ((Task)listAdapter.getGroup(groupPosition)).getName());
			    startActivityForResult(intent,REQUEST_INSERT);
		    	 return true;
		    case R.id.complete:
		    	((Task)listAdapter.getGroup(groupPosition)).delete();
		    	prepareListData();
		    	//delete subs
		    	 return true;
		    default:
			      return super.onContextItemSelected(item);
			  }
		    	

	    } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {	    	
	    	switch (item.getItemId()) {
	    	
		    case R.id.editSub:
		      Intent i = new Intent(MainActivity.this,EditTask.class);
		      i.putExtra("TaskName", ((Task)listAdapter.getChild(groupPosition,childPosition)).getName());			   
		      startActivityForResult(i,REQUEST_UPDATE);
		      return true;
		    case R.id.deleteSub:
		    	((Task)listAdapter.getChild(groupPosition,childPosition)).delete();
		    	Log.v("apples",((Task)listAdapter.getChild(groupPosition,childPosition)).getName());
		    	prepareListData();
		    	 return true;
		    case R.id.completeSub:
		    	((Task)listAdapter.getChild(groupPosition,childPosition)).setCompleted(1);
		    	
		    	 return true;
		    default:
			      return super.onContextItemSelected(item);
			  }
	    }
		return false;
		
	}
	public static Database getItemDatabase() {
		return itemDatabase;
	}


	public void setItemDatabase(Database itemDatabase) {
		MainActivity.itemDatabase = itemDatabase;
	}
	

}
