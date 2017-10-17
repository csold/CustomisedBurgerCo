package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private static Connection conn;
	
	public static void open() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://10.140.69.227/burgers?user=root&password=password");
	}
	
	public static void close() throws SQLException {
		conn.close();
	}
	
	public static boolean run(String query) throws SQLException {
		return conn.createStatement().execute(query);
	}
	
	public static ResultSet fetch(String query) throws SQLException {
		return conn.createStatement().executeQuery(query);
	}

}
