package com.landa.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DataGeter {

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/*
	 * This is a test function which will connects to a given rest service and
	 * prints it's response to Android Log with labels .
	 */
	public static void connect(String url) throws ClientProtocolException,
			IOException, JSONException {

		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("accept", "application/json");
		// Execute the request
		HttpResponse response;
		try {

			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i("Connect", response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.i("Results:", result);

				JSONObject json = new JSONObject(result);

				String title = json.getString("Type");
				instream.close();
				Log.i("Type: ", title);
				if (title.equals("Menu")) {
					dataParse.storeMenu(result);
					
				} else if (title.equals("Order")) {
					dataParse.storeOrder(result);
				} else {
				}
				
				
			}

			/*
			 * String FILENAME = AsyncHelper.name; FileOutputStream fos =
			 * AsyncHelper.current.openFileOutput(FILENAME,
			 * Context.MODE_PRIVATE); fos.write(result.getBytes()); fos.close();
			 */

		} catch (ClientProtocolException e) {
			// do something
		} catch (IOException e) {
			// do something
		}

	}

}
