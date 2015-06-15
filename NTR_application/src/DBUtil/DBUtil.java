package DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {
	private DBUtil() {
		
	}
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String serverName = "92.109.48.222";
			String portNumber = "1521";
			String sid = "xe";
			String url = "jdbc:oracle:thin:@"+serverName+":"+portNumber+":"+sid;
			String username = "NTR_database";
			String password = "NTR";
			con = DriverManager.getConnection(url,username,password);
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void closeConnection(Connection con) {
		try {
			if(con != null) con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
