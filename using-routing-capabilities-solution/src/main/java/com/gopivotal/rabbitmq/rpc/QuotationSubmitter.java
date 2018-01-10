/**
 * 
 */
package com.gopivotal.rabbitmq.rpc;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 *
 */
public class QuotationSubmitter {
	
	private static final QuotationService quotationService = new QuotationService();
	
	private static final long TIMEOUT = 2000L;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare("offers","fanout",true);
		
		String replyQueueName = channel.queueDeclare().getQueue();
		
		BasicProperties props = new BasicProperties.Builder().replyTo(replyQueueName).build();
		
		String quotation = quotationService.next();
		channel.basicPublish("offers","",props,quotation.getBytes());
		
		System.out.println("published quotation, waiting for response...");
		
		long waitUpTo = System.currentTimeMillis()+TIMEOUT;
		GetResponse getResponse = null;
		while(getResponse == null && System.currentTimeMillis() < waitUpTo) {
			getResponse = channel.basicGet(replyQueueName, true);			
		}
		
		if(getResponse == null) {
			System.out.println("no response in "+TIMEOUT+" ms, can't wait more!");
		} else {
			String response = new String(getResponse.getBody());
			System.out.println("response arrived, should we buy "+quotation+"? "+response);		
		}
		
		channel.close();
		connection.close();
	}

}
