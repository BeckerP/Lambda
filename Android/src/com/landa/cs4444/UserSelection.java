package com.landa.cs4444;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.landa.R;
import com.landa.backend.asyncLoader;
import com.landa.helpers.AsyncHelper;

public class UserSelection extends Activity {

	public static Context appContext;
	public static final String USER_SELECTION_KEY = "com.landa.cs4444.User_Selection";

	String value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_selection);
		AsyncHelper.setHold("bad");
		// pass this method your currents context.
		AsyncHelper.setContext(getApplicationContext());

		// set this method with the url you need to access
		AsyncHelper.setURL("http://192.184.85.36/csce4444/android/menu.php");

		AsyncHelper.setName("Menu"); // must do this or nothing will happen.
		asyncLoader task = new asyncLoader(UserSelection.this);
		task.execute(); // to start the connection process.
		final Button AdButton = (Button) findViewById(R.id.admin);
		AdButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				value = "Admin";
				Intent myIntent = new Intent(UserSelection.this, LogIn.class);
				myIntent.putExtra(USER_SELECTION_KEY, value);
				UserSelection.this.startActivity(myIntent);
			}
		});

		final Button serverButton = (Button) findViewById(R.id.server);
		serverButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				value = "Wait";
				AsyncHelper.setHold("Wait");
				Intent myIntent = new Intent(UserSelection.this, LogIn.class);
				myIntent.putExtra(USER_SELECTION_KEY, value);
				UserSelection.this.startActivity(myIntent);
			}
		});

		final Button cookButton = (Button) findViewById(R.id.cook);
		cookButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				value = "Cook";
				// Intent myIntent = new Intent(UserSelection.this,
				// LogIn.class);
				Intent myIntent = new Intent(UserSelection.this, LogIn.class);

				myIntent.putExtra(USER_SELECTION_KEY, value);
				UserSelection.this.startActivity(myIntent);
			}
		});

		final Button customerButton = (Button) findViewById(R.id.customer);
		customerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Intent myIntent = new Intent(UserSelection.this,
				// Customer_Activity_Single_Pane.class);
				AsyncHelper.setHold("null");
				Intent myIntent = new Intent(UserSelection.this,
						UserSelectionTableSelect.class);
				UserSelection.this.startActivity(myIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_selection, menu);
		return true;
	}
}
