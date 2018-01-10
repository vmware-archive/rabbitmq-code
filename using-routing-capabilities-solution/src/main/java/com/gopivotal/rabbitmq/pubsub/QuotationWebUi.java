/**
 * 
 */
package com.gopivotal.rabbitmq.pubsub;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 *
 */
public class QuotationWebUi {
	
	private static final List<String> QUOTATIONS = Collections.synchronizedList(new LinkedList<String>());
	
	private static final String QUEUE = "webui";

	public static void main(String [] args) throws Exception {
		Server server = new Server(8085);
		server.setHandler(new QuotationSummaryHandler());
		server.start();
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE, false, false, false,null);
		channel.queuePurge(QUEUE);	
		
		channel.queueBind(QUEUE, "quotations", "");
		
		channel.basicConsume(QUEUE, true,new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				QUOTATIONS.add(0,new String(body) + " " + new Date());	
			}
			
		});		
		
	}
	
	
	
	private static final class QuotationSummaryHandler extends AbstractHandler {
		@Override
		public void handle(String target, HttpServletRequest request,
				HttpServletResponse response, int dispatch) throws IOException,
				ServletException {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain");
			Iterator<String> iter = QUOTATIONS.iterator();
			int count = 0;
			while(iter.hasNext() && count++ < 10) {
				response.getWriter().println(iter.next());
			}
			response.flushBuffer();
		}
	}
	
}
