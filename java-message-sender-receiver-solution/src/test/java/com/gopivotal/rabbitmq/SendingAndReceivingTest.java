/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.junit.Assert;
import org.junit.Test;

import com.gopivotal.rabbitmq.QuotationService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * 
 */
public class SendingAndReceivingTest {

	private final QuotationService quotationService = new QuotationService();

	@Test
	public void sendAndReceiving() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5672);
		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		/* uncomment the next lines if no quotations queue, nor quotations exchange exist.
		channel.queueDeclare("quotations", false, false, false, null);
		channel.queuePurge("quotations");

		channel.exchangeDeclare("quotations", "fanout", true);
		channel.queueBind("quotations", "quotations", "");
		*/

		String quotation = quotationService.next();
		byte[] message = quotation.getBytes();

		channel.basicPublish("quotations", "nasq", null, message);

		GetResponse response = null;
		short attempts = 5;
		while (response == null && attempts-- > 0) {
			response = channel.basicGet("quotations", true);
		}

		Assert.assertNotNull(response);

		String receivedQuotation = new String(response.getBody());
		Assert.assertEquals(quotation, receivedQuotation);

		channel.close();

		connection.close();

	}

}
