/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 */
@ContextConfiguration(classes=SenderConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SendingAndReceivingTest {
	
	@Autowired RabbitTemplate template;

	private final QuotationService quotationService = new QuotationService();

	@Test
	public void sendAndReceiving() throws Exception {
		String quotation = quotationService.next();
		template.convertAndSend("quotations", "nasq", quotation);

		String receivedQuotation = null;
		short attempts = 5;
		while (receivedQuotation == null && attempts-- > 0) {
			receivedQuotation = (String)template.receiveAndConvert("quotations");
		}

		Assert.assertNotNull(receivedQuotation);

		Assert.assertEquals(quotation, receivedQuotation);
	}

}
