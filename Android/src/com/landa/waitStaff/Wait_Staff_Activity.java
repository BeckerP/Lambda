package com.landa.waitStaff;

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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.landa.R;
import com.landa.backend.RegisterGCM;
import com.landa.db.orderOpenHelper;
import com.landa.helpers.AsyncHelper;

public class Wait_Staff_Activity extends FragmentActivity {

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
	Context context;
	String regid;

	@Override
	public void onCreate(Bundle SavedInstanceState) { //when Server program starts
		super.onCreate(SavedInstanceState);
	
		context = getApplicationContext();
		
        gcm = GoogleCloudMessaging.getInstance(this);
        regid = getRegistrationId(context);
		registerInBackground(regid);
		setContentView(R.layout.waitstaff_activity); //go to this xml file for layout

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_for_table_list); //make fragment, use this layout
		
		if (fragment == null) {
			fragment = new TableListFrag();
			fm.beginTransaction().add(R.id.fragment_for_table_list, fragment) //draw fragment
					.commit();
		}
		
		

	
	 //block no longer used with cards interface
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
		return getSharedPreferences(Wait_Staff_Activity.class.getSimpleName(),
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
		params.put("name", "wait");
		Log.i(TAG, "Inside sendRIDTOB");
		try {
			// post("http://162.243.86.210/gcm/register.php", params);
			RegisterGCM.Post("http://192.184.85.36/csce4444/android/gcm/register.php",
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


		}
	};
	
	//TODO: Broadcast receiver wait staff.
	
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
