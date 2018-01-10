/**
 * 
 */
package com.gopivotal.rabbitmq.rpc;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 *
 */
public class QuotationOfferDecider {
	
	private static final String QUEUE = "decider";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		final Channel channel = connection.createChannel();		
		channel.exchangeDeclare("offers","fanout",true);
		
		channel.queueDeclare(QUEUE, false, false, false,null);
		channel.queuePurge(QUEUE);	
		
		channel.queueBind(QUEUE, "offers", "");
		
		channel.basicConsume(QUEUE, true,new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				letsWait();
				String response = Math.random() > 0.5 ? "yes" : "no";
				channel.basicPublish("", properties.getReplyTo(), null, response.getBytes());
			}
			
		});
		
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
