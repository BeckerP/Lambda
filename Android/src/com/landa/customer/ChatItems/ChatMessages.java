package com.landa.customer.ChatItems;

import java.util.ArrayList;

import android.content.Context;

public class ChatMessages {
	private ArrayList<Message> mMessages;
	private static ChatMessages sMessage_List;
	private Context mAppContext;
	
private ChatMessages(Context appContext) {
	mAppContext = appContext;
	mMessages = new ArrayList<Message>();
}
}
/*
public class Table_List {
private ArrayList<Table> mTables;
private static Table_List sTable_List;
private Context mAppContext;

private Table_List(Context appContext) {
	mAppContext = appContext;
	mTables = new ArrayList<Table>();
	for (int i = 1; i < 22; i++) {// when wait staff starts, create list of
									// tables 1-21
		Table t = new Table();
		t.setmTable_Number("Table " + i);
		mTables.add(t);
	}
}

public static Table_List get(Context c) {
	if (sTable_List == null) {
		sTable_List = new Table_List(c.getApplicationContext());
	}
	return sTable_List;
}

public ArrayList<Table> getTables() {
	return mTables;
}

public Table getTable(UUID id) {
	for (Table t : mTables) {
		if (t.getmId().equals(id))
			;
		return t;
	}
	return null;
}
}*/