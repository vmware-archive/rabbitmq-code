/**
 * 
 */
package com.gopivotal.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 *
 */
public class QuotationSender {
	
	private static final QuotationService quotationService = new QuotationService();

	public static void main(String [] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		// TODO durable, a boolean argument, is the last argument of exchangeDeclare()
		channel.exchangeDeclare("quotations", "fanout", false);
		// TODO durable, a boolean argument, is the second argument of queueDeclare()
		channel.queueDeclare("quotations", false, false, false, null);
		channel.queueBind("quotations","quotations", "");
		
		while(true) {
			letsWait();
			String quotation = quotationService.next();
			// TODO set message properties to persistent using a property from MessageProperties
			channel.basicPublish("quotations", "nasq", null, quotation.getBytes());
		}
	}

	private static void letsWait() throws Exception {
		Thread.sleep(1000);		
	}
	
}
