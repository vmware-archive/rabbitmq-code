/**
 * 
 */
package com.gopivotal.rabbitmq;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * 
 */
public class AcknowledgementBencher {

	private static final int NB_MESSAGE = 100000;
	private QuotationService quotationService = new QuotationService();

	public void runTest() throws Exception {
	
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// TODO call the send() method to post messages in the queue
		
		System.out.println("Waiting for all message to be written in the queue...");
		Thread.sleep(10000);
		
		System.out.println("Bench is starting...");
		
		// TODO call twice the consume() method with :
		// 			- autoAck = true
		// 			- autoAck = false
		
		connection.close();

	}

	
	public void send(Channel channel) throws Exception {
	
		System.out.println("Sending " + NB_MESSAGE * 2 + " messages for testing ACK...");
		
		for(int i = 0 ; i < NB_MESSAGE * 2 ; i++){
			channel.basicPublish("quotations", "nasq", null, quotationService.next().getBytes());
		}

		System.out.println(NB_MESSAGE * 2 + " messages has been sent for testing ACK...");
	
	}
	
	public void consume(Channel channel, boolean autoAck) throws Exception{
	
		long deliveryTag = 0;
		GetResponse response = null;
		
		long startTime = getCurrentTime();
		
		for(int i = 0 ; i < NB_MESSAGE; i++){
			
			// TODO write the code that consumes messages from a queue
			
			if (response != null && !autoAck) {
				deliveryTag = response.getEnvelope().getDeliveryTag();
				channel.basicAck(deliveryTag, false); 
			}
		}
		
		long endTime = getCurrentTime();
		
		System.out.println("Time to consume " + NB_MESSAGE + "  messages " + (autoAck ? "with no" : "with") + " ACK => " + (endTime - startTime) + " ms.");
		
	}
	
	public long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) throws Exception {
		AcknowledgementBencher acknowledgementBencher = new AcknowledgementBencher();
		acknowledgementBencher.runTest();
		System.out.println(">> End.");
	}

}
