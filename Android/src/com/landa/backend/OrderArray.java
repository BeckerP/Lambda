package com.landa.backend;

import java.util.ArrayList;

import android.content.Context;
import android.text.format.Time;

public class OrderArray {
	private ArrayList<Item> mItems;
	private static OrderArray sItemArray;
	private Context mAppContext;
	private Time timestamp;
	private String tableID;

	private OrderArray(Context appContext) {
		mAppContext = appContext;
		mItems = new ArrayList<Item>();
		timestamp = new Time();
	}

	public static OrderArray get(Context c) {
		if (sItemArray == null) {
			sItemArray = new OrderArray(c.getApplicationContext());
		}
		return sItemArray;
	}

	public ArrayList<Item> getItems() {
		return mItems;
	}

	public Time getTime() {
		return timestamp;
	}

	public String getTableID() {
		return tableID;
	}

	public void setTableID(String ID) {
		tableID = ID;
	}
}
