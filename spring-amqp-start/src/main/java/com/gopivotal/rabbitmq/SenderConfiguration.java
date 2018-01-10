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
		// TODO 01 create the CachingConnectionFactory
		// the default parameters (host, port, etc) work for a default RabbitMQ installation
		return null;
	}
	
	@Bean RabbitTemplate rabbitTemplate() {
		// TODO 02 create the RabbitTemplate
		// don't forget to link it to the ConnectionFactory
		return null;
	}
	
}
