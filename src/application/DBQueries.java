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

/**
 * Database class where all the database manipulation are prepared
 * @author user
 *
 */
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
	/**
	 * This method establishes the connection to the database
	 * @return
	 */
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
	/**
	 * Method to get all movies
	 * @return
	 */
	public ObservableList<Movie> getAllMovies() {
		ObservableList<Movie> allMovies = FXCollections.observableArrayList();
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				allMovies.add(new Movie(
						resultSet.getInt("filmID"),
						resultSet.getString("filmTitle"),
						resultSet.getString("runningTime"),
						resultSet.getString("cover"),
						resultSet.getString("link"),
						resultSet.getString("script"),
						resultSet.getString("releaseYear"),
						resultSet.getString("genre"),
						resultSet.getString("movieLink")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return allMovies;
	}
	/**
	 * Method to get all the covers
	 * @return
	 */
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
			close();
		}
		return titleCoverYear;
	}
	/**
	 * Method that makes possible to search movies by their title
	 * @param title
	 * @return
	 */
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
			close();
		}
		return searchList;
	}
	/**
	 * Method to add a new user in the database
	 * @param name
	 * @param email
	 * @param phoneNo
	 * @param password
	 * @return
	 */
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
			close();
		}
		return result;		
	}
	/**
	 * Method to check the email and password and login the user 
	 * @param email
	 * @param password
	 * @return
	 */
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
		    close();
		}
		return result;
	}
	/**
	 * Method to retrieve all movie information using the title
	 * @param title
	 * @return
	 */
	public ObservableList<Movie> openMovieByTitle(String title) {
		ObservableList<Movie> movies = FXCollections.observableArrayList();
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film WHERE filmTitle = ?");
			preparedStatement.setString(1, title);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				movies.add(new Movie(resultSet.getInt("filmID"), 
						          resultSet.getString("filmTitle"), 
						          resultSet.getString("runningTime"), 
						          resultSet.getString("cover"), 
						          resultSet.getString("link"),
						          resultSet.getString("script"),
						          resultSet.getString("releaseYear"),
						          resultSet.getString("genre"),
						          resultSet.getString("movieLink")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return movies;
	}
	/**
	 * Method to add a new movie
	 * @param filmTitle
	 * @param runningTime
	 * @param cover
	 * @param link
	 * @param script
	 * @param releaseYear
	 * @param genre
	 * @param movieLink
	 * @return
	 */
	public int addMovie(String filmTitle, String runningTime, String cover, String link, String script, String releaseYear, String genre, String movieLink) {
		int result = 0;
		String sql = "INSERT INTO film(filmTitle,runningTime,cover,link,script,releaseYear,genre,movieLink) VALUES (?,?,?,?,?,?,?,?)";
		try {
			preparedStatement = setConnection().prepareStatement(sql);
		    preparedStatement.setString(1, filmTitle);
		    preparedStatement.setString(2, runningTime);
		    preparedStatement.setString(3, cover);
		    preparedStatement.setString(4, link);
		    preparedStatement.setString(5, script); 
		    preparedStatement.setString(6, releaseYear);
		    preparedStatement.setString(7, genre);
		    preparedStatement.setString(8, movieLink);
		    result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return result;		
	}
	/**
	 * Method to add a new suggestion
	 * @param title
	 * @param director
	 * @param year
	 * @param genre
	 * @return
	 */
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
			close();
		}
		return result;		
	}
	/**
	 * Method to get all the users
	 * @return
	 */
	public ObservableList<User> getAllUsers() {
		ObservableList<User> users = FXCollections.observableArrayList();
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM user");
			resultSet = preparedStatement.executeQuery();
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
			close();
		}
		return users;
	}
	/**
	 * Method to get all the suggested movies
	 * @return
	 */
	public ObservableList<SuggestMovie> getAllSuggested() {
		ObservableList<SuggestMovie> suggestedList = FXCollections.observableArrayList();
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM suggestedFilm");
			resultSet = preparedStatement.executeQuery();
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
			close();
		}
		return suggestedList;
	}
	/**
	 * Method to classify the movies by their genre
	 * @param genre
	 * @return
	 */
	public ObservableList<Movie> clasifyGenres(String genre) {
		ObservableList<Movie> searchList = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film WHERE genre = ?");
			preparedStatement.setString(1, genre);
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
			close();
		}
		return searchList;
	}
	/**
	 * Method to get the movie details using the title
	 * @param title
	 * @return
	 */
	public ObservableList<Movie> movieDetails(String title) {
		ResultSet resultSet = null;
		ObservableList<Movie> movies = FXCollections.observableArrayList();
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM film WHERE filmTitle = ?");
			preparedStatement.setString(1, title);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				movies.add(new Movie(resultSet.getInt("filmID"), 
						             resultSet.getString("filmTitle"), 
						             resultSet.getString("runningTime"), 
						             resultSet.getString("cover"), 
						             resultSet.getString("link"),
						             resultSet.getString("script"),
						             resultSet.getString("releaseYear"),
						             resultSet.getString("genre"),
						             resultSet.getString("movieLink")));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return movies;
	}
	/**
	 * Method to get all movie genres
	 * @return
	 */
	public ObservableList<String> getGenres(){
		resultSet = null;
		ObservableList<String> genres = FXCollections.observableArrayList();
		try {
			preparedStatement = setConnection().prepareStatement("SELECT DISTINCT genre FROM film");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				genres.add(new String(resultSet.getString("genre")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return genres;
	}
	/**
	 * Method to check if the email is previously used in the database
	 * @param email
	 * @return
	 */
	public boolean checkEmails(String email) {
		boolean checked = false;
		resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM user WHERE email = ?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				checked = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return checked;
	}
	/**
	 * Method which deletes a movie
	 * @param id
	 * @return
	 */
	public int deleteMovie(int id) {
		int result = 0;
		try {
			preparedStatement = setConnection().prepareStatement("DELETE FROM film where filmID = ?");
			preparedStatement.setInt(1, id);
			result= preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return result;
	}
	/**
	 * Method to delete a movie
	 * @param id
	 * @return
	 */
	public int deleteUser(int id) {
		int result = 0;
		try {
			preparedStatement = setConnection().prepareStatement("DELETE FROM user where userID = ?");
			preparedStatement.setInt(1, id);
			result= preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return result;
	}
	/**
	 * Method to update movie details
	 * @param id
	 * @param title
	 * @param duration
	 * @param cover
	 * @param trailer
	 * @param description
	 * @param year
	 * @param genre
	 * @param movieLink
	 * @return
	 */
	public int updateMovie(int id,String title,String duration,String cover,String trailer,String description,String year,String genre,String movieLink) {
		int result = 0;
		try {
			preparedStatement = setConnection().prepareStatement("UPDATE film SET filmTitle = ?, runningTime = ?, cover = ?,"
					+ "link = ?, script = ?, releaseYear = ?, genre = ?, movieLink = ? WHERE filmID = ?");
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, duration);
			preparedStatement.setString(3, cover);
			preparedStatement.setString(4, trailer);
			preparedStatement.setString(5, description);
			preparedStatement.setString(6, year);
			preparedStatement.setString(7, genre);
			preparedStatement.setString(8, movieLink);
			preparedStatement.setInt(9, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return result;
	}
	/**
	 * Method to update user's information
	 * @param id
	 * @param name
	 * @param email
	 * @param phone
	 * @param password
	 * @return
	 */
	public int updateUser(int id,String name,String email,String phone,String password) {
		int result = 0;
		try {
			preparedStatement = setConnection().prepareStatement("UPDATE user SET name = ?, email = ?, phone = ?,"
					+ "password = ? WHERE userID = ?");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, password);
			preparedStatement.setInt(5, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return result;
	}
	/**
	 * Method to get the link where a movie can be watched
	 * @param title
	 * @return
	 */
	public ObservableList<Movie> getMovieLink(String title) {
		ResultSet resultSet = null;
		ObservableList<Movie> movies = FXCollections.observableArrayList();
		try {
			preparedStatement = setConnection().prepareStatement("SELECT movieLink FROM film WHERE filmTitle = ?");
			preparedStatement.setString(1, title);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				movies.add(new Movie(resultSet.getString("movieLink")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return movies;
	}
	/**
	 * Method to get the password when a user forgets it
	 * @param name
	 * @param email
	 * @param phone
	 * @return
	 */
	public String getPassword(String name, String email, String phone) {
		resultSet = null;
		String password = "";
		try {
			preparedStatement = setConnection().prepareStatement("SELECT password FROM user WHERE name = ? AND email = ? AND phone = ?");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, phone);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				password = resultSet.getString("password");
			}else {
				Alert message = new Alert(AlertType.ERROR);
				message.setTitle("Error");
				message.setContentText("Information provided is not correct");
				message.show();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return password;
	}
	/**
	 * Method to retrieve all coming soon movies
	 * @return
	 */
	public ObservableList<Movie> getAllComingSoon() {
		ObservableList<Movie> comingSoonMovies = FXCollections.observableArrayList();
		ResultSet resultSet = null;
		try {
			preparedStatement = setConnection().prepareStatement("SELECT * FROM comingsoonmovies");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				comingSoonMovies.add(new Movie(resultSet.getString("title"),
						                     resultSet.getString("cover"),
						                     resultSet.getString("year"),
						                     resultSet.getString("genre")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return comingSoonMovies;
	}
	
	public void close() {
		try {
			setConnection().close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
