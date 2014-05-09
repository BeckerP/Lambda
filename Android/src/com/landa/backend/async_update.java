package com.landa.backend;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.landa.helpers.AsyncHelper;

public class async_update extends AsyncTask<Void, Void, Void> {

	String SERVER_URL = AsyncHelper.getURL();
	Context currentCon = AsyncHelper.getContext();

	protected void onPreExecute() {
		// update the UI immediately after the task is executed
		super.onPreExecute();

		Toast.makeText(currentCon, "Connecting to server.", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		try {
			try {
				update.connect(SERVER_URL);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(JSONObject result) throws JSONException {
		JSONArray employees = result.getJSONArray("Employees");
	}

}
