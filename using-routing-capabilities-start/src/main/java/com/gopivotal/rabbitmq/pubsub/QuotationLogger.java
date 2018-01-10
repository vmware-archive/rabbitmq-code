/**
 * 
 */
package com.gopivotal.rabbitmq.pubsub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class QuotationLogger {
	
	private static final String QUEUE = "logger";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		File log = new File("./quotations.log");
		if(log.exists()) {
			log.delete();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(log));
		
		// TODO connect to the broker and create a channel
		
		// TODO declare the queue and purge it to make sure it's empty at the beginning
		// use the QUEUE property for the name of the queue
		
		// TODO bind the queue to the "quotations" exchange

		// TODO channel.basicGet in the loop to receive messages	
	    while(true) {
	    	
	    	// TODO get the quotation from the body of the message and use the writeQuotation method
	    }

	}

	private static void writeQuotation(BufferedWriter writer,
			String quotation) throws IOException {
		writer.append(quotation);
		writer.newLine();
		writer.flush();
	}

}
