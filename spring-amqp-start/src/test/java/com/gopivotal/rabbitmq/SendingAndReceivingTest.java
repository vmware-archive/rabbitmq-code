/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * 
 */
@ContextConfiguration(classes=SenderConfiguration.class)
public class SendingAndReceivingTest {
	
	@Autowired RabbitTemplate template;

	private final QuotationService quotationService = new QuotationService();

	@Test
	public void sendAndReceiving() throws Exception {
		String quotation = quotationService.next();
		
		// TODO 07 use the template to publish the quotation to the quotations exchange

		String receivedQuotation = null;
		short attempts = 5;
		while (receivedQuotation == null && attempts-- > 0) {
			// TODO 08 fetch the message from the quotations queue
			
		}

		Assertions.assertNotNull(receivedQuotation);

		Assertions.assertEquals(quotation, receivedQuotation);
	}

}
