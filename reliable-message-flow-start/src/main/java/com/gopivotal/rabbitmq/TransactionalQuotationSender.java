/**
 * 
 */
package com.gopivotal.rabbitmq;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 */
public class TransactionalQuotationSender {
	
	private static final String QUEUE = "transactional.quotations";
	
	private static final QuotationService quotationService = new QuotationService();

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE,true,false,false,null);
		channel.queuePurge(QUEUE);
		int messagesCount = 5;
		int messagesSent = 0;
		int poisonPill =(int)  (Math.random()*5.0*2.0);
		
		System.out.println("sending "+messagesCount+" messages to queue "+QUEUE);

		// TODO start transaction (call txSelect())
		
		for(int i=0;i<messagesCount;i++) {
			if(i == poisonPill) {
				System.out.println("ERROR, message not sent!");
			} else {
				channel.basicPublish("", QUEUE, null, quotationService.nextDetailed().toString().getBytes());
				messagesSent++;
			}
		}
		
		if(messagesCount == messagesSent) {
			System.out.println("messages sent successfully");
			// TODO commit transaction
			
		} else {
			System.out.println("Oops, looks like a message hasn't been! Check the content of the queue");
			// TODO rollback transaction
			
		}
		
		connection.close();
	}

}
