package com.landa.customer;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.landa.R;
import com.landa.waitStaff.GlobalVars;

public class CustomerSelectionFragment extends Fragment implements
		OnClickListener {
	OnCustomerClickListener callBack;

	public interface OnCustomerClickListener {
		public void onCustomerClick(View v);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//GlobalVars.time_of_create = new Date(); //Table has been selected, reset time of create(arrival)
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			callBack = (OnCustomerClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onCustomerClickListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.customer_selection_fragment,
				container, false);

		Button btnDrink = (Button) v.findViewById(R.id.Drink);
		btnDrink.setOnClickListener(this);

		Button btnMenu = (Button) v.findViewById(R.id.Menu);
		btnMenu.setOnClickListener(this);

		Button btnHelp = (Button) v.findViewById(R.id.Help);
		btnHelp.setOnClickListener(this);

		Button btnGame = (Button) v.findViewById(R.id.Game);
		btnGame.setOnClickListener(this);

		Button btnChat = (Button) v.findViewById(R.id.Chat);
		btnChat.setOnClickListener(this);

		Button btnPay = (Button) v.findViewById(R.id.Pay);
		btnPay.setOnClickListener(this);

		return v;
	}

	public void onClick(View v) {
		callBack.onCustomerClick(v);
	}
}