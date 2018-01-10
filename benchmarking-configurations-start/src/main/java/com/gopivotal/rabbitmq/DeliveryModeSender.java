/**
 * 
 */
package com.gopivotal.rabbitmq;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 */
public class DeliveryModeSender {

	private static final int NB_MESSAGE = 100000;
	private QuotationService quotationService = new QuotationService();

	public void runTest() throws Exception {
	
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// Put the channel into confirm mode
		channel.confirmSelect();

		channel.exchangeDeclare("deliveryExchange", "direct", true);
		
		// TODO declare the queue "deliveryQueue" with the durable flag
		
		channel.queueBind("deliveryQueue", "deliveryExchange", "nasq");
		
		
		// warming up of the rabbitmq server
		warmUp(channel);
		
		// TODO call senderLoop() method with non-persistent flag

		// TODO call senderLoop() method with persistent flag

		channel.queueDelete("deliveryQueue");
		channel.exchangeDelete("deliveryExchange");
		
		connection.close();

	}

	
	private void senderLoop(Channel channel, int persistentMode, boolean verbose) throws Exception{
		
		// TODO create here the properties to use the argument "persistentMode"
		// and provide the object created to the basicPublish method
		
		long startTime = getCurrentTime();
		for (int i = 0; i < NB_MESSAGE ; i++) {
			channel.basicPublish("deliveryExchange", "nasq", null, quotationService.next().getBytes());
		}
		
		// Wait for confirmations to get an accurate timing
		channel.waitForConfirms();

		long endTime = getCurrentTime();
		
		if(verbose){
			System.out.println("Time to send " + NB_MESSAGE + "  messages with " +
					((persistentMode == 2) ? "" : "NON ") + "persistent mode => " + (endTime - startTime) + " ms.");
		}
		
	}
	
	private void warmUp(Channel channel) throws Exception {
		senderLoop(channel, 1, false);
		channel.queuePurge("deliveryQueue");
		Thread.sleep(1000);
	}
	
	
	public long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) throws Exception {
		DeliveryModeSender deliveryModeSender = new DeliveryModeSender();
		deliveryModeSender.runTest();
		System.out.println(">> End.");
	}

}