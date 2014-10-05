package com.example.ninjatasks;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<Task> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<Task, List<Task>> _listDataChild;
	
	private ImageView image;
	
	
	public ExpandableListAdapter(Context context, List<Task> listDataHeader,
			HashMap<Task, List<Task>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	
	}
	
	
//REGULAR CHILD
	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = ((Task)getChild(groupPosition, childPosition)).getName();
		final String childDue = ((Task)getChild(groupPosition, childPosition)).timeLeft();
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);
		TextView timeDue = (TextView) convertView
				.findViewById(R.id.dueListItem);

		txtListChild.setText(childText);
		timeDue.setText("Due in:"+childDue);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (this._listDataHeader == null) {
	        Log.e("Debug", "mListDataHeader is null.");
	        return 0;
	    } else if (groupPosition < 0 || groupPosition >= this._listDataHeader.size()) {
	        Log.e("Debug", "position invalid: " + groupPosition);
	        return 0;
	    } else if (this._listDataHeader.get(groupPosition) == null) {
	        Log.e("Debug", "Value of mListDataHeader at position is null: " + groupPosition);
	        return 0;
	    } else if (this._listDataChild == null) {
	        Log.e("Debug", "mListDataChild is null.");
	        return 0;
	    } else if (!this._listDataChild.containsKey(this._listDataHeader.get(groupPosition))) {
	        Log.e("Debug", "No key: " + this._listDataHeader.get(groupPosition));
	        return 0;
	    } else if (this._listDataChild.get(this._listDataHeader.get(groupPosition)) == null) {
	        Log.e("Debug", "Value at key is null: " + this._listDataHeader.get(groupPosition));
	        return 0;
	    } else {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	    }
		//return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {		
		return this._listDataHeader.size();
		
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = ((Task)getGroup(groupPosition)).getName();
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}
		image=(ImageView)convertView.findViewById(R.id.expandableIcon);
		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		TextView headDue = (TextView) convertView
				.findViewById(R.id.dueListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		headDue.setText("Due in:"+((Task)getGroup(groupPosition)).timeLeft());
		lblListHeader.setText(headerTitle);
		if(getChildrenCount(groupPosition)!=0){
			int imageResourceId = isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float;
			image.setImageResource(imageResourceId);
		 
			image.setVisibility(View.VISIBLE);
		} else {
			image.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
