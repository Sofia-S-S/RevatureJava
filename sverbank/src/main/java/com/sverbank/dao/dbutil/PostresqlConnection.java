package com.sverbank.dao.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostresqlConnection {

	private static Connection connection;
	
	private PostresqlConnection() {}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url="jdbc:postgresql://localhost:5432/postgres";
		String username="postgres";
		String password="Ne18mne!";
		connection=DriverManager.getConnection(url, username, password);
		return connection;
	}
}
