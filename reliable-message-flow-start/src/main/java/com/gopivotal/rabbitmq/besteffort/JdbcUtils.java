/**
 * 
 */
package com.gopivotal.rabbitmq.besteffort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gopivotal.rabbitmq.Quotation;

/**
 *
 */
public class JdbcUtils {
	
	public static final String DB_SENDER = "sender";
	public static final String DB_CONSUMER = "consumer";

	public static void initDb(String db) {
		java.sql.Connection conn = conn(db+";DB_CLOSE_DELAY=-1");
		try {
			conn.prepareStatement("drop table quotations if exists").execute();
			conn.prepareStatement("create table quotations (symbol varchar(5),value decimal,received_time bigint)").execute();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			close(conn);
		}
	}
	
	public static List<Quotation> getAllQuotations(String db) {
		Connection conn = conn(db);
		try {
			List<Quotation> quotations = new ArrayList<Quotation>();
			ResultSet rs = conn.prepareStatement("select symbol,value,received_time from quotations order by received_time asc").executeQuery();
			while(rs.next()) {
				quotations.add(new Quotation(rs.getString(1), rs.getDouble(2), new Date(rs.getLong(3))));
			}
			return quotations;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn);
		}
	}
	
	public static int count(String db) {
		Connection conn = conn(db);
		try {
			ResultSet rs = conn.prepareStatement("select count(*) from quotations").executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn);
		} 
	}
	
	public static java.sql.Connection conn(String db) {
		try {
			java.sql.Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/mem:"+db,"sa","");
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public static void insertQuotationIntoDb(java.sql.Connection dbConn,Quotation quotation) {
		try {
			dbConn.setAutoCommit(false);
			PreparedStatement ps = dbConn.prepareStatement("insert into quotations (symbol,value,received_time) values (?,?,?)");
			ps.setString(1, quotation.getStock());
			ps.setDouble(2, quotation.getValue());
			ps.setLong(3, quotation.getDate().getTime());
			ps.execute();
			dbConn.commit();
			dbConn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) { }
		}
	}
	
}
