package com.gopivotal.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class QuotationMsgHandler implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		System.out.println("receiving quotation: "+ new String(msg.getBody()));
	}

}
