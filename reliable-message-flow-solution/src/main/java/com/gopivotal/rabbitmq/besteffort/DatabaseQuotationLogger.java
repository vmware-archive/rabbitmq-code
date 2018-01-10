/**
 * 
 */
package com.gopivotal.rabbitmq.besteffort;

import java.io.IOException;

import com.gopivotal.rabbitmq.Quotation;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 *
 */
public class DatabaseQuotationLogger {
	
	public static final String CONSUMER_QUEUE = "webui";
	
	public static void main(String [] args) throws Exception {		
		final java.sql.Connection dbConn = JdbcUtils.conn(JdbcUtils.DB_CONSUMER);
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		final Channel channel = connection.createChannel();
		
		channel.basicConsume(CONSUMER_QUEUE, false,new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				
				Quotation quotation = Quotation.read(new String(body));
				JdbcUtils.insertQuotationIntoDb(dbConn, quotation);
				
				if(Math.random() < 0.20) {
					System.out.println("ERROR, no ack!");
				} else {
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
				
				
			}
			
		});		
		
	}
	
	
}
