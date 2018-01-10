/**
 * 
 */
package com.gopivotal.rabbitmq.ha;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 */
public class HaQuotationSender {

	private static final QuotationService quotationService = new QuotationService();

	public static void main(String[] args) throws Exception {
		Channel channel = connect();
		
		while (true) {
			letsWait();
			String quotation = quotationService.next();			
			channel.basicPublish("quotations", "", null,quotation.getBytes());
		}

	}

	private static Channel connect() throws Exception {
		Channel channel = null;
		while (channel == null) {
			try {
				Thread.sleep(500);
				ConnectionFactory factory = new ConnectionFactory();
				factory.setUsername("guest");
				factory.setPassword("guest");
				factory.setVirtualHost("/");
				factory.setHost("localhost");

				int serverPort = 5673;
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

}
