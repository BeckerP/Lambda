package com.landa.helpers;

import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Context;

public class AsyncHelper {

	static String url;
	static Context current;
	public static List<NameValuePair> data;
	static String name;
	static String search;
	static String Table;
	static String hold;
	static Activity activity;
	static String Data;

	public AsyncHelper() {
	}

	public AsyncHelper(String url, String name, Context current,
			List<NameValuePair> data, String search, String table,
			Activity activity,String hold,String Data) {
		AsyncHelper.url = url;
		AsyncHelper.current = current;
		AsyncHelper.data = data;
		AsyncHelper.name = name;
		AsyncHelper.search = search;
		AsyncHelper.Table = table;
		AsyncHelper.activity = activity;
		AsyncHelper.Data=Data;
		AsyncHelper.hold=hold;
	}

	public static String getURL() {

		return AsyncHelper.url;
	}

	public static void setURL(String temp) {
		AsyncHelper.url = temp;
	}

	public static String getHold() {

		return AsyncHelper.hold;
	}

	public static void setHold(String temp) {
		AsyncHelper.hold = temp;
	}
	
	public static Context getContext() {

		return AsyncHelper.current;
	}

	public static void setContext(Context temp) {
		AsyncHelper.current = temp;
	}

	public static void setVars(List<NameValuePair> data) {
		AsyncHelper.data = data;
	}

	public static void setName(String name) {
		AsyncHelper.name = name;

	}

	public static String getName() {

		return AsyncHelper.name;
	}

	public static void setSearch(String search) {
		AsyncHelper.search = search;
	}

	public static String getSearch() {
		return AsyncHelper.search;

	}

	public static void setTable(String tableNo) {
		AsyncHelper.Table = tableNo;

	}

	public static String getTable() {
		return AsyncHelper.Table;
	}

	public static void setActivity(Activity activity) {
		AsyncHelper.activity = activity;
	}

	public static Activity getActivity() {

		return AsyncHelper.activity;
	}
	
	public static String getData()
	{
		return AsyncHelper.Data;
		
	}

	public static void setData(String data) {
		AsyncHelper.Data=data;
		
	}
}