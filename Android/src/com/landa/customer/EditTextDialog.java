package com.landa.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.landa.R;
import com.landa.backend.Item;

public class EditTextDialog extends DialogFragment implements TextWatcher {
	public EditText edit;
	public Item item;
	public int index;
	public String message;
	String title;
	
	public static EditTextDialog newInstance(String str) {
		EditTextDialog f = new EditTextDialog();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString("title", str);
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
		

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		
		builder.setView(inflater.inflate(R.layout.edit_text_dialog, null));
		title = getArguments().getString("title");
		
		
		// Add action buttons
		builder.setMessage("Add notes below");
		builder.setPositiveButton("Enter",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						edit = (EditText) getDialog().findViewById(R.id.editModifiables);
						edit.addTextChangedListener(EditTextDialog.this);		
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
				@Override
					public void onClick(DialogInterface dialog, int id) {
						edit = (EditText) getDialog().findViewById(R.id.editModifiables);
						edit.setText(title); //This is deleting everything.
						EditTextDialog.this.getDialog().cancel();
					}
				});
		return builder.create();
	}
	
		@Override
		public void onDestroy (){
			super.onDestroy();
			Broadcast_Singleton.getEventBus().post(new ReceiveFromDialogEvent(edit.getText().toString()));
			
			//Send my result toFragment
		}
	
		@Override
		public void onStart()
		{
			super.onStart();
		}
		
		@Override
		public void onResume(){
			super.onResume();
			Broadcast_Singleton.getEventBus().register(this);
		}
		
		@Override
		public void onPause(){
			super.onPause();
			Broadcast_Singleton.getEventBus().unregister(this);
		}
	
		
		@Override
		public void onDetach(){
			super.onDetach();
		}
		
        @Override
        public void afterTextChanged(Editable s) {
        	edit.setText(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        
}