/**
 * 
 */
package com.gopivotal.rabbitmq.pubsub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 *
 */
public class QuotationLogger {
	
	private static final String QUEUE = "logger";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		File log = new File("./quotations.log");
		if(log.exists()) {
			log.delete();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(log));
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE, false, false, false,null);
		channel.queuePurge(QUEUE);	
		
		channel.queueBind(QUEUE, "quotations", "");
		
	    while(true) {
	    	GetResponse response = channel.basicGet(QUEUE,true);
	    	if(response != null) {
	    		String quotation = new String(response.getBody());
	    		writeQuotation(writer, quotation);
	    	}
	    }

	}

	private static void writeQuotation(BufferedWriter writer,
			String quotation) throws IOException {
		writer.append(quotation);
		writer.newLine();
		writer.flush();
	}

}
