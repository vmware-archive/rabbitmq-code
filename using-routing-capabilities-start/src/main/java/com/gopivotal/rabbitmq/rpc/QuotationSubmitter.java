/**
 * 
 */
package com.gopivotal.rabbitmq.rpc;

import com.gopivotal.rabbitmq.QuotationService;

/**
 *
 */
public class QuotationSubmitter {
	
	private static final QuotationService quotationService = new QuotationService();
	
	// TODO increase the timeout to 5 seconds if necessary
	private static final long TIMEOUT = 2000L;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO connect to the broker and create a channel
		
		// TODO declare an "offers" fanout
		
		// TODO declare the reply queue and get its name (hint: channel.queueDeclare().getQueue() )
		
		// TODO create BasicProperties and set the reply queue
				
		String quotation = quotationService.next();
		// TODO publish the quotation
		
		System.out.println("published quotation, waiting for response...");
				
		// TODO wait for the response in a while loop, use channel.basicGet
		
		// TODO display the response
		
		// TODO close the channel and the connection
	}

}
