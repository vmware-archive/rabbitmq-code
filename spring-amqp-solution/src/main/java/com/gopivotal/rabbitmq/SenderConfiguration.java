/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *
 */
@Configuration
public class SenderConfiguration {

	@Bean ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}
	
	@Bean RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(connectionFactory());
	}
	
}
