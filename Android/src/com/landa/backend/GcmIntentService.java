/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.landa.backend;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.landa.R;
import com.landa.cook.CookActivity;
import com.landa.waitStaff.WaitInfo;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCM Demo";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		String usertype = intent.getStringExtra("user");
		String action = intent.getStringExtra("action");

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString(), "");
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString(), "");
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				//Log.i(TAG, "Message is" + usertype + action);
				for (int i = 0; i < 5; i++) {
					Log.i(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
					}
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				// TODO: add other users
				if(usertype.equals("cook")){
					Log.i(TAG, "Sending to cook activity");
					CookOrderToActivity();
				}
				if(usertype.equals("waitstaff")){
					if(action.equals("refill")){
						String table = intent.getStringExtra("table");
						RefillToActivity(table);
						sendNotification("Table "+ table +" would like a refill.", table);
					}
					if(action.equals("help")){
						String table = intent.getStringExtra("table");
						HelpToActivity(table);
						sendNotification("Table " + table + " requests help.", table);
					}
					if(action.equals("refresh")){
						RefreshWait();
						
					}
				}
				if(usertype.equals("customer")){
					if(action.equals("readytopay")){
						readyToPay();
					}
					if(action.equals("chat")){
						String tableFrom = intent.getStringExtra("tableFrom");
						String msg = intent.getStringExtra("msg");
						chat(tableFrom, msg);
					}
				}
				
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReciever.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String msg, String table) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(getApplicationContext(), WaitInfo.class);
		/*PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, WaitInfo.class), 0); */
		intent.putExtra("tableNo",table);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_stat_gcm)
				.setContentTitle("GCM Notification")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		
		
		
		
		
		
	}

	private void CookOrderToActivity() {
		Intent intent = new Intent("cookOrder");
		sendLocationBroadcast(intent);
	}

	private void RefillToActivity(String table) {
		Intent intent = new Intent("waitstaff");
		intent.putExtra("action","refill");
		intent.putExtra("table", table);
		sendLocationBroadcast(intent);
	}

	private void HelpToActivity(String table) {
		Intent intent = new Intent("waitstaff");
		intent.putExtra("action", "help");
		intent.putExtra("table", table);
		sendLocationBroadcast(intent);
	}
	private void RefreshWait(){
		Intent intent = new Intent("waitstaff");
		intent.putExtra("action", "refresh");
		sendLocationBroadcast(intent);
	}
	private void readyToPay(){
		Intent intent = new Intent("customerpay");
		sendLocationBroadcast(intent);
	}
	private void chat(String tableFrom, String msg){
		Intent intent = new Intent("chat");
		intent.putExtra("tableFrom", tableFrom);
		intent.putExtra("msg", msg);
		sendLocationBroadcast(intent);
	}

	private void sendLocationBroadcast(Intent intent) {
		// intent.putExtra("currentSpeed", currentSpeed);
		// intent.putExtra("latitude", latitude);
		// intent.putExtra("longitude", longitude);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
}
