package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBQueries {
	
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:3306/cinema";
	private final String USERNAME = "root";
	private final String PASSWORD = "1234";
	private Connection connect;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public DBQueries() {
		setConnection();
	}
	
	public Connection setConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			connect = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			statement = connect.createStatement();
			if(connect == null)
				System.out.println("Error");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connect;
	}
	
	public ObservableList<Movie> getAllMovies() {
		ObservableList<Movie> allMovies = null;
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film");
			resultSet = preparedStatement.executeQuery();
			allMovies = FXCollections.observableArrayList();
			
			while(resultSet.next()){
				allMovies.add(new Movie(
						resultSet.getInt("filmID"),
						resultSet.getString("filmTitle"),
						resultSet.getString("runningTime"),
						resultSet.getString("cover"),
						resultSet.getString("link"),
						resultSet.getString("script"),
						resultSet.getString("releaseYear"),
						resultSet.getString("genre")));
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
		List<Movie> titleCoverYear = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film");
			resultSet = preparedStatement.executeQuery();
			titleCoverYear = new ArrayList<Movie>();
			
			while(resultSet.next()){
				titleCoverYear.add(new Movie(resultSet.getString("filmTitle"),
						                     resultSet.getString("cover"),
						                     resultSet.getString("releaseYear"),
						                     resultSet.getString("genre")));
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
				try {
					setConnection().close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return titleCoverYear;
	}
	
	public ObservableList<Movie> searchMovies(String title) {
		ObservableList<Movie> searchList = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film WHERE filmTitle LIKE ?");
			preparedStatement.setString(1, "%"+title+"%");
			resultSet = preparedStatement.executeQuery();
			searchList = FXCollections.observableArrayList();
			
			while(resultSet.next()){
				searchList.add(new Movie(resultSet.getString("filmTitle"),
						                     resultSet.getString("cover"),
						                     resultSet.getString("releaseYear"),
						                     resultSet.getString("genre")));
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
				try {
					setConnection().close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return searchList;
	}
	
	public int addUser(String name, String email, String phoneNo, String password) {
		int result = 0;
		String sql = "INSERT INTO user(name, email,phone,password) VALUES (?, ?, ?, ?)";
		try {
			preparedStatement = setConnection().prepareStatement(sql);
		    preparedStatement.setString(1, name);
		    preparedStatement.setString(2, email);
		    preparedStatement.setString(3, phoneNo);
		    preparedStatement.setString(4, password);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				setConnection().close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;		
	}
	
	public boolean loginUser(String email, String password){
		boolean result = false;
		resultSet = null;
		List<User> users = null;
		String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
		try {
			preparedStatement = setConnection().prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			users = new ArrayList<User>();
			if (resultSet.next()) {
				users.add(new User(resultSet.getInt("userID"), 
						           resultSet.getString("name"),
						           resultSet.getString("email"), 
						           resultSet.getString("phone"), 
						           resultSet.getString("password")));
				Alert message = new Alert(AlertType.INFORMATION);
				message.setTitle("Login");
				message.setContentText("Welcome back "+resultSet.getString("name"));
				message.show();
				result = true;
			}
			else {
				Alert message = new Alert(AlertType.ERROR);
				message.setTitle("Error");
				message.setContentText("Incorrect password or email");
				message.show();
				email = "";
				password = "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				setConnection().close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	public Movie openMovieByTitle(String title) {
		Movie movie = null;
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film WHERE filmTitle = ?");
			preparedStatement.setString(1, title);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				movie = new Movie(resultSet.getInt("filmID"), 
						          resultSet.getString("filmTitle"), 
						          resultSet.getString("runningTime"), 
						          resultSet.getString("cover"), 
						          resultSet.getString("link"),
						          resultSet.getString("script"),
						          resultSet.getString("releaseYear"),
						          resultSet.getString("genre"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				setConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return movie;
	}
	
	public int addMovie(String filmTitle, String runningTime, String cover, String link, String script, String releaseYear, String genre) {
		int result = 0;
		String sql = "INSERT INTO film(filmTitle,runningTime,cover,link,script,releaseYear,genre) VALUES (?,?,?,?,?,?,?)";
		try {
			preparedStatement = setConnection().prepareStatement(sql);
		    preparedStatement.setString(1, filmTitle);
		    preparedStatement.setString(2, runningTime);
		    preparedStatement.setString(3, cover);
		    preparedStatement.setString(4, link);
		    preparedStatement.setString(5, script); 
		    preparedStatement.setString(6, releaseYear);
		    preparedStatement.setString(7, genre);
		    result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				setConnection().close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;		
	}
	
	public int addSuggested(String title, String director, String year, String genre) {
		int result = 0;
		String sql = "INSERT INTO suggestedFilm(title,director,year,genre) VALUES (?, ?, ?, ?)";
		try {
			preparedStatement = setConnection().prepareStatement(sql);
		    preparedStatement.setString(1, title);
		    preparedStatement.setString(2, director);
		    preparedStatement.setString(3, year);
		    preparedStatement.setString(4, genre);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				setConnection().close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;		
	}
	
	public ObservableList<User> getAllUsers() {
		ObservableList<User> users = null;
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM user");
			resultSet = preparedStatement.executeQuery();
			users = FXCollections.observableArrayList();
			while(resultSet.next()) {
				users.add(new User(resultSet.getInt("userID"), 
						           resultSet.getString("name"), 
						           resultSet.getString("email"), 
						           resultSet.getString("phone"), 
						           resultSet.getString("password")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				setConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return users;
	}
	
	public ObservableList<SuggestMovie> getAllSuggested() {
		ObservableList<SuggestMovie> suggestedList = null;
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM suggestedFilm");
			resultSet = preparedStatement.executeQuery();
			suggestedList = FXCollections.observableArrayList();
			while(resultSet.next()) {
				suggestedList.add(new SuggestMovie(resultSet.getInt("id"), 
						                           resultSet.getString("title"), 
						                           resultSet.getString("director"), 
						                           resultSet.getString("year"), 
						                           resultSet.getString("genre")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				setConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return suggestedList;
	}
}
