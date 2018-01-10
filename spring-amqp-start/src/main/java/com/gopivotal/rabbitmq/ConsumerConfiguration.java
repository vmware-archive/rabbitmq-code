/**
 * 
 */
package com.gopivotal.rabbitmq;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class ConsumerConfiguration {

	@Bean ConnectionFactory connectionFactory() {
		// TODO 05 create the CachingConnectionFactory
		// the default parameters (host, port, etc) work for a default RabbitMQ installation
		return null;
	}
	
	@Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
		// TODO 06 configure the SimpleMessageListenerContainer
		// it must listen on the quotations queue and call the quotationMsgHandler bean
		// (don't forget to link it to the ConnectionFactory
        return null;
    }
	
	@Bean MessageListener quotationMsgHandler() {
		return new QuotationMsgHandler();
	}
	
}
