package com.landa.backend;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.landa.customer.CustomerOrderFragment;
import com.landa.helpers.AsyncHelper;

public class Async_orderSender extends AsyncTask<Void, Void, Integer> {

	private Activity activity;
	protected ProgressDialog dlg;
	private Handler myHandler;

	private long startRead, endRead, endJson;

	public Async_orderSender(Activity activity) {
		this.activity = activity;
	}

	String SERVER_URL = AsyncHelper.getURL();
	Context currentCon = AsyncHelper.getContext();

	protected void onPreExecute() {
		super.onPreExecute();
		myHandler = new Handler();

		dlg = new ProgressDialog(activity);
		dlg.setTitle("Sending order, please wait.");
		dlg.show();
		startRead = System.currentTimeMillis();
		// Toast.makeText(currentCon, "Connecting to server.",
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	protected Integer doInBackground(Void... arg0) {

		int result = 0;

		try {

			result = DataSender.connect(SERVER_URL);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		// if(result==1)
		// {
		myHandler.post(new Runnable() {
			public void run() {
				dlg.dismiss();
				Log.i("updating", "Now");
				CustomerOrderFragment.update();
				
				
				
			}
		});
		// }

		/*
		 * else { dlg.setTitle("Error Please try again."); //need to wait or
		 * sleep for for .5 sec dlg.dismiss(); }
		 */

	}
}
