package com.landa.cs4444;

import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.landa.R;
import com.landa.admin.Admin;
import com.landa.backend.asyncLoader;
import com.landa.cook.CookActivity;
import com.landa.helpers.AsyncHelper;

public class LogIn extends Activity {

	Context appContext;
	EditText userInput, passwordInput;
	Toast Result;
	List<NameValuePair> data;
	boolean correctPassword = false;
	public static final String USER_SELECTION_KEY = "com.landa.cs4444.User_Selection";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		AsyncHelper.setContext(getApplicationContext()); // pass this method
															// your currents
															// context.
		AsyncHelper.setURL("http://192.184.85.36/csce4444/android/order.php"); // set
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
		asyncLoader task = new asyncLoader(LogIn.this); // to start the connection
												// process.
		task.execute();
		final String value = intent
				.getStringExtra(UserSelection.USER_SELECTION_KEY);

		setContentView(R.layout.log_in);

		userInput = (EditText) findViewById(R.id.User);
		passwordInput = (EditText) findViewById(R.id.password);

		final Button SubmitButton = (Button) findViewById(R.id.submit);
		SubmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Grab the text from the editTexts after Submit button click
				String user = userInput.getText().toString();
				String pass = passwordInput.getText().toString();

				// Check equalities using .equals never == for strings!! Only
				// checking object then!
				if (user.equals("Admin") && pass.equals("password")
						&& value.equals("Admin")) {
					// Switch to Admin activity
					passwordSuccess();
					Intent AdminActivity = new Intent(LogIn.this, Admin.class);
					startActivity(AdminActivity);
				}

				else if (user.equals("Server") && pass.equals("password")
						&& value.equals("Wait")) {
					passwordSuccess();
					
					String Selection = "Wait";
					Intent myIntent = new Intent(LogIn.this,UserSelectionTableSelect.class);
					myIntent.putExtra("Selection", Selection);
					LogIn.this.startActivity(myIntent);
				}

				else if (user.equals("Cook") && pass.equals("password")
						&& value.equals("Cook")) {
					passwordSuccess();
					Intent CookActivity = new Intent(LogIn.this,
							CookActivity.class);
					startActivity(CookActivity);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Incorrect username or password for specified account.",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void passwordSuccess() {
		Toast.makeText(getApplicationContext(), "Login successful",
				Toast.LENGTH_LONG).show();
	}
}
