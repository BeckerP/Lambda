package com.landa.customer;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.landa.R;
import com.landa.backend.AsyncOrder_Payment;
import com.landa.backend.Item;
import com.landa.customer.Adapters.PaymentAdapter;
import com.landa.db.orderOpenHelper;
import com.landa.helpers.AsyncHelper;
import com.landa.waitStaff.GlobalVars;
import com.squareup.otto.Subscribe;

public class CustomerPaymentFragment extends Fragment implements
		OnCheckedChangeListener,OnClickListener,OnItemClickListener {
	public static ArrayList<Item> payment = new ArrayList<Item>();
	public static PaymentAdapter adapter;
	public static ListView paymentList;
	public static TextView subtotalValueView;
	public static TextView taxValueView;
	public static TextView totalValueView;
	public static RadioGroup tipPercent;
	public static CheckBox roundUp;
	public Button cashBtn;
	public Button creditBtn;
	String tableid;
	static final double TAX = .0825;
	static double tip = 1.0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Broadcast_Singleton.getEventBus().register(this);
		
		String tableNo = getActivity().getIntent().getStringExtra("tableNo");
		tableid=tableNo;
		orderOpenHelper db = new orderOpenHelper(AsyncHelper.getContext());
		int check=0;
		for(int x=0;x<db.tableOrder_Checkout(Integer.parseInt(tableNo)).size();++x)//checks how many of the order are delivered
		{
			if(Integer.parseInt(db.tableOrder(Integer.parseInt(tableNo)).get(x).getStatus())==3)
			{
				++check;
			}
		}
		if(db.tableOrder_Checkout(Integer.parseInt(tableNo)).size()==check)//if all order are delevired 
		{
		for(int x=0;x<db.tableOrder_Checkout(Integer.parseInt(tableNo)).size();++x)//then display orders. 
		{
			
			Log.i("Checkout: ",String.valueOf((db.tableOrder_Checkout(Integer.parseInt(tableNo)).get(x).getPrice())));
			payment.add(db.tableOrder_Checkout(Integer.parseInt(tableNo)).get(x));
		}
		}
		db.close();
	}

	public static void update() {
		adapter = (PaymentAdapter) paymentList.getAdapter();
		adapter.notifyDataSetChanged();
		subtotalValueView.setText(computeSubtotalFormatted(computeSubtotal()));
		totalValueView.setText(computeTotalFormatted(computeTotal()));

	}

	public static double computeSubtotal() {
		Item item;
		double subtotal = 0;
		for (int i = 0; i < payment.size(); i++) {
			item = payment.get(i);
			if (item.getCheckbox() == true)
				subtotal += item.getPrice();
		}
		subtotal = subtotal * tip;
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
		
		if(roundUp.isChecked())
				total = Math.ceil(total);
		return total;
	}

	public static String computeTotalFormatted(double total) {
		DecimalFormat dec = new DecimalFormat("$#.00");
		dec.setRoundingMode(RoundingMode.HALF_UP);
		return dec.format(total);
	}
	
	public void toggleCheck(Item item) {
		boolean checkbox;
		
		adapter = (PaymentAdapter) paymentList.getAdapter();
		checkbox = item.getCheckbox();
		item.setCheckBox(!checkbox);
		payment.indexOf(item);
		update();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// This is my fragments xml file
		View v = inflater.inflate(R.layout.customer_payment_fragment,
				container, false);

		paymentList = (ListView) v.findViewById(R.id.paymentList);
		PaymentAdapter adapter = new PaymentAdapter(getActivity(), payment); // Create adapter

		paymentList.setAdapter(adapter); // Bind adapter to the arrayList
		paymentList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
				//Toggle checkbox
				toggleCheck(payment.get(index));
				
			}
		});
		
		
		
		// Bind the textviews.
		subtotalValueView = (TextView) v.findViewById(R.id.subtotalValue);
		taxValueView = (TextView) v.findViewById(R.id.taxValue);
		totalValueView = (TextView) v.findViewById(R.id.totalValue);

		// Bind the radiobutton group
		tipPercent = (RadioGroup) v.findViewById(R.id.tipPercent);
		tipPercent.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// Check which radio button was clicked
				switch (checkedId) {
				case R.id.tip0:
					tip = 1.0;
					break;
				case R.id.tip10:
					tip = 1.10;
					break;
				case R.id.tip15:
					tip = 1.15;
					break;
				case R.id.tip20:
					tip = 1.20;
					break;
				}
				update();
			}
		});

		// Bind the round up checkbox
		roundUp = (CheckBox) v.findViewById(R.id.chkRoundUp);
		roundUp.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener(){
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		            update();
			}
		});

		// Bind the submit button.
		cashBtn = (Button) v.findViewById(R.id.cash);
		cashBtn.setOnClickListener(this);
		
		creditBtn = (Button) v.findViewById(R.id.credit);
		creditBtn.setOnClickListener(this);
		
		update();
		return v;
	}

	@Override
	public void onClick(View v) {
	         switch(v.getId()){
	             case R.id.credit:
	     			FragmentTransaction ft = getChildFragmentManager()
	    			.beginTransaction();
	    			CreditCardDialog fg = new CreditCardDialog().newInstance(computeTotal());
	    			fg.show(ft, "creditText");
	             break;
	             case R.id.cash:
	         		AsyncHelper.setURL("http://192.184.85.36/csce4444/android/change.php");// set
	        		AsyncHelper.setName(tableid);
	        		AsyncHelper.setSearch(String.valueOf(computeTotal()-computeSubtotal()));//setting the tax
	        		AsyncHelper.setTable(String.valueOf(computeTotal()));//setting total;
	        		AsyncHelper.setData(String.valueOf(tip));
	        	    AsyncOrder_Payment task = new AsyncOrder_Payment(getActivity());
	        		task.execute();
	        		
	        		Toast.makeText(getActivity(),
	        				"Thank you for dining with us today.",
	        				Toast.LENGTH_LONG).show();
	        		GlobalVars.time_of_pay = new Date();
	        		removePaidItems();
	             break;
	         }
	   }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	public static void removePaidItems(){
		Item item;
		
		for (int i = 0; i < payment.size(); i++) {
			item = payment.get(i);
			if (item.getCheckbox() == true)
				payment.remove(i);
		}
		update();
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
	}
	   @Subscribe
	    public void HandleRecieveFromDialogEvent(CreditCardEvent event){
		   if(event.success == true){
        		AsyncHelper.setURL("http://192.184.85.36/csce4444/android/change.php");// set
        		AsyncHelper.setName(tableid);
        		AsyncHelper.setSearch(String.valueOf(computeTotal()-computeSubtotal()));//setting the tax
        		AsyncHelper.setTable(String.valueOf(computeTotal()));//setting total;
        		AsyncHelper.setData(String.valueOf(tip));
        		AsyncOrder_Payment task = new AsyncOrder_Payment(getActivity());
//        		task.execute();
       		
       		Toast.makeText(getActivity(),
       				"Thank you for dining with us today.",
       				Toast.LENGTH_LONG).show();
       		GlobalVars.time_of_pay = new Date();
       		removePaidItems();
		   }
	    }
}
