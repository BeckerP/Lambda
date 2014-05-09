package com.landa.customer.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.landa.R;
import com.landa.backend.Item;

public class PaymentAdapter extends BaseAdapter {

	Context context;
	int layoutResourceId;
	private ArrayList<Item> listData; // Once we get json working be a REALLY
										// good idea to actually use the generic
										// features of this

	private LayoutInflater layoutInflater;

	public PaymentAdapter(Context context, ArrayList<Item> listData) {
		this.context = context;
		this.listData = listData;
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		OrderHolder holder;
		if (convertView == null) {
			layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.customer_payment_row,
					parent, false);

			holder = new OrderHolder();
			holder.nameView = (TextView) convertView.findViewById(R.id.paymentItemName);
			holder.priceView = (TextView) convertView.findViewById(R.id.paymentPrice);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			convertView.setTag(holder);
		} else {
			holder = (OrderHolder) convertView.getTag();
		}
		Item item = listData.get(position);
		holder.nameView.setText(item.getName());
		holder.priceView.setText(item.getPriceFormatted());
		holder.checkbox.setChecked(item.getCheckbox());
		return convertView;
	}

	public class OrderHolder {
		TextView nameView;
		TextView priceView;
		CheckBox checkbox;
	}

}
