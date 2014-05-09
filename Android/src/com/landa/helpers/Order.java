/**
 * 
 */
package com.landa.helpers;

import java.net.URL;
import java.text.DecimalFormat;

/**
 * @author philip
 * 
 */

public class Order {
	private String name;
	private URL imgURL;
	private String Mod;
	private Double price; // production level I wouldn't use a double for this
	private String modifiable[];
	private String allergens[];
	private boolean hasAlcohol;
	private String ingredients[];
	private String removedItems[] = { "" };
	private String notes;
	private String status;
	private DecimalFormat dec;
	public double subTotal;
	private String TableId;

	public Order(String TableId, String name, String Mod, Double price,
			String status) {
		this.name = name;
		this.TableId = TableId;
		this.Mod = Mod;
		this.price = price;
		this.status = status;
	}

	/*
	 * NOTE: To use imgURL I would probably look into this ImageView iv = new
	 * ImageView;
	 * 
	 * URL url = new URL(address); InputStream content =
	 * (InputStream)url.getContent(); Drawable d =
	 * Drawable.createFromStream(content , "src"); iv.setImageDrawable(d)
	 */
	public String getMod() {
		return Mod;
	}

	public void setMod(String mod) {
		this.Mod = mod;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the desc
	 */
	public String getTableId() {
		return TableId;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setTableId(String id) {
		this.TableId = id;
	}

	/**
	 * @return the imgURL
	 */
	public URL getImgURL() {
		return imgURL;
	}

	/**
	 * @param imgURL
	 *            the imgURL to set
	 */
	public void setImgURL(URL imgURL) {
		this.imgURL = imgURL;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/*
	 * 
	 * @param format the format to display the price as, ex: "#.00 EUR" # are
	 * ignored if zero and .00 is appended if null"
	 */
	public String getPriceFormatted(String format) {
		dec = new DecimalFormat(format);
		return dec.format(price);
	}

	/**
	 * 
	 * Convenience method to format price, do not use for any mathematical
	 * operators!
	 * 
	 * @return returns a formatted price in US currency.
	 */
	public String getPriceFormatted() {
		if (Math.round(price) == price)
			dec = new DecimalFormat("$#");
		else
			dec = new DecimalFormat("$#.00");
		return dec.format(price);
	}

	/**
	 * @return the modifiable
	 */
	public String[] getModifiable() {
		return modifiable;
	}

	/**
	 * @param modifiable
	 *            the modifiable to set
	 */
	public void setModifiable(String[] modifiable) {
		this.modifiable = modifiable;
	}

	/**
	 * @return the allergens
	 */
	public String[] getAllergens() {
		return allergens;
	}

	/**
	 * @param allergens
	 *            the allergens to set
	 */
	public void setAllergens(String[] allergens) {
		this.allergens = allergens;
	}

	/**
	 * @return the hasAlcohol
	 */
	public boolean isHasAlcohol() {
		return hasAlcohol;
	}

	/**
	 * @param hasAlcohol
	 *            the hasAlcohol to set
	 */
	public void setHasAlcohol(boolean hasAlcohol) {
		this.hasAlcohol = hasAlcohol;
	}

	/**
	 * @return the ingredients
	 */
	public String[] getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients
	 *            the ingredients to set
	 */
	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String[] getRemoved() {
		return this.removedItems;
	}

}