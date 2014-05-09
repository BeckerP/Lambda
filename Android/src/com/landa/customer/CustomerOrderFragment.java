package com.landa.customer;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.landa.R;
import com.landa.backend.Async_orderSender;
import com.landa.backend.Item;
import com.landa.customer.Adapters.OrderAdapter;
import com.landa.helpers.AsyncHelper;
import com.squareup.otto.Subscribe;

public class CustomerOrderFragment extends Fragment{
	public static ArrayList<Item> order = new ArrayList<Item>();
	public static OrderAdapter adapter;
	public Item item;
	static ListView orderList;
	public static TextView subtotalValueView;
	static TextView taxValueView;
	public static TextView totalValueView;
	Button submitBtn;
	static final double TAX = .0825;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Broadcast_Singleton.getEventBus().register(this);
	}

	public void use(Item tmpItem) { // Gets the Item that was clicked in
									// Tab1Fragment and puts it in here.
		Item item = Item.newInstance(tmpItem);
		//item.Copy(tmpItem); //Work on this later.
		adapter = (OrderAdapter) orderList.getAdapter();
		
		order.add(item);
		subtotalValueView.setText(computeSubtotalFormatted(computeSubtotal()));
		totalValueView.setText(computeTotalFormatted(computeTotal()));

		adapter.notifyDataSetChanged();
	}

	public static void update() {
		adapter.notifyDataSetChanged(); 
		subtotalValueView.setText(computeSubtotalFormatted(computeSubtotal()));
		totalValueView.setText(computeTotalFormatted(computeTotal()));
	}
//Use update; not refresh
	public void refresh() {

		adapter.notifyDataSetChanged();
	}

	public static double computeSubtotal() {
		Item item;
		double subtotal = 0;
		for (int i = 0; i < order.size(); i++) {
			item = order.get(i);
			subtotal += item.getPrice();
		}
		return subtotal;
	}

	public static String computeSubtotalFormatted(double subtotal) {
		DecimalFormat dec = new DecimalFormat("$#.00");
		dec.setRoundingMode(RoundingMode.HALF_UP);

		return dec.format(subtotal);
	}

	public static double computeTotal() {
		double subtotal, total;
		subtotal = computeSubtotal();
		total = subtotal * (1 + TAX);
		return total;
	}

	public static String computeTotalFormatted(double total) {
		DecimalFormat dec = new DecimalFormat("$#.00");
		dec.setRoundingMode(RoundingMode.HALF_UP);

		return dec.format(total);
	}

	/**
	 * Deletes the one of specified item in the order
	 * <p>
	 * Not based on index but on the first occurrence of the item. May not
	 * delete anything if item is not found.
	 * 
	 * @param item
	 *            Pass the item you wish to remove.
	 */
	public void delete(Item item) {
		adapter = (OrderAdapter) orderList.getAdapter();
		order.remove(item);
		update();
	}

	/**
	 * Modifies the specified item in the listView and orderArray
	 * 
	 * @param str
	 *            string you want to add
	 * @param item
	 *            item you wish to swap out of this location
	 */
//	public void modify(Item item, String str) {
//		item.setRemoved(str);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// This is my fragments xml file
		View v = inflater.inflate(R.layout.customer_order_fragment, container,
				false);

		orderList = (ListView) v.findViewById(R.id.orderList); // this is the
																// listviews id
																// IN my
																// fragments xml
																// file
		OrderAdapter adapter = new OrderAdapter(getActivity(), order); // Create
																		// adapter

		registerForContextMenu(orderList); // bind context menu with arraylist
		orderList.setAdapter(adapter); // Bind adapter to the arrayList

		// Bind the textviews.
		subtotalValueView = (TextView) v.findViewById(R.id.subtotalValue);
		taxValueView = (TextView) v.findViewById(R.id.taxValue);
		totalValueView = (TextView) v.findViewById(R.id.totalValue);

		subtotalValueView.setText(computeSubtotalFormatted(computeSubtotal()));
		totalValueView.setText(computeTotalFormatted(computeTotal()));
		
		// Bind the submit button.
		submitBtn = (Button) v.findViewById(R.id.submit);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});
		return v;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int index = (int) info.id;
		menu.setHeaderTitle(order.get(index).getName());

		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.order_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem mItem) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) mItem
				.getMenuInfo();
		int index = (int) info.id;
		item = order.get(index);
		
		String removedText = "";
		if(!item.getRemoved().isEmpty())
			removedText = item.getRemoved();
		

		switch (mItem.getItemId()) {
		case R.id.edit:
			FragmentTransaction ft = getChildFragmentManager()
			.beginTransaction();
			EditTextDialog fg = new EditTextDialog().newInstance(removedText);
			fg.show(ft, "editText");
			//ft.commit();
			return true;
		case R.id.delete:
			delete(item);
			return true;
		default:
			return super.onContextItemSelected(mItem);
		}
	
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		final ListView listView = (ListView) view.findViewById(R.id.orderList); // Problem
																				// code
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Get items position in ArrayList
				// Remove item from array & Refresh the arraylist
				// delete((Item)parent.getItemAtPosition(position));
				listView.showContextMenuForChild(view);
				
				subtotalValueView.setText(computeSubtotalFormatted(computeSubtotal()));
				totalValueView.setText(computeTotalFormatted(computeTotal()));
			}
		});

		// This is where we will add code to shoot the submit button over to the
		// database
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Start


				AsyncHelper
						.setURL("http://192.184.85.36/csce4444/android/add.php");// set
																					// proper
																					// url
				Async_orderSender task = new Async_orderSender(getActivity());
				/*
				 * for(int x=0;x<order.size();x++) {
				 * Order_Send_Helper.setData(order.get(x)); }
				 */
				task.execute();
				// End
				// order.clear(); //Clears the order after submit. //Don't add
				// anything pass this line
				// subtotalValueView.setText(Double.toString(computeSubtotal()));
				// totalValueView.setText(computeTotalFormatted(computeTotal()));
				// adapter.notifyDataSetChanged(); //Do this last

				// Toast to say stuff got; may want to include this in a
				// try/catch or if/else
				Toast.makeText(getActivity(), "Order has been dispatched.",
						Toast.LENGTH_LONG).show();

				// Stare at Android, Android stares back at you.
			} 
		});
		
	}
	
	   @Subscribe
	    public void HandleRecieveFromDialogEvent(ReceiveFromDialogEvent event){
		   adapter = (OrderAdapter) orderList.getAdapter();
		   
		   //Need to get the item index
		 //order.get(event.index).setRemoved(event.message);
		   item.setRemoved(event.message);
		   adapter.notifyDataSetChanged();
	    }
}