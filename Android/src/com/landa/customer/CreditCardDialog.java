package com.landa.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.landa.R;

public class CreditCardDialog extends DialogFragment implements TextWatcher {
	public EditText credit;
	public TextView error;
	public TextView creditlbl;
	public String message;
	public double total;
	public boolean success = false;
	public Button btnPositive;

	public static CreditCardDialog newInstance(double x) {
		CreditCardDialog f = new CreditCardDialog();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putDouble("total", x);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.credit_card_dialog, null));
		total = getArguments().getDouble("total");

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

		// Add action buttons
		builder.setMessage("Credit Card Payment");
		builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// I would pass in the total in a textview right above

				creditlbl = (TextView) getDialog().findViewById(R.id.textCredit);
				creditlbl.setText("Total:" + total);
				credit = (EditText) getDialog().findViewById(R.id.editCredit);
				success = true;
				credit.addTextChangedListener(CreditCardDialog.this);
				
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				credit = (EditText) getDialog().findViewById(R.id.editCredit);
				CreditCardDialog.this.getDialog().cancel();
			}
		});
		return builder.create();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Broadcast_Singleton.getEventBus().post(new CreditCardEvent(success));

		// Send my result toFragment
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		Broadcast_Singleton.getEventBus().register(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		Broadcast_Singleton.getEventBus().unregister(this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void afterTextChanged(Editable s) {
		credit.setText(s.toString());
		
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

}