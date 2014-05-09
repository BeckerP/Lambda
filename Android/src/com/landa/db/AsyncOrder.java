package com.landa.db;

import android.os.AsyncTask;

import com.landa.helpers.AsyncHelper;

public class AsyncOrder extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... ID) {

		orderOpenHelper db = new orderOpenHelper(AsyncHelper.getContext());

		if (AsyncHelper.getSearch().equals("Read")) {
			db.getOrders();
		} else if (AsyncHelper.getSearch().equals("remove")) {
			db.removeOrder(AsyncHelper.getName());
		}
		return null;
	}
}