package com.landa.customer.ChatItems;

import java.util.UUID;

public class Message {
	private UUID mId;
	private String mMessage;
	private String mTableFrom;
	private String mTableTo;
	
	public Message(String mMessage, String mFrom, String mTo) {
		this.mMessage = mMessage;
		this.mTableFrom = mFrom;
		this.mTableTo = mTo;
	}
	public void setmMessages(String mMessage, String mTableFro, String mTableTo) {
		this.mMessage = mMessage;
		this.mTableFrom = mTableFro;
		this.mTableTo = mTableTo;
	}
	public String getmMessage() {
		return mMessage;
	}
	public String getmTableFrom(){
		return mTableFrom;
	}
	public String getmTableTo(){
		return mTableTo;
	}
	
	
}
/*
public class Table {
	private UUID mId;
	private String mTable_Number;

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

}*/