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
public class BigQuotationSender {


	private static final int INCREMENT = 50000;

	private QuotationService quotationService = new QuotationService();
	private int QUOTATION_SIZE_INITIAL = quotationService.next().getBytes().length;
	private int nbQuotations = 1;
	
	public void runTest() throws Exception {
	
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
	
		// TODO create a while loop to iterate on the basicPublish
			
			long startTime = getCurrentTime();
		
			channel.basicPublish("quotations", "nasq", null, getBulkQuotations().getBytes());
			
			long endTime = getCurrentTime();
			
			System.out.println("Time to send packets of " + (nbQuotations * QUOTATION_SIZE_INITIAL) / 1000 + " kilobytes => " + (endTime - startTime) + " ms.");
			
			// TODO uncomment the line below to increment the size
			// nbQuotations = nbQuotations + INCREMENT;
		
		connection.close();

	}

	private String getBulkQuotations() throws Exception {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < nbQuotations ; i++){
			sb.append(quotationService.next()).append(";");
		}
		return sb.toString();
	}
	
	public long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) throws Exception {
		BigQuotationSender bigQuotationSender = new BigQuotationSender();
		bigQuotationSender.runTest();
		System.out.println(">> End.");
	}

}
