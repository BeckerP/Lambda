package com.landa.customer;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.landa.waitStaff.Table;
import com.landa.waitStaff.Table_List;

public class CustomerChat extends ListActivity {
	
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
	static final String TAG2 = "GCM";

	public static TextView mDisplay;
	Context context;

	private ArrayList<Table> mTables;
	private static final String TAG = "Chatlist";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = getApplicationContext();


		
		mTables = Table_List.get(this).getTables();
		ArrayAdapter<Table> adapter = new ArrayAdapter<Table>(this,
				android.R.layout.simple_list_item_1, mTables);
		setListAdapter(adapter);
		
	}
	
	
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
		String tableFrom = getIntent().getStringExtra("tableNo");
    	Long tablenum = id+1;
		Intent chatscreen = new Intent(this, ChatScreen.class);
		chatscreen.putExtra("tableTo", tablenum.toString());
		chatscreen.putExtra("tableFrom",tableFrom);
		startActivity(chatscreen);
    	
    }



	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG2, "recieved message from gcm backend thing");
			String action = intent.getAction();
		}
	};
}