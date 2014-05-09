package com.landa.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.landa.customer.CustomerPaymentFragment;
import com.landa.helpers.AsyncHelper;

public class Payment {

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
			int count = CustomerOrderFragment.order.size();
			int count2=count;
			Log.i("Count: ", Integer.toString(count));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			
			while (count >= 0) {
				nameValuePairs.add(new BasicNameValuePair("Comp", "none"));
				nameValuePairs.add(new BasicNameValuePair("TableId", AsyncHelper.getName()));
				nameValuePairs.add(new BasicNameValuePair("Date",dateFormat.format(date) ));
				nameValuePairs.add(new BasicNameValuePair("Total", AsyncHelper.getTable()));
				nameValuePairs.add(new BasicNameValuePair("Tax", AsyncHelper.getSearch()));
				nameValuePairs.add(new BasicNameValuePair("Tip", AsyncHelper.getData()));
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			  int c=0;
				while(nameValuePairs.size()>c)
			    {
			    	Log.i("order paied:",nameValuePairs.get(c).toString());
			    	++c;
			    }
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
			
			HttpClient Http = new DefaultHttpClient();
			HttpPost Post = new HttpPost("http://192.184.85.36/csce4444/android/Top.php");
			List<NameValuePair> namePairs = new ArrayList<NameValuePair>(CustomerPaymentFragment.payment.size());
			for(int x=0;x<CustomerPaymentFragment.payment.size();++x) 
			{
				namePairs.add(new BasicNameValuePair("Item",CustomerPaymentFragment.payment.get(x).getName()));
				Log.i("Top Order:",CustomerPaymentFragment.payment.get(x).getName());
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = Http.execute(Post);

				// Receive code
				HttpEntity Entity = response.getEntity();
				InputStream Instream = Entity.getContent();
				String Result = convertStreamToString(Instream);
				Log.i("Results:", Result);
				data = Result;
				Instream.close();
				namePairs.clear();
				
			}
			
			
				return 1;
			
			// CustomerOrderFragment.update();
		} catch (ClientProtocolException e) {
			// do something
		} catch (IOException e) {
			// do something
		}
		return 0;

	}

}
