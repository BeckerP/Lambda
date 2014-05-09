package com.landa.waitStaff;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.landa.R;


public class TableListFrag extends ListFragment {
	private ArrayList<Table> mTables;
	private static final String TAG = "TableListFrag";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.table); //Activity hosts this fragment, which contains a List View of Tables
		mTables = Table_List.get(getActivity()).getTables(); //get our list of 21 tables we created.
		
		ArrayAdapter<Table> adapter =
				new ArrayAdapter<Table>(getActivity(),android.R.layout.simple_list_item_1, mTables);
		setListAdapter(adapter);
	}
	//alternative method makes this useless
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Table t = (Table)(getListAdapter()).getItem(position);
		Log.d(TAG, t.getmId() + " was clicked");
		GlobalVars.toDisplayInView = t.getmTable_Number(); //we have the name of the table we want to show in the info view.
		
	}
	//----
	
	
	
	
	//-----
	private class TableAdapter extends ArrayAdapter<Table> {
		public TableAdapter(ArrayList<Table> tables) {
			super(getActivity(), 0, tables);
		}
	}
	
	/*
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null) {
			convertView= getActivity().getLayoutInflater().inflate(R.layout.list_item_table, null);
		}
		
		Table t = getmId(position);
		TextView titleTextView=
				(TextView)convertView.findViewById(R.id.table_list_item_titleTextView);
		
	
	}
	*/
}
