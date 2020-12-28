package com.app.dao.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//4

public class PostgresqlConnection {
	
	//4.1
	private static Connection connection;
	
	private PostgresqlConnection() { 
		
	}
	
	//4.2
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		//STEP 1 - Load Driver
		Class.forName("org.postgresql.Driver");// 
		//STEP 2 - Open Connection (url, username, password)
		String url="jdbc:postgresql://localhost:5432/postgres";
		String username="postgres";
		String password = "Ne18mne!";
		connection=DriverManager.getConnection(url, username, password);
		System.out.println("my connection is OK");
		return connection;

	}
	

}
