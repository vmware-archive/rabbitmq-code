/**
 * 
 */
package com.gopivotal.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 *
 */
public class SimpleReceiver {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		while(true) {
			channel.txSelect();
			GetResponse get = channel.basicGet("quotations", false);
			if(get == null) {
				channel.txCommit();
			} else {
				System.out.println("received message, waiting for commit");
				System.in.read();
				channel.txCommit();
			}
			
		}

	}

}
