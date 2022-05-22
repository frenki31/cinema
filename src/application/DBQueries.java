package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBQueries {
	
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	final String URL = "jdbc:mysql://localhost:3306/cinema";
	final String USERNAME = "root";
	final String PASSWORD = "1234";
	Connection connect;
	Statement statement;
	PreparedStatement preparedStatement;
	
	public DBQueries() {
		setConnection();
	}
	
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
	
	public List<Movie> getAllMovies() {
		List<Movie> allMovies = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film");
			resultSet = preparedStatement.executeQuery();
			allMovies = new ArrayList<Movie>();
			
			while(resultSet.next()){
				allMovies.add(new Movie(
						resultSet.getInt("filmID"),
						resultSet.getString("filmTitle"),
						resultSet.getString("runningTime"),
						resultSet.getString("cover"),
						resultSet.getString("link"),
						resultSet.getString("script"),
						resultSet.getInt("releaseYear")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allMovies;
	}

	public List<Movie> getAllCovers() {
		List<Movie> allCovers = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT cover FROM film");
			resultSet = preparedStatement.executeQuery();
			allCovers = new ArrayList<Movie>();
			
			while(resultSet.next()){
				allCovers.add(new Movie(resultSet.getString("cover")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allCovers;
	}
}
