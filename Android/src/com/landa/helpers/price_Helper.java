package com.landa.helpers;

public class price_Helper {
	static Double subTotal;
	static Double Tax;
	static Double Total;

	public price_Helper(Double sub, Double tax, Double total) {
		price_Helper.subTotal = sub;
		price_Helper.Tax = tax;
		price_Helper.Total = total;
	}

	public static void setSubTotal(Double sub) {
		price_Helper.subTotal += sub;
	}

	public static void setTax() {
		price_Helper.Tax = price_Helper.subTotal * .0825;
	}

	public static void setTotal() {
		price_Helper.Total = price_Helper.subTotal + price_Helper.Tax;
	}

	public static Double getSubTotal() {
		return price_Helper.subTotal;
	}

	public static Double getTax() {
		return price_Helper.Tax;
	}

	public static Double getTotal() {
		return price_Helper.Total;
	}

}
