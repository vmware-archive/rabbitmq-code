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
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		
		long startTime = 0;
		long endTime = 0;
		int nbMessages = 0;
		
		while(true){
		
			if(nbMessages == 0){
				startTime = getCurrentTime();
			}
			
			channel.basicPublish("quotations", "nasq", null, quotationService.next().getBytes());
			
			if(nbMessages == 10000){
				endTime = getCurrentTime();
				System.out.println("Time to send 10000 messages : " + (endTime - startTime) + " milliseconds.");
				nbMessages = 0;
			}else{
				nbMessages++;
			}
		}

		// no need to close connection since we're running in a infinite loop...
		//connection.close();
		
	}

	
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}

}
