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

public class OrderAdapter extends BaseAdapter {

	Context context;
	int layoutResourceId;
	private ArrayList<Item> listData; // Once we get json working be a REALLY
										// good idea to actually use the generic
										// features of this

	private LayoutInflater layoutInflater;

	public OrderAdapter(Context context, ArrayList<Item> listData) {
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
			convertView = layoutInflater.inflate(R.layout.order_list_row,
					parent, false);
			layoutInflater = LayoutInflater.from(context); // Problem code
			convertView = layoutInflater.inflate(R.layout.order_list_row,
					parent, false);

			holder = new OrderHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.itemName);
			holder.priceView = (TextView) convertView.findViewById(R.id.price);
			holder.removedItemsView = (TextView) convertView
					.findViewById(R.id.removedItems);
			convertView.setTag(holder);
		} else {
			holder = (OrderHolder) convertView.getTag();
		}
		Item item = listData.get(position);
		holder.nameView.setText(item.getName());
		holder.priceView.setText(item.getPriceFormatted());
		holder.removedItemsView.setText(item.getRemoved());

		return convertView;
	}

	public class OrderHolder {
		TextView nameView;
		TextView priceView;
		TextView removedItemsView;
	}

}
