package com.landa.cook;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.landa.R;
import com.landa.backend.asyncLoader;
import com.landa.db.orderOpenHelper;
import com.landa.helpers.AsyncHelper;

public class CookActivity extends Activity {

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	String SENDER_ID = "298068567427";
	static String SENDER_Id = "298068567427";
	public static String SERVER_URL = "http://192.184.85.36/";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCM";

	public static TextView mDisplay;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	Context context;
	orderOpenHelper db = new orderOpenHelper(AsyncHelper.getContext());
	String regid;
	// Activity act=this.g;
	// AsyncHelper.setActivity(act);
	static CardUI mCardView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cook);
		// order();
		
		
		context = getApplicationContext();
		
        gcm = GoogleCloudMessaging.getInstance(this);
        regid = getRegistrationId(context);
		registerInBackground(regid);

		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver, new IntentFilter("cookOrder"));

		// init CardView
		mCardView = (CardUI) findViewById(R.id.cardsview);
		mCardView.setSwipeable(true);
		order();
		MyCard androidViewsCard = new MyCard("www.androidviews.net");
		androidViewsCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.androidviews.net/"));
				startActivity(intent);

			}
		});

	}

	public static void order(/* Activity activity */) {
		orderOpenHelper dd = new orderOpenHelper(AsyncHelper.getContext());
		mCardView.clearCards();
		int outer = dd.getCount();
		int id;
		String Id;
		int num = 0;
		int tmp;
	
		
		for (int x = 0; outer > x; x++)// loops through all order by table.
		{
			
			CardStack stack = new CardStack();
			Log.i("outer:", Integer.toString(outer));
			Log.i("x:", Integer.toString(x));
		
			
			Id = ((dd.Orders().get(x).getTableId()));
			id=Integer.parseInt(Id);
			int in = dd.getLen(id);
			int check=0;
			for (int y = 0; in > y; ++y) {
				if(Integer.parseInt(dd.tableOrder(id).get(y).getStatus())==1) 
				{
					check =1;
				}
				 
			}
			if (check == 1) {
			Log.i("TableId", Integer.toString(id));
			stack.setTitle(Integer.toString(id));// where i need the id number
													// as well in item.
			

			Log.i("in:", Integer.toString(in));
			for (int y = 0; in > y; ++y) {
				
				//Log.i("Y:", Integer.toString(y));
				Log.i("size of list",Integer.toString(dd.tableOrder(id).size()));
				
				if(Integer.parseInt(dd.tableOrder(id).get(y).getStatus())==1)
				{
					stack.add(new MyCard(dd.tableOrder(id).get(y).getTableId(),dd.tableOrder(id).get(y).getName()
							  ,dd.tableOrder(id).get(y).getMod(),
							SENDER_Id,Integer.parseInt(dd.tableOrder(id).get(y).getStatus())));	
					Log.i("card", dd.tableOrder(id).get(y).getName() + " "
							+ dd.tableOrder(id).get(y).getMod()); 
				}
				}
				
			}
			
			// stack.add(new
			// MyCard(db.tableOrder(id).get(tmp).getName()+" "+db.tableOrder(id).get(tmp).getMod()
			// ,Integer.toString(id)+":"+db.tableOrder(id).get(tmp).getName(),
			// SENDER_ID ));
			// CardUI mCardView;
			// mCardView = (CardUI) (findViewById(R.id.cardsview));//needs activity
			// not sure how to do it.
			// mCardView.setSwipeable(true);
			num = 0;
			mCardView.addStack(stack);
		}
		dd.close();
		mCardView.refresh();

	}
	
	public static void Refresh()
	{
		mCardView.refresh();
	}

	/**
	 * Stores the registration ID and the app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGcmPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Gets the current registration ID for application on GCM service, if there
	 * is one.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and the app versionCode in the application's
	 * shared preferences.
	 */

	private void registerInBackground(final String regidold) {
		if(regidold.isEmpty()){
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					String msg = "";
					try {
						if (gcm == null) {
							gcm = GoogleCloudMessaging.getInstance(context);
						}
						regid = gcm.register(SENDER_ID);
						Log.i(TAG, "Device registered with new id, reg id =" + regid);
						// msg = "Device registered, registration ID=" + regid;

						// You should send the registration ID to your server over
						// HTTP, so it
						// can use GCM/HTTP or CCS to send messages to your app.
						Log.i(TAG, "Sending registration");
						sendRegistrationIdToBackend();
						// Log.i(TAG, "Registration sent.");

						// For this demo: we don't need to send it because the
						// device will send
						// upstream messages to a server that echo back the message
						// using the
						// 'from' address in the message.

						// Persist the regID - no need to register again.
						storeRegistrationId(context, regid);

					} catch (IOException ex) {
						msg = "Error :" + ex.getMessage();
						// If there is an error, don't just keep trying to register.
						// Require the user to click a button again, or perform
						// exponential back-off.
					}
					return msg;
				}

				// @Override
				// protected void onPostExecute(String msg) {
				// mDisplay.append(msg + "\n");
				// }
			}.execute(null, null, null);
		}
		else{
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					String msg = "";
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = regidold;
					Log.i(TAG, "Device registered, reg id =" + regid);
					// msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over
					// HTTP, so it
					// can use GCM/HTTP or CCS to send messages to your app.
					Log.i(TAG, "Sending registration");
					sendRegistrationIdToBackend();
					// Log.i(TAG, "Registration sent.");

					// For this demo: we don't need to send it because the
					// device will send
					// upstream messages to a server that echo back the message
					// using the
					// 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
					return msg;
				}

				// @Override
				// protected void onPostExecute(String msg) {
				// mDisplay.append(msg + "\n");
				// }
			}.execute(null, null, null);
		}

	}
		
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */

	private SharedPreferences getGcmPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(CookActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);

	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regid); // Push the parameters
		params.put("name", "cook");
		// params.put("email", "emericaonline04@yahoo.com");
		Log.i(TAG, "Inside sendRIDTOB");
		try {
			// post("http://162.243.86.210/gcm/register.php", params);
			post("http://192.184.85.36/csce4444/android/gcm/register.php",
					params);

			Log.i(TAG, "Registration sent.");

		} catch (IOException e) {
			// Here we are simplifying and retrying on any error; in a real
			// application, it should retry only on unrecoverable errors
			// (like HTTP error code 503).
			return;
		}
	}

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "recieved message from gcm backend thing");
			String action = intent.getAction();
			AsyncHelper.setContext(getApplicationContext()); // pass this method
			// context.
			AsyncHelper
					.setURL("http://192.184.85.36/csce4444/android/order.php"); // set
																				// this
																				// method
																				// with
																				// the
																				// url
																				// you
																				// need
																				// to
																				// access
			AsyncHelper.setName("Order");// must do this or nothing will happen.
			AsyncHelper.setHold("good");
			AsyncHelper.setData("Cook");
			asyncLoader task = new asyncLoader(CookActivity.this); // to start the connection									
			// process.
			task.execute(); 
			//order();

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Issue a POST request to the server.
	 * 
	 * @param endpoint
	 *            POST address.
	 * @param params
	 *            request parameters.
	 * 
	 * @throws IOException
	 *             propagated from POST.
	 */
	public static void post(String endpoint, Map<String, String> params)
			throws IOException {

		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		Log.v(TAG, "Posting '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			Log.e("URL", "> " + url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}
