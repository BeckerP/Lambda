package com.landa.cook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;
import com.landa.R;

public class MyCardDesc extends Card {
	public MyCardDesc(String title, String desc) {
		super(title, desc);
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.cook_card_ex,
				null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.description)).setText(desc);

		return view;
	}

	
	public void cardswipeactions() {
		// TODO Auto-generated method stub
		
	}

}
