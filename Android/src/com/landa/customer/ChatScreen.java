package com.landa.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.landa.R;
import com.landa.backend.GcmImplementation;
import com.landa.backend.RegisterGCM;
import com.landa.backend.asyncLoader;
import com.landa.cook.CookActivity;
import com.landa.customer.ChatItems.Message;
import com.landa.customer.ChatItems.filter;
import com.landa.helpers.AsyncHelper;
import com.landa.waitStaff.Table;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatScreen extends Activity {
	private ArrayList<Message> mMessage;
	private ArrayList<String> messages ;
	private ArrayAdapter<String> adapter; 
	static final String TAG = "Chat";

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.chat);

	TextView title = (TextView) findViewById(R.id.tablechatnum);
	String tableTo = getIntent().getStringExtra("tableTo");

	title.setText("Table "+tableTo);
	LocalBroadcastManager.getInstance(this).registerReceiver(
			mMessageReceiver, new IntentFilter("chat"));	
	
	
	//String tableFrom = getIntent().getStringExtra("tableFrom");	
	//Message m = new Message("Heya", tableTo, tableFrom);

	messages = new ArrayList<String>();
	ListView chatmessage = (ListView) findViewById (R.id.chatmessages);

	//m.setmMessages("HEYA", tableTo, tableFrom);	
	//mMessage.add(m);

	
	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);
	//setListAdapter(adapter);
	chatmessage.setAdapter(adapter);

	//adapter.notifyDataSetChanged();
	}

	public void sendMessage(View view){
		EditText edittext = (EditText) findViewById(R.id.editText1);
		final String message = filter.filterwords(edittext.getText().toString());
		
		//messages.add("You: " + edittext.getText().toString());
		messages.add("You: " + message);
		edittext.setText("");
		InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				Log.v("URL", "> " + GcmImplementation.SERVER_URL);
				Map<String, String> params = new HashMap<String, String>();
				String tableTo = getIntent().getStringExtra("tableTo");
				String tableFrom = getIntent().getStringExtra("tableFrom");
				params.put("tableTo", tableTo);
				params.put("tableFrom", tableFrom);
				params.put("msg", message);
				try {
					RegisterGCM.Post("http://192.184.85.36/csce4444/android/gcm/sendchatmsg.php", params);

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
	
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "recieved message from gcm backend thing");
			String action = intent.getAction();
			String tableFrom = intent.getStringExtra("tableFrom");
			String msg = intent.getStringExtra("msg");
			String tableTo = getIntent().getStringExtra("tableTo");
			if(tableFrom.equals(tableTo)){
				messages.add("Them: "+ msg);
				adapter.notifyDataSetChanged();
			}

		}
	};
}
