package com.gopivotal.rabbitmq.besteffort;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.tools.Console;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 */

/**
 *
 */
public class StartServers {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		startHttpServer();
		startAndInitDatabase(args);		
		initBroker();
	}

	private static void startHttpServer() throws Exception {
		Server server = new Server(8085);
		server.setHandler(new QuotationSummaryHandler());
		server.start();
	}
	
	private static void startAndInitDatabase(String[] args) throws SQLException {
		Console.main("-tcp"); // starts only the TCP server, no console
		JdbcUtils.initDb(JdbcUtils.DB_CONSUMER);
		JdbcUtils.initDb(JdbcUtils.DB_SENDER);
	}
	
	private static void initBroker() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();		
		final Channel channel = connection.createChannel();
		channel.queueDeclare(DatabaseQuotationLogger.CONSUMER_QUEUE, true, false, false, null);
		channel.queuePurge(DatabaseQuotationLogger.CONSUMER_QUEUE);
		connection.close();
	}
	
	private static final class QuotationSummaryHandler extends AbstractHandler {
		@Override
		public void handle(String target, HttpServletRequest request,
				HttpServletResponse response, int dispatch) throws IOException,
				ServletException {
			String urlWithoutSlash = target.substring(1);
			if(JdbcUtils.DB_CONSUMER.equals(urlWithoutSlash) || JdbcUtils.DB_SENDER.equals(urlWithoutSlash)) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("image/png");
				Chart chart = new Chart(urlWithoutSlash,JdbcUtils.getAllQuotations(urlWithoutSlash));
				chart.write(response.getOutputStream());
			} else if("/summary".equals(target)) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("text/html");
				StringBuilder builder = new StringBuilder()
					.append("<html><head><title>Quotations</title></head>")
					.append("<table><tr>")
					.append("<td>"+JdbcUtils.count(JdbcUtils.DB_SENDER)+" quotation(s) <br/><img src=\"/sender\" /></td>")
					.append("<td>"+JdbcUtils.count(JdbcUtils.DB_CONSUMER)+" quotation(s) <br/><img src=\"/consumer\" /></td>")
					.append("</tr></table>")
					.append("</html>");
				response.getWriter().write(builder.toString());
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			response.flushBuffer();
		}
	}

}
