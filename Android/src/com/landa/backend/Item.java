/**
 * 
 */
package com.landa.backend;

import java.net.URL;
import java.text.DecimalFormat;

/**
 * @author philip
 * 
 */

public class Item {
	private String name;
	private String desc;
	private URL imgURL;
	private double price; // production level I wouldn't use a double for this
	private boolean hasAlcohol;
	private String removedItems = "";
	private DecimalFormat dec;
	private boolean checkBox = true;
	public double subTotal;
	
	public static Item newInstance(Item i){
		return new Item(i.getName(),i.getDesc(),i.getPrice());

	}
	
	public Item(String name, String desc, double price) {
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.hasAlcohol = false;
		
	}	
	/*
	 * NOTE: To use imgURL I would probably look into this ImageView iv = new
	 * ImageView;
	 * 
	 * URL url = new URL(address); InputStream content =
	 * (InputStream)url.getContent(); Drawable d =
	 * Drawable.createFromStream(content , "src"); iv.setImageDrawable(d)
	 */

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
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
	 * @param hasAlochol
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
	public void setRemoved(String removed) {
		this.removedItems = removed;
	}

	public String getRemoved() {
		return this.removedItems;
	}

	public boolean getCheckbox() {
		return this.checkBox;
	}

	public void setCheckBox(boolean checkbox) {
		this.checkBox = checkbox;
	}
}