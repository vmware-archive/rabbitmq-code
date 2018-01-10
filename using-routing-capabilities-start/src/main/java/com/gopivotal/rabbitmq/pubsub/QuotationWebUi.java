/**
 * 
 */
package com.gopivotal.rabbitmq.pubsub;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

/**
 *
 */
public class QuotationWebUi {
	
	private static final List<String> QUOTATIONS = Collections.synchronizedList(new LinkedList<String>());
	
	private static final String QUEUE = "webui";

	public static void main(String [] args) throws Exception {
		startWebServer();
		
		// TODO connect to the broker and create a channel
		
		// TODO declare the queue and purge it to make sure it's empty at the beginning
		// use the QUEUE property for the name of the queue
				
				
		// TODO bind the queue to the "quotations" exchange
		
		// TODO use Channel.basicConsume to get the messages
		// in the handleDelivery method, get the quotation and add it to the QUOTATION Java collection:
		// QUOTATIONS.add(0,new String(body) + " " + new Date());
		
	}

	private static void startWebServer() throws Exception {
		Server server = new Server(8085);
		server.setHandler(new QuotationSummaryHandler());
		server.start();
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
