/**
 * 
 */
package com.gopivotal.rabbitmq.ha;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

/**
 *
 */
public class HaQuotationConsumer {
	
	private static final int [] SERVER_PORTS = {5672,5673,5674};
	
	private static final int FIRST_CONNECTION_PORT = 5674;
	
	private static final Random RANDOM = new Random();
	
	private static final Address [] ADDRESSES = new Address[] {
			new Address("localhost",5672),
			new Address("localhost",5673),
			new Address("localhost",5674)
	};
	
	private static Connection connection;

	public static void main(String [] args) throws Exception {
		while(true) {
			try {
				CountDownLatch countDownLatch = new CountDownLatch(1);
				connectAndListen();
				countDownLatch.await();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
				
	}
	
	private static void initConnection() {
		while(true) {
			try {
				Thread.sleep(500);
				int port = connection == null ? FIRST_CONNECTION_PORT : pickPortRandomly();
				connection = createConnection(port);
				return;
			} catch (Exception e) {
				System.err.println("couln't connect to broker, trying again");
			}
			
		}
	}
	
	private static Connection createConnection(int port) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");

		System.out.println("connecting to server with port: "+port);
		
		factory.setPort(port);
		Connection conn = factory.newConnection();
		
		conn.addShutdownListener(new ShutdownListener() {
			
			@Override
			public void shutdownCompleted(ShutdownSignalException cause) {
				System.err.println("shutdown of connection detected");
				if(cause.isHardError()) {
					connectAndListen();
				}
			}
		});
		
		return conn;
	}

	private static void connectAndListen() {
		initConnection();		
		try {
			Channel channel = connection.createChannel();

			channel.basicConsume("market.us", true,new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope,
						BasicProperties properties, byte[] body) throws IOException {
					System.out.println("receiving quotation: "+new String(body));				
				}
				
				@Override
				public void handleCancel(String consumerTag) throws IOException {
					System.err.println("detected consumer cancellation, trying to reconnect");
					connectAndListen();
				}
				
			});
		} catch (Exception e) {			
			System.err.println("error while listening "+e);
			try {
				connection.close();
			} catch (IOException ioe) {
				System.err.println("error while closing connection after a failure");
			}
		}
	}
	
	private static int pickPortRandomly() {
		return SERVER_PORTS[RANDOM.nextInt(SERVER_PORTS.length)];
	}
	
}
