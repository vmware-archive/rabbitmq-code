/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gopivotal.rabbitmq.QuotationService;

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
		
		// TODO 07 use the template to publish the quotation to the quotations exchange

		String receivedQuotation = null;
		short attempts = 5;
		while (receivedQuotation == null && attempts-- > 0) {
			// TODO 08 fetch the message from the quotations queue
			
		}

		Assert.assertNotNull(receivedQuotation);

		Assert.assertEquals(quotation, receivedQuotation);
	}

}
