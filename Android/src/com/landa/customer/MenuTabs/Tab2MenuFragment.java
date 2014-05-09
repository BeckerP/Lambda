package com.landa.customer.MenuTabs;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.landa.R;
import com.landa.backend.Item;
import com.landa.customer.Adapters.DrinkAdapter;
import com.landa.customer.DrinkTabs.Tab1DrinkFragment.OnTab1DrinkListener;
import com.landa.db.MenuOpenHelper;
import com.landa.helpers.AsyncHelper;

public class Tab2MenuFragment extends Fragment implements OnItemClickListener {

	OnTab1DrinkListener callBack;
	static DrinkAdapter adapter;
	static ArrayList<Item> menu;

	public interface OnTab1MenuListener {
		public void onDataPass(Item item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Add array in this
		// sdb.getCategory("Drink"); //will return all items in that category.
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		super.onCreateView(inflater, container, savedInstanceState);
		MenuOpenHelper db = new MenuOpenHelper(AsyncHelper.getContext());

		View v = inflater.inflate(R.layout.tab_view, container, false);

		menu = db.getCategory("Salads");
		adapter = new DrinkAdapter(getActivity(), menu); // Problem adapter

		ListView listView = (ListView) v.findViewById(R.id.drinkList); // Problem
																		// code
		listView.setAdapter(adapter);
		db.close();
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		final ListView listView = (ListView) view.findViewById(R.id.drinkList); // Problem
																				// code
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Get items position in ArrayList
				// Get array
				callBack.onDataPass((Item) parent.getItemAtPosition(position));
			}
		});

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			callBack = (OnTab1DrinkListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO I have no idea why I needt his since I implement up above and it
		// works from what I can tell

	}

}
