package com.landa.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.landa.cook.CookActivity;
import com.landa.db.MenuOpenHelper;
import com.landa.db.orderOpenHelper;
import com.landa.helpers.AsyncHelper;
import com.landa.helpers.menuHelper;
import com.landa.helpers.orderHelper;

public class dataParse {

	public static void storeMenu(String result) {
		try {
			// Toast.makeText(AsyncHelper.getContext(), "Data parser",
			// Toast.LENGTH_SHORT).show();
			MenuOpenHelper db = new MenuOpenHelper(AsyncHelper.getContext());
			db.dropTable();
			/*
			 * JSONObject json=new JSONObject(result); JSONObject
			 * dat=json.getJSONObject("Result"); JSONArray
			 * datArray=dat.getJSONArray(dat.toString());
			 */

			JSONObject json = new JSONObject(result);

			Log.i("Parse:", result);
			JSONArray datArray = json.getJSONArray("Resutlt");

			JSONObject json_data;
			for (int i = 0; i < datArray.length(); i++) {
				json_data = datArray.getJSONObject(i);
				String Id = json_data.getString(" ID");
				String Name = json_data.getString("Name");
				String Category = json_data.getString("Category");
				String Price = json_data.getString("Price");
				String Description = json_data.getString("Desctiption");
				String Modifiables = json_data.getString("Modifiables");
				String PicLocation = json_data.getString("PicLocation");
				String Visibility = json_data.getString("Visibility");

				menuHelper.setId(Id);
				menuHelper.setName(Name);
				menuHelper.setCategory(Category);
				menuHelper.setPrice(Price);
				menuHelper.setDescription(Description);
				menuHelper.setModifiables(Modifiables);
				menuHelper.setPicLocation(PicLocation);
				menuHelper.setVisibility(Visibility);
				// Toast.makeText(AsyncHelper.getContext(),
				// "adding items= "+Id+" "+Name+" "+Category,
				// Toast.LENGTH_SHORT).show();
				Log.i("DB_ADD:", Id + " " + Name + " " + Category + " " + Price
						+ " " + Description + " " + Modifiables + " "
						+ PicLocation + " " + Visibility);
				db.addItem(new menuHelper(Id, Name, Category, Price,
						Description, Modifiables, PicLocation, Visibility));

			}
			storePop();
			db.close();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void storePop() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet Httpget = new HttpGet("http://192.184.85.36/csce4444/android/pop.php");
			Httpget.addHeader("accept", "application/json");
			// Execute the request
			String Result;
			HttpResponse Response;
				Response = httpclient.execute(Httpget);
				// Examine the response status
				Log.i("Connect", Response.getStatusLine().toString());

				// Get hold of the response entity
				HttpEntity Entity = Response.getEntity();
				// If the response does not enclose an entity, there is no need
				// to worry about connection release

				if (Entity != null) {

					// A Simple JSON Response Read
					InputStream Instream = Entity.getContent();
					Result = convertStreamToString(Instream);
					Log.i("Results:", Result);

					//JSONObject Json = new JSONObject(Result);

					//String Title = Json.getString("Type");
					
					
				
			MenuOpenHelper db = new MenuOpenHelper(AsyncHelper.getContext());
			//db.dropTable();
			/*
			 * JSONObject json=new JSONObject(result); JSONObject
			 * dat=json.getJSONObject("Result"); JSONArray
			 * datArray=dat.getJSONArray(dat.toString());
			 */

			JSONObject json = new JSONObject(Result);

			Log.i("Parse:", Result);
			JSONArray datArray = json.getJSONArray("Resutlt");
			menuHelper pop;
			JSONObject json_data;
			for (int i = 0; i < datArray.length(); i++) {
				json_data = datArray.getJSONObject(i);
				String Name = json_data.getString("Name");
				Log.i("Item",Name);
				pop=db.getItem(Name);
				String Id =pop.getId();
				String Category = "PopularItems";
				String Price = pop.getPrice();
				String Description = pop.getDescription();
				String Modifiables = pop.getModifiables();
				String PicLocation = pop.getPicLocation();
				String Visibility = pop.getVisibility();

				menuHelper.setId(Id);
				menuHelper.setName(Name);
				menuHelper.setCategory(Category);
				menuHelper.setPrice(Price);
				menuHelper.setDescription(Description);
				menuHelper.setModifiables(Modifiables);
				menuHelper.setPicLocation(PicLocation);
				menuHelper.setVisibility(Visibility);
				// Toast.makeText(AsyncHelper.getContext(),
				// "adding items= "+Id+" "+Name+" "+Category,
				// Toast.LENGTH_SHORT).show();
				Log.i("DB_ADD:", Id + " " + Name + " " + Category + " " + Price
						+ " " + Description + " " + Modifiables + " "
						+ PicLocation + " " + Visibility);
				db.addItem(new menuHelper(Id, Name, Category, Price,
						PicLocation, Modifiables, Description, Visibility));

			}
			Instream.close();
			db.close();
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// do something
		} catch (IOException e) {
			// do something
		}

	}
	
	
	
	
	public static void storeOrder(String result) {
		try {

			orderOpenHelper db = new orderOpenHelper(AsyncHelper.getContext());
			db.dropTable();

			JSONObject json = new JSONObject(result);

			Log.i("Parse:", result);
			JSONArray datArray = json.getJSONArray("Resutlt");

			JSONObject json_data;
			for (int i = 0; i < datArray.length(); i++) {
				json_data = datArray.getJSONObject(i);
				String Id = json_data.getString(" TableId");
				String Name = json_data.getString("Title");
				String Mod = json_data.getString("Modifiables");
				double Price = Double.parseDouble(json_data.getString("Price"));
				String status = json_data.getString("Status");

				orderHelper.setTableId(Id);
				orderHelper.setTitle(Name);
				orderHelper.setMod(Mod);
				orderHelper.setPrice(Price);
				orderHelper.setStatus(status);
				Log.i("DB_ADD:", Id + " " + Name + " " + Mod + " " + Price
						+ " " + status);
				db.addItem(new orderHelper(Id, Name, Mod, Price, status));

			}

			db.close();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
	
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	
	
	
}
