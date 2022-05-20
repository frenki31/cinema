package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	String JDBC_DRIVER = "com.sql.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/sakila";
	String USERNAME = "root";
	String PASSWORD = "1234";
	Connection connect;
	Statement statement;
	
	public Connection setConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			connect = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			statement = connect.createStatement();
			if(connect != null)
				System.out.println("Connected to DB");
			else
				System.out.println("Error!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connect;
	}
	
	public void executeSQL(String query) {
		int result;
		try {
			result = statement.executeUpdate(query);
			if(result == 1)
				System.out.println("Update succesful!\n");
			else
				System.out.println("Update error!\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
