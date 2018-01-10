/**
 * 
 */
package com.gopivotal.rabbitmq;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 */
public class BrokerFlooder {

	public static void main(String[] args) throws Exception {

		QuotationService quotationService = new QuotationService();
		
		ConnectionFactory factory = new ConnectionFactory();
		
		// TODO write code to connect to the broker
		
		long startTime = 0;
		long endTime = 0;
		
		// TODO change this code to loop infinitely on the basicPublish method
		
		startTime = getCurrentTime();
		
			// TODO uncomment this
			//channel.basicPublish("quotations", "nasq", null, quotationService.next().getBytes());
			
			// TODO change this code to print the time elapsed between 10000 messages
				endTime = getCurrentTime();
		System.out.println("Time to send 1 message : " + (endTime - startTime) + " milliseconds.");
		
		// no need to close connection since we're running in a infinite loop...
		//connection.close();

	}
	
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}

}
