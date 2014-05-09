package com.landa.helpers;

public class orderHelper {
	static String TableId, Title, Mod;
	static Double Price;
	static String Status;

	public orderHelper(String TableId, String Title, String Mod, Double Price,
			String Status) {
		orderHelper.TableId = TableId;
		orderHelper.Title = Title;
		orderHelper.Mod = Mod;
		orderHelper.Price = Price;
		orderHelper.Status = Status;
	}

	public static void setTitle(String title) {
		orderHelper.Title = title;

	}

	public static void setMod(String mod) {
		orderHelper.Mod = mod;

	}

	public static void setPrice(Double price) {
		orderHelper.Price = price;

	}

	public String getTableId() {

		return orderHelper.TableId;
	}

	public String getTitle() {

		return orderHelper.Title;
	}

	public String getMod() {

		return orderHelper.Mod;
	}

	public Double getPrice() {

		return orderHelper.Price;
	}

	public static void setTableId(String tableId) {
		orderHelper.TableId = tableId;

	}

	public static void setStatus(String status) {
		orderHelper.Status = status;
	}

	public String getStatus() {

		return orderHelper.Status;
	}

}
