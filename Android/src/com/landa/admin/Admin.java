package com.landa.admin;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.landa.R;

public class Admin extends Activity {

	WebView adminView;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

 		setContentView(R.layout.activity_admin);
 		
 		adminView = (WebView) findViewById(R.id.adminView);
		adminView.getSettings().setJavaScriptEnabled(true);
 		adminView.loadUrl("http://192.184.85.36/csce4444/android/admin.php");
		// Intent AdminActivity = getIntent(); Placeholder if you ever want to catch the other intent. Just import the class back in Ctrl-Shift-O 

	}
}
