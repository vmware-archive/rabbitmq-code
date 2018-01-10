/**
 * 
 */
package com.gopivotal.rabbitmq.rpc;


/**
 *
 */
public class QuotationOfferDecider {
	
	private static final String QUEUE = "decider";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO connect to the broker and create a channel
		// declare the channel as final to be able to refer to it in the Consumer
		
		// TODO declare an "offers" fanout
		
		// TODO declare the queue and purge it to make sure it's empty at the beginning
		// use the QUEUE property for the name of the queue
		
		// TODO bind the queue to the "offers" exchange
		
		// TODO subscribe a DefaultConsumer to the queue
		// in the handleDelivery method, generate the response payload like this:
		// String response = Math.random() > 0.5 ? "yes" : "no";
		// then send a message to the reply
		// hint: use the default  exchange ("") and the reply queue name for the routing key 
		
		
		System.out.println("server is waiting for quotations...");
		
	}

	private static void letsWait() {
		try {
			Thread.sleep((long) (Math.random()*4.00*1000.0));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
}
