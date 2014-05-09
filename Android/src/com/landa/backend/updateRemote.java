package com.landa.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.util.Log;

import com.landa.customer.CustomerOrderFragment;
import com.landa.helpers.AsyncHelper;

public class updateRemote {

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
	public static int connect(String url) throws ClientProtocolException,
			IOException, JSONException {
		// I need to send a list of all the orders to send.

		try {
			HttpClient http = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			String data = "null";
			// post.setHeader("host", url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			int count = CustomerOrderFragment.order.size() - 1;
			Log.i("Count: ", Integer.toString(count));

			while (count >= 0) {
				nameValuePairs.add(new BasicNameValuePair("name", "Orders"));
				nameValuePairs.add(new BasicNameValuePair("title", AsyncHelper.getName()));
				nameValuePairs.add(new BasicNameValuePair("tableId", AsyncHelper.getTable()));
				nameValuePairs.add(new BasicNameValuePair("value", AsyncHelper.getSearch()));
				
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = http.execute(post);

				// Receive code
				HttpEntity entity = response.getEntity();
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.i("Results:", result);
				data = result;
				instream.close();
				nameValuePairs.clear();
				--count;
			}
			if (data.equals("True")) {
				CustomerOrderFragment.order.clear();
				return 1;
			}
			CustomerOrderFragment.order.clear();
			// CustomerOrderFragment.update();
		} catch (ClientProtocolException e) {
			// do something
		} catch (IOException e) {
			// do something
		}
		return 0;

	}

}
