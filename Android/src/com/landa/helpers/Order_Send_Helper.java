package com.landa.helpers;

import java.util.ArrayList;

import com.landa.backend.Item;

public class Order_Send_Helper {
	public static int Count;
	public static String Values;
	public static ArrayList<Item> Data;
	static String Num;

	public Order_Send_Helper(String Values, Item Data, String Num) {
		Order_Send_Helper.Values = Values;
		Order_Send_Helper.Data.add(Data);
		Order_Send_Helper.Num = Num;
	}

	public void setValue(String Values) {
		Order_Send_Helper.Values = Values;
	}

	public static void setData(Item item) {
		Data.add(item);
	}

	public void setNum(String Num) {
		Order_Send_Helper.Num = Num;
	}

	public static String getNum() {
		return Order_Send_Helper.Num;
	}

	public static Item getData(int num) {
		return Order_Send_Helper.Data.get(num);
	}

	public static String getValues() {
		return Order_Send_Helper.Values;
	}

	public static void setCount() {
		Order_Send_Helper.Count += 1;
	}

	public static int getCount() {
		Order_Send_Helper.Count--;
		return Order_Send_Helper.Count;
	}

	public static void remove(Item item) {
		Order_Send_Helper.Data.remove(item);

	}

}
