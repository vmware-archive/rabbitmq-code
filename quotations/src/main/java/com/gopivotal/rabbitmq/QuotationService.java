/**
 * 
 */
package com.gopivotal.rabbitmq;

import java.util.Date;

/**
 *
 */
public class QuotationService {

	private double current = 80.63;
	
	private String symbol = "PVTL";
	
	public String next() {
		return nextDetailed().toSimpleString();
	}
	
	public Quotation nextDetailed() {
		// VMware stocks are skyrocketting! BUY!!!
		current += Math.random() - 0.4;
		Quotation quotation = new Quotation(symbol,current,new Date());
		return quotation;		
	}
	
}
