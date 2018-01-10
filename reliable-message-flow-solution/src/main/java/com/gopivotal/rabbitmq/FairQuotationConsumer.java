/**
 * 
 */
package com.gopivotal.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 
 */
public class FairQuotationConsumer {
	
	private static final String QUEUE = "quotations";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();

		final Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE,false,false,false,null);
		
		channel.basicQos(1);
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag,
					Envelope envelope, BasicProperties properties,
					byte[] body) throws IOException {

				System.out.println("received message " + envelope.getDeliveryTag() );
				processing();
				System.out.println("processed message "	+ envelope.getDeliveryTag());
				channel.basicAck(envelope.getDeliveryTag(), false);
			}

		};

		channel.basicConsume(QUEUE, false, consumer);
		
		System.out.println("Fair consumer started");
	}

	private static void processing() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
