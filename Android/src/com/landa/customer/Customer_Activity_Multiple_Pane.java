package com.landa.customer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.landa.R;
import com.landa.backend.GcmImplementation;
import com.landa.backend.Item;
import com.landa.customer.DetailFragments.CustomerDrinkFragmentDetails;
import com.landa.customer.DetailFragments.CustomerMenuFragmentDetails;
import com.landa.customer.DrinkTabs.Tab1DrinkFragment.OnTab1DrinkListener;
import com.landa.helpers.AsyncHelper;

public class Customer_Activity_Multiple_Pane extends FragmentActivity implements
		OnTab1DrinkListener {
	// TODO Implement gcm features, chat, php: send notifications to database
	// and cook, send notification to waiter for this table

	// make an receipt arraylist to talk between the Drink and Menu Items and
	// the Receipt items.

	//String tableno = "1";

	protected int getLayoutResId() {
		return R.layout.activity_fragment_multipane;
	}

	public interface OnCustomerClickListener {
		public void onCustomerClick(View v);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_multipane);
		
		setContentView(getLayoutResId());
		String tableNo = getIntent().getStringExtra("tableNo"); // This labeling
			AsyncHelper.setTable(tableNo);													// is probably
																// confusing I
																// know.

		Intent multiPaneActivity = getIntent();

		long id = multiPaneActivity.getLongExtra(
				Customer_Activity_Single_Pane.MULTIPANE_LAYOUT, -1);
		if (id == R.id.Drink) {
			// Creating the listview fragment
			CustomerOrderFragment listViewFrag = new CustomerOrderFragment();
			FragmentManager fm = getSupportFragmentManager();

			FragmentTransaction transaction = fm.beginTransaction();
			transaction.add(R.id.fragment_container_order, listViewFrag);
			transaction.commit();

			// Creating the details fragment
			CustomerDrinkFragmentDetails detailsFrag = new CustomerDrinkFragmentDetails();

			transaction = fm.beginTransaction();
			transaction.add(R.id.fragment_container_detail, detailsFrag);


			transaction.commit();

		} else if (id == R.id.Menu) {
			CustomerOrderFragment listViewFrag = new CustomerOrderFragment();
			FragmentManager fm = getSupportFragmentManager();

			FragmentTransaction transaction = fm.beginTransaction();
			transaction.add(R.id.fragment_container_order, listViewFrag);
			transaction.commit();

			// Creating the details fragment
			CustomerMenuFragmentDetails detailsFrag = new CustomerMenuFragmentDetails();

			transaction = fm.beginTransaction();
			transaction.add(R.id.fragment_container_detail, detailsFrag);

			transaction.commit();

		} else if (id == R.id.Chat) {
			// make chat fragment if it's two pane otherwise delete this whole
			// row
			
			Intent ServerActivity = new Intent(Customer_Activity_Multiple_Pane.this, CustomerChat.class);
			ServerActivity.putExtra("tableNo", tableNo);
			startActivity(ServerActivity); 
			//setContentView(R.layout.customer_chat_select);

		} else if (id == R.id.Pay) {
			// Create the double pane fragment for payment
			FragmentManager fm = getSupportFragmentManager();
			// Creating the details fragment
			FragmentTransaction transaction = fm.beginTransaction();
			PaymentHelperFragment paymentHelper = new PaymentHelperFragment();
			transaction.add(R.id.fragment_container_order, paymentHelper);
			transaction.commit();

			transaction = fm.beginTransaction();
			CustomerPaymentFragment paymentFrag = new CustomerPaymentFragment();
			transaction.add(R.id.fragment_container_detail, paymentFrag);
			transaction.commit();
		}
	}

	@Override
	// Create the menu for HELP and REFILL button
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.customer_menu, menu);
		return true;
	}

	@Override
	// REACT to CLICKS on HELP and REFILL
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_refill:
			Toast.makeText(
					this,
					"Refill notification dispatched! Waitress will be with you shortly!",
					Toast.LENGTH_LONG).show();
			new AsyncTask<Void, Void, Void>() {
				String tableNo = getIntent().getStringExtra("tableNo");
				
				@Override
				protected void onPreExecute() {
				}

				@Override
				protected Void doInBackground(Void... arg0) {
					Log.v("URL", "> " + GcmImplementation.SERVER_URL);
					Map<String, String> params = new HashMap<String, String>();
					params.put("tableNo", tableNo);
					try {
						GcmImplementation.post(
								"http://192.184.85.36/csce4444/android/gcm/wait/refill.php", params);
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
			break;

		case R.id.action_help:
			// TODO Actually make this hook up with the waitress interface.
			Toast.makeText(
					this,
					"Help notification dispatched! Waitress will be with you shortly.",
					Toast.LENGTH_LONG).show();
			new AsyncTask<Void, Void, Void>() {
				String tableNo = getIntent().getStringExtra("tableNo");
				
						
				@Override
				protected void onPreExecute() {
				}

				@Override
				protected Void doInBackground(Void... arg0) {
					Log.v("URL", "> " + GcmImplementation.SERVER_URL);
					Map<String, String> params = new HashMap<String, String>();
					params.put("tableNo", tableNo);
					try {
						GcmImplementation.post(
								"http://192.184.85.36/csce4444/android/gcm/wait/help.php", params);
						// TODO implement help.php
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
			break;
		default:
			break;
		}
		return true;
	}

	public void onDataPass(Item item) {
		CustomerOrderFragment dataUser = (CustomerOrderFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_container_order);
		dataUser.use(item);
	}
}
