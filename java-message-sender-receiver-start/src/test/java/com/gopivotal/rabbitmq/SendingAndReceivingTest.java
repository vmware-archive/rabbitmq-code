/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.junit.jupiter.api.Test;

/**
 *
 */
public class SendingAndReceivingTest {

	private final QuotationService quotationService = new QuotationService();
	
	@Test 
	public void sendAndReceiving() throws Exception {
		// TODO setup the connection with a ConnectionFactory (see slides for examples)
		
		// TODO setup a new channel using the newly created connection
		
		// Un-comment the next lines if the quotations queue and quotations exchange do not exist.
		// channel.queueDeclare("quotations", false, false, false, null);
		// channel.exchangeDeclare("quotations", "fanout", true);
		// channel.queueBind("quotations", "quotations", "");

		// TODO get the next quotation and publish the message

		// TODO fetch the response

		// TODO perform assertions

		// TODO don't forget to close the channel and connection
		
	}
	
}
