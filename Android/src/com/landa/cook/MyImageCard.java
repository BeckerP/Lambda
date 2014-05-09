package com.landa.cook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;
import com.landa.R;

public class MyImageCard extends Card {
	public MyImageCard(String title, int image) {
		// super(title, image);
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.cook_card_picture, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		// ((ImageView)
		// view.findViewById(R.id.imageView1)).setImageResource(image);
		return view;
	}

	public void cardswipeactions() {
		// TODO Auto-generated method stub
		
	}
}
