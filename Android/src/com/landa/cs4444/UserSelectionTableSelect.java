package com.landa.cs4444;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.landa.customer.Customer_Activity_Single_Pane;
import com.landa.helpers.AsyncHelper;
import com.landa.waitStaff.GlobalVars;
import com.landa.waitStaff.Table;
import com.landa.waitStaff.Table_List;
import com.landa.waitStaff.WaitInfo;

public class UserSelectionTableSelect extends ListActivity 
/*
														 * implements
														 * AdapterView
														 * .OnItemSelectedListener
														 */{
	// TODO Create customer spinner, to make it all pretty, functional now
	// though.
	
	
	private ArrayList<Table> mTables;
	private static final String TAG = "Chatlist";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
	
		mTables = Table_List.get(this).getTables();
		ArrayAdapter<Table> adapter = new ArrayAdapter<Table>(this,
				android.R.layout.simple_list_item_1, mTables);
		setListAdapter(adapter);
		
	}
	
	
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
		
    	if (AsyncHelper.getHold().equals("Wait"))
    	{
        	Intent myIntent = new Intent(UserSelectionTableSelect.this,
    				WaitInfo.class);
        	Long tableNo = id+1;
        	
        	
        	
    		myIntent.putExtra("tableNo", tableNo.toString() );
    		Log.i(TAG, tableNo.toString());
    		UserSelectionTableSelect.this.startActivity(myIntent);	
    	}
    	else{
    		
    	
    	Intent myIntent = new Intent(UserSelectionTableSelect.this,
				Customer_Activity_Single_Pane.class);
		
    	Long tableNo = id+1;
		myIntent.putExtra("tableNo", tableNo.toString() );
		Log.i(TAG, tableNo.toString());
		UserSelectionTableSelect.this.startActivity(myIntent);
    	}
    	
    }


}
