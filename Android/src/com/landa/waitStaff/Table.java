package com.landa.waitStaff;

import java.util.UUID;

public class Table {
	private UUID mId; //id to know which table we're looking at
	private String mTable_Number; //to display in ListFragment
	//Getters and Setters below
	@Override
	public String toString() {
		return mTable_Number;
	}

	public Table() {
		mId = UUID.randomUUID();
	}

	public String getmTable_Number() {
		return mTable_Number;
	}

	public void setmTable_Number(String mTable_Number) {
		this.mTable_Number = mTable_Number;
	}

	public UUID getmId() {
		return mId;
	}

}