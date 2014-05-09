package com.landa.db;

import android.os.AsyncTask;

import com.landa.helpers.AsyncHelper;

public class AsyncMenu extends AsyncTask<String, Void, Void> {

	public static final String cat = "Category";
	public static final String item = "Item";

	@Override
	protected Void doInBackground(String... ID) {

		MenuOpenHelper db = new MenuOpenHelper(AsyncHelper.getContext());
		int id = Integer.parseInt(ID[0]);
		if (AsyncHelper.getSearch().equals("Category")) {
			db.getCategory(AsyncHelper.getName());
		} else if (AsyncHelper.getSearch().equals("Item")) {
			db.getItem(AsyncHelper.getName());
		}
		return null;
	}
}