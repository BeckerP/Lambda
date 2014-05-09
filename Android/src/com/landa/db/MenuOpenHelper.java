package com.landa.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.landa.backend.Item;
import com.landa.helpers.menuHelper;

public class MenuOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String Poi_TABLE = "Menu";
	private static final String Type_int = " INTEGER";
	private static final String DATABASE_NAME = "Lambda";
	private static final String Type_float = " REAL";
	private static final String Type_text = " TEXT";
	private static final String format = "Id " + Type_int + "PRIMARY KEY, "
			+ "Name" + Type_text + "," + "Category" + Type_text + "," + "Price"
			+ Type_float + "," + "Modifiables" + Type_text + "," + "Descripton"
			+ Type_text + "," + "PicLocation" + Type_text + "," + "Visibility"
			+ " INTEGER";
	private static final String Poi_TABLE_CREATE = "CREATE TABLE " + Poi_TABLE
			+ " (" + format + ")";

	public MenuOpenHelper(Context context) {
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
	public void addItem(menuHelper help) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("Id", help.getId());
		values.put("Name", help.getName());
		values.put("Category", help.getCategory());
		values.put("Price", help.getPrice());
		values.put("Modifiables", help.getModifiables());
		values.put("Descripton", help.getDescription());
		values.put("PicLocation", help.getPicLocation());
		values.put("Visibility", help.getVisibility());
		db.insert(Poi_TABLE, null, values);
		db.close();
	}

	
	public void addPopular(menuHelper help) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("Id", help.getId());
		values.put("Name", help.getName());
		values.put("Category", help.getCategory());
		values.put("Price", help.getPrice());
		values.put("Modifiables", help.getModifiables());
		values.put("Descripton", help.getDescription());
		values.put("PicLocation", help.getPicLocation());
		values.put("Visibility", help.getVisibility());
		db.insert(Poi_TABLE, null, values);
		db.close();
	}
	
	
	// Getting single poi
	public menuHelper getItem(String Name) {
		SQLiteDatabase db = this.getReadableDatabase();
		menuHelper help;
		/*Cursor cursor = db.query(Poi_TABLE, new String[] { "Id", "Name",
				"Category", "Price", "Modifiables", "Descripton",
				"PicLocation", "Visibility" }, "Name" + "=?",
				new String[] { String.valueOf(Name) }, null, null, null);*/
		
		String selectQuery = "SELECT * FROM " + Poi_TABLE
				+ " WHERE Name = " + "'" + Name + "'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null&&cursor.moveToFirst())
			{

		help = new menuHelper(cursor.getString(0),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7));
		db.close();
		return help;
			}
		
		else
		{
			db.close();
			return null;
		}
		
		

	}

	public void dropTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + Poi_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + Poi_TABLE);
		onCreate(db);
	}

	public ArrayList<Item> getCategory(String name) {

		ArrayList<Item> Menu = new ArrayList<Item>();
		Item item;
		String selectQuery = "SELECT * FROM " + Poi_TABLE
				+ " WHERE Category = " + "'" + name + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				item = new Item(cursor.getString(1), cursor.getString(5),
						Double.parseDouble(cursor.getString(3)));
				Menu.add(item);
			} while (cursor.moveToNext());
		}

		return Menu;
	}

}
