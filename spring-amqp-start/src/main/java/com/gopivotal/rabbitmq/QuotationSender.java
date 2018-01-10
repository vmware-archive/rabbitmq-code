/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gopivotal.rabbitmq.QuotationService;

/**
 *
 */
public class QuotationSender {
	
	private static final QuotationService quotationService = new QuotationService();

	public static void main(String [] args) throws Exception {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(SenderConfiguration.class)) {
			// TODO 03 declare a RabbitTemplate variable, and fetch the RabbitTemplate bean from the Spring context

			while(true) {
				letsWait();
				String quotation = quotationService.next();
				// TODO  04 using the template's convertAndSend() method, publish a message to the quotations exchange
			}
		}
	}

	private static void letsWait() throws Exception {
		Thread.sleep(1000);		
	}
	
}
