package com.landa.helpers;

public class menuHelper {
	static String Id, Name, Category, Price, Description, Modfiables,
			PicLocation, Visibility;

	public menuHelper(String Id, String Name, String Category, String Price,
			String Description, String PicLoaction, String Modfiables,
			String Visibilty) {
		menuHelper.Id = Id;
		menuHelper.Name = Name;
		menuHelper.Category = Category;
		menuHelper.Price = Price;
		menuHelper.Description = Description;
		menuHelper.PicLocation = PicLoaction;
		menuHelper.Modfiables = Modfiables;
		menuHelper.Visibility = Visibilty;
	}

	public static void setId(String id) {
		menuHelper.Id = id;

	}

	public static void setName(String name) {
		menuHelper.Name = name;

	}

	public static void setCategory(String category) {
		menuHelper.Category = category;
	}

	public static void setDescription(String description) {
		menuHelper.Description = description;

	}

	public static void setPicLocation(String picLocation) {
		menuHelper.PicLocation = picLocation;

	}

	public static void setModifiables(String modifiables) {
		menuHelper.Modfiables = modifiables;

	}

	public static void setVisibility(String visibility) {
		menuHelper.Visibility = visibility;

	}

	public String getId() {
		return menuHelper.Id;

	}

	public String getName() {
		return menuHelper.Name;

	}

	public String getCategory() {
		return menuHelper.Category;

	}

	public String getDescription() {
		return menuHelper.Description;

	}

	public String getModifiables() {
		return menuHelper.Modfiables;

	}

	public String getVisibility() {
		return menuHelper.Visibility;

	}

	public String getPicLocation() {
		return menuHelper.PicLocation;
	}

	public static void setPrice(String price) {
		menuHelper.Price = price;
	}

	public String getPrice() {
		return menuHelper.Price;
	}

}
