package com.landa.cook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;
import com.landa.R;
import com.landa.backend.AsyncUpdater;
import com.landa.backend.GcmImplementation;
import com.landa.backend.RegisterGCM;
import com.landa.db.orderOpenHelper;
import com.landa.helpers.AsyncHelper;

public class MyCard extends Card {

	public MyCard(String title) {
		super(title);
	}

	public MyCard(String title, String desc, String sENDER_Id) {
		super(title, desc, sENDER_Id);
	}
	public MyCard(String tableId, String title ,String desc, String sENDER_Id,int status) {
		super(tableId, title, desc, sENDER_Id, status);
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.cook_card_ex,
				null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.description)).setText(desc);
		return view;
	}
	
	@Override
	public void cardswipeactions() {
		orderOpenHelper db = new orderOpenHelper(AsyncHelper.getContext());
		if(status==1)
		{
		db.updateItem(tableId,title,String.valueOf(status+1));//local db change. 
		db.close();
		Log.d("Card swipe: ",title+" "+tableId);
		AsyncHelper.setURL("http://192.184.85.36/csce4444/android/update.php");
		AsyncHelper.setName(title);
		AsyncHelper.setTable(tableId);
		AsyncHelper.setSearch(String.valueOf(status+1));
		AsyncUpdater task = new AsyncUpdater();//jon you fixed this last time not sure how. 
		task.execute();
		
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				Log.v("URL", "> " + GcmImplementation.SERVER_URL);
				Map<String, String> params = new HashMap<String, String>();
				try {
					RegisterGCM.Post("http://192.184.85.36/csce4444/android/cooktoserver.php", params);

					// TODO implement refill.php
				} catch (IOException e) {
					// Here we are simplifying and retrying on any error; in
					// a real
					// application, it should retry only on unrecoverable
					// errors
					// (like HTTP error code 503).
				}
			return null;
			}
		}.execute();
	}
		else if(status==2)
		{
			db.updateItem(tableId,title,String.valueOf(status+1));//local db change. 
			db.close();
			Log.d("Card swipe: ",title+" "+tableId);
			AsyncHelper.setURL("http://192.184.85.36/csce4444/android/update.php");
			AsyncHelper.setName(title);
			AsyncHelper.setTable(tableId);
			AsyncHelper.setSearch(String.valueOf(status+1));
			AsyncUpdater task = new AsyncUpdater();//jon you fixed this last time not sure how. 
			task.execute();
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... arg0) {
					Log.v("URL", "> " + GcmImplementation.SERVER_URL);
					Map<String, String> params = new HashMap<String, String>();
					params.put("tableNo", tableId);
					try {
						RegisterGCM.Post("http://192.184.85.36/csce4444/android/readytopay.php", params);

						// TODO implement refill.php
					} catch (IOException e) {
						// Here we are simplifying and retrying on any error; in
						// a real
						// application, it should retry only on unrecoverable
						// errors
						// (like HTTP error code 503).
					}
				return null;
				}
			}.execute();
		}
		else
		{
			
		}
	}
	
}
