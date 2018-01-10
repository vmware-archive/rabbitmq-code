/**
 * 
 */
package com.gopivotal.rabbitmq.ha;

import java.util.Random;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 */
public class HaQuotationSender {
	
	private static final int [] SERVER_PORTS = {5672,5673,5674};
	
	private static final int FIRST_CONNECTION_PORT = 5672;
	
	private static final Random RANDOM = new Random();

	private static final QuotationService quotationService = new QuotationService();

	public static void main(String[] args) throws Exception {
		Channel channel = connect(FIRST_CONNECTION_PORT);
		
		while (true) {
			letsWait();
			String quotation = quotationService.next();			
			try {
				channel.basicPublish("quotations", "", null,quotation.getBytes());
			} catch (Exception e) {
				System.err.println("exception while sending a message, trying to reconnect");
				channel = connect();
			}
		}

	}

	private static Channel connect() {
		return connect(-1);
	}
	
	private static Channel connect(int portSuggestion) {		
		Channel channel = null;
		while (channel == null) {
			try {
				Thread.sleep(500);
				ConnectionFactory factory = new ConnectionFactory();
				factory.setUsername("guest");
				factory.setPassword("guest");
				factory.setVirtualHost("/");
				factory.setHost("localhost");

				int serverPort = portSuggestion == -1 ? pickPortRandomly() : portSuggestion;
				System.out.println("connecting to server with port: "
						+ serverPort);

				factory.setPort(serverPort);
				Connection connection = factory.newConnection();
				channel = connection.createChannel();
				return channel;
			} catch (Exception e) {
				System.err.println("couln't connect to broker, trying again");
			}
		}
		return null;
	}
	
	private static void letsWait() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.err.println("couldn't wait!");
		}
	}
	
	private static int pickPortRandomly() {
		return SERVER_PORTS[RANDOM.nextInt(SERVER_PORTS.length)];
	}

}
