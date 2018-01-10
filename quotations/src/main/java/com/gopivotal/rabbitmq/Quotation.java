/**
 * 
 */
package com.gopivotal.rabbitmq;

import java.util.Date;

/**
 *
 */
public class Quotation {
	
	private final String stock;
	
	private final double value;

	private final Date date;

	public Quotation(String stock, double value, Date date) {
		super();
		this.stock = stock;
		this.value = value;
		this.date = date;
	}

	public String getStock() {
		return stock;
	}

	public double getValue() {
		return value;
	}

	public Date getDate() {
		return date;
	}

	public String toSimpleString() {
		return stock+","+value;
	}
	
	public String toString() {
		return stock+","+value+","+date.getTime();
	}
	
	public static Quotation read(String detailedString) {
		String [] split = detailedString.split(",");
		return new Quotation(split[0],Double.parseDouble(split[1]),new Date(Long.parseLong(split[2])));
	}
	
}
