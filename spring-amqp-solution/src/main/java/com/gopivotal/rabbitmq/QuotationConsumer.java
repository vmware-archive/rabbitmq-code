/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 */
public class QuotationConsumer {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class)) {
			while (true);
		}
	}
		
}
