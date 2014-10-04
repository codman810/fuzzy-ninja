package com.example.ninjatasks;

import java.util.ArrayList;
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
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	HashMap<String, List<String>> listDataChild2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);
		
		registerForContextMenu(expListView);
		
		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			
				return false;
				}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
					
					return false;
					}
				});
		
		
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
	    startActivity(intent);
	}
	//make list
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		listDataChild2 = new HashMap<String, List<String>>();
		// Adding child data
				listDataHeader.add("Top 250");
				listDataHeader.add("Now Showing");
				listDataHeader.add("Coming Soon..");

				// Adding child data
				List<String> top250 = new ArrayList<String>();
				top250.add("The Shawshank Redemption");
				top250.add("The Godfather");
				top250.add("The Godfather: Part II");
				top250.add("Pulp Fiction");
				top250.add("The Good, the Bad and the Ugly");
				top250.add("The Dark Knight");
				top250.add("12 Angry Men");

				List<String> nowShowing = new ArrayList<String>();
				nowShowing.add("The Conjuring");
				nowShowing.add("Despicable Me 2");
				nowShowing.add("Turbo");
				nowShowing.add("Grown Ups 2");
				nowShowing.add("Red 2");
				nowShowing.add("The Wolverine");

				List<String> comingSoon = new ArrayList<String>();
				comingSoon.add("2 Guns");
				comingSoon.add("The Smurfs 2");
				comingSoon.add("The Spectacular Now");
				comingSoon.add("The Canyons");
				comingSoon.add("Europa Report");

				listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
				listDataChild.put(listDataHeader.get(1), nowShowing);
				listDataChild.put(listDataHeader.get(2), comingSoon);
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
		      i.putExtra((String) listAdapter.getGroup(groupPosition), true);
		      Log.v("name",(String) listAdapter.getGroup(groupPosition));
		      startActivity(i);
		      return true;
		    case R.id.delete:
		    	 return true;
		    case R.id.add:
		    	 return true;
		    case R.id.complete:
		    	 return true;
		    default:
			      return super.onContextItemSelected(item);
			  }
		    	

	    } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
	        // do someting with child
	    }
		return false;

		
	}

}
