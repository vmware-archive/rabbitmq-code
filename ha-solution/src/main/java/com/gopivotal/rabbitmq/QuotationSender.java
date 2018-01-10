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
public class QuotationSender {
	
	private static final QuotationService quotationService = new QuotationService();

	public static void main(String [] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5674);
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		while(true) {
			letsWait();
			String quotation = quotationService.next();
			channel.basicPublish("quotations", "", null, quotation.getBytes());
		}
	}

	private static void letsWait() throws Exception {
		Thread.sleep(1000);		
	}
	
}
