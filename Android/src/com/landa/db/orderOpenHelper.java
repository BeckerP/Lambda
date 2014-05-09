package com.landa.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.landa.backend.Item;
import com.landa.cook.CookActivity;
import com.landa.helpers.AsyncHelper;
import com.landa.helpers.Order;
import com.landa.helpers.menuHelper;
import com.landa.helpers.orderHelper;

public class orderOpenHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 2;
	private static final String Poi_TABLE = "Tab_Order";
	private static final String Type_int = " INTEGER";
	private static final String DATABASE_NAME = "Lambda";
	private static final String Type_float = " REAL";
	private static final String Type_text = " TEXT";
	private static final String format = "TableId " + Type_int
			+ "PRIMARY KEY, " + "Title" + Type_text +
			"," + "Mod" + Type_text+ 
			"," + "Price" + Type_float +
			","+"Status" + Type_int;
	private static final String Poi_TABLE_CREATE = "CREATE TABLE " + Poi_TABLE
			+ " (" + format + ")";

	public orderOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Poi_TABLE_CREATE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Poi_TABLE);
		onCreate(db);
	}

	// Adding new poi
	public void addItem(orderHelper help) {
		SQLiteDatabase db = this.getWritableDatabase();
		Log.i("Order status: ", help.getStatus());
		ContentValues values = new ContentValues();
		values.put("TableId", help.getTableId());
		values.put("Title", help.getTitle());
		values.put("Mod", help.getMod());
		values.put("Price", help.getPrice());
		values.put("Status", help.getStatus());
		db.insert(Poi_TABLE, null, values);
		db.close();
	}

	// Getting single poi
	public menuHelper getOrder(String Name) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(Poi_TABLE, new String[] { format }, "Name" + "=?",
						new String[] { String.valueOf(Name) }, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		menuHelper help = new menuHelper((cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7));
		// return poi
		return help;
	}

	public List<String> getOrders() {//gets all orders
		List<String> Order = new ArrayList<String>();

		String selectQuery = "SELECT  * FROM " + Poi_TABLE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				Order.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		return Order;

	}

	public ArrayList<Order> Orders() {
		ArrayList<Order> Order = new ArrayList<Order>();

		String selectQuery = "SELECT  * FROM " + Poi_TABLE
				+ " GROUP BY TableId";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		
		do{	
				//cursor.moveToNext();
			Order.add(new Order(cursor.getString(0), cursor.getString(1),
					cursor.getString(2),
					Double.parseDouble(cursor.getString(3)), cursor
							.getString(4)));
		}while(cursor.moveToNext());
		

		

		return Order;

	}

	public ArrayList<Order> tableOrder(int num) {//returns table order
		ArrayList<Order> Order = new ArrayList<Order>();

		String selectQuery = "SELECT  * FROM " + Poi_TABLE
				+ " WHERE TableId = " + num;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				/*Log.i("search: ",
						(cursor.getString(0) + " " + cursor.getString(1) + " "
								+ cursor.getString(2) + " " + Double
								.parseDouble(cursor.getString(3))));*/
				Order.add(new Order(cursor.getString(0), cursor.getString(1),
						cursor.getString(2), Double.parseDouble(cursor
								.getString(3)), cursor.getString(4)));
			} while (cursor.moveToNext());
		}

		return Order;

	}
	
	
	public ArrayList<Item> tableOrder_Checkout(int num) {//returns table order
		ArrayList<Item> Order = new ArrayList<Item>();

		String selectQuery = "SELECT  * FROM " + Poi_TABLE
				+ " WHERE TableId = " + num;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				/*Log.i("search: ",
						(cursor.getString(0) + " " + cursor.getString(1) + " "
								+ cursor.getString(2) + " " + Double
								.parseDouble(cursor.getString(3))));*/
				Order.add(new Item(cursor.getString(1),cursor.getString(2),Double.parseDouble(cursor.getString(3))));
			} while (cursor.moveToNext());
		}

		return Order;

	}
	
	

	public int getCount()// return the number to table orders
	{
		int num = 0;
		String selectQuery = "SELECT  * FROM " + Poi_TABLE
				+ " GROUP BY TableId";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		while (cursor.moveToNext()) {
			++num;
		}
		return num;

	}

	public int getLen(int choice)// return the number to table orders
	{
		int num = 0;
		String selectQuery = "SELECT  * FROM " + Poi_TABLE
				+ " Where TableId = " + choice;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		while (cursor.moveToNext()) {
			++num;
		}
		return num;

	}

	public void dropTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + Poi_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + Poi_TABLE);
		onCreate(db);
	}

	public void removeOrder(String TableId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Poi_TABLE, "TableId" + " = ?",
				new String[] { String.valueOf(TableId) });
		db.close();

	}

	public void updateItem(String tableId, String title, String num) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE " + Poi_TABLE+" SET Status = '"+num+"' WHERE Title = '"+title+"' AND TableId = '"+tableId+"'" );
		
		
	}

}
