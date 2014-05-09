package com.landa.customer;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.landa.R;

public class Game_Activity extends Activity{

	WebView gameView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		
		gameView = (WebView) findViewById(R.id.adminView);
		gameView.getSettings().setJavaScriptEnabled(true);
		gameView.loadUrl("http://192.184.85.36/csce4444/android/game.php");
		
		//String url = "http://192.184.85.36/csce4444/android/game.php";
		//String postData = "tablenumber=whatever the table number is";
		// 'whatever the table number is' needs to be replaced with the table number variable
		//gameView.postUrl(url, EncodingUtils.getBytes(postData, "base64"));

		// Intent AdminActivity = getIntent(); Placeholder if you ever want to
		// catch the other intent. Just import the class back in Ctrl-Shift-O
	}
}