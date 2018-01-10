/**
 * 
 */
package com.gopivotal.rabbitmq.besteffort;

import com.gopivotal.rabbitmq.Quotation;
import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 */
public class DatabaseQuotationSender {
	
	private static final QuotationService quotationService = new QuotationService();
	
	public static void main(String [] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		java.sql.Connection dbConn = JdbcUtils.conn(JdbcUtils.DB_SENDER);
		while(true) {
			letsWait();
			Quotation quotation = quotationService.nextDetailed();
			channel.basicPublish("", DatabaseQuotationLogger.CONSUMER_QUEUE, null, quotation.toString().getBytes());
			JdbcUtils.insertQuotationIntoDb(dbConn,quotation);
		}
	}

	private static void letsWait() throws Exception {
		Thread.sleep(1000);		
	}
	
	
	
	
	
}
