package com.landa.customer.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.landa.R;
import com.landa.backend.Item;

public class DrinkAdapter extends BaseAdapter {

	Context context;
	int layoutResourceId;
	private ArrayList<Item> listData;
	private LayoutInflater layoutInflater;

	public DrinkAdapter(Context context, ArrayList<Item> listData) {
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
			convertView = layoutInflater.inflate(R.layout.drink_order_list_row,
					parent, false);
			holder = new OrderHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.drinkName);
			holder.priceView = (TextView) convertView
					.findViewById(R.id.drinkprice);
			holder.descView = (TextView) convertView
					.findViewById(R.id.drinkDesc);
			convertView.setTag(holder);
		} else {
			holder = (OrderHolder) convertView.getTag();
		}
		Item item = listData.get(position);
		holder.nameView.setText(item.getName());
		holder.priceView.setText(item.getPriceFormatted());
		holder.descView.setText(item.getDesc());
		return convertView;
	}

	static class OrderHolder {
		TextView nameView;
		TextView priceView;
		TextView descView;
	}
}
