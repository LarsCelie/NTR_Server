package main.java.doa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	static final String JDBC_DRIVER = "jdbc:derby:";
	static final String url = "92.109.48.22";

	String username = "";
	String password = "";

	public Connection getConnection() throws SQLException {

	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.username);
	    connectionProps.put("password", this.password);

	        conn = DriverManager.getConnection(JDBC_DRIVER+url,
	                   connectionProps);
	    System.out.println("Connected to database");
	    return conn;
	}
}
