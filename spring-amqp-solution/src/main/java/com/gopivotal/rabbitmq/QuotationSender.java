/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 */
public class QuotationSender {
	
	private static final QuotationService quotationService = new QuotationService();

	public static void main(String [] args) throws Exception {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(SenderConfiguration.class)) {
			RabbitTemplate template = context.getBean(RabbitTemplate.class); 

			while(true) {
				letsWait();
				String quotation = quotationService.next();
				template.convertAndSend("quotations", "nasq", quotation);
			}
		}
	}

	private static void letsWait() throws Exception {
		Thread.sleep(1000);		
	}
	
}
