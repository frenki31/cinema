package application;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Database class where all the database manipulation are prepared
 * @author user
 *
 */
public class DBQuery {

    private final String URL = "jdbc:sqlserver://DESKTOP-QT7MTFJ\\SQLEXPRESS;databaseName=CINEMATRIX;"
            + "encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2";
    private final String user = "sa";
    private final String pass = "1234";
    private Connection connect;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DBQuery() {
        setConnection();
    }
    /**
     * This method establishes the connection to the database
     * @return
     */
    public Connection setConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(URL,user,pass);
            if(connect == null)
                System.out.println("Error");
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connect;
    }
    /**
     * Method to get all movies
     * @return
     */
    public ObservableList<Movie> getAllMovies(String user) {
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT * FROM MOVIE WHERE ADMIN_ID = (SELECT ADMIN_ID FROM ADMIN WHERE ADMIN_USER = ?)");
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                allMovies.add(new Movie(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getDouble("MOVIE_BUDGET"),
                        resultSet.getString("MOVIE_OVERVIEW"),
                        resultSet.getDouble("MOVIE_REVENUE"),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate(),
                        resultSet.getTime("MOVIE_RUNTIME").toLocalTime(),
                        resultSet.getString("MOVIE_STATUS"),
                        resultSet.getDouble("MOVIE_RATING"),
                        resultSet.getInt("MOVIE_RATINGS_NR"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getInt("MOVIE_TOTAL_RATING"),
                        resultSet.getInt("ADMIN_ID")));
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
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TITLE,MOVIE_COVER,MOVIE_RELEASE FROM MOVIE");
            resultSet = preparedStatement.executeQuery();
            titleCoverYear = new ArrayList<Movie>();

            while(resultSet.next()){
                titleCoverYear.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate()));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return titleCoverYear;
    }
    /**
     * Method to get all genres for corresponding movie
     * @param title
     * @return
     */
    public List<String> getAllGenres(String title) {
        List<String> genres = null;
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT STRING_AGG(GENRE_CATEGORY, ', ') AS CATEGORY FROM MOVIE JOIN MOVIE_GENRE ON MOVIE.MOVIE_ID = MOVIE_GENRE.MOVIE_ID WHERE MOVIE_TITLE = ? GROUP BY MOVIE.MOVIE_TITLE");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            genres = new ArrayList<String>();

            while(resultSet.next()){
                genres.add(new String(resultSet.getString("CATEGORY")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return genres;
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
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TITLE,MOVIE_COVER,MOVIE_RELEASE FROM MOVIE WHERE MOVIE_TITLE LIKE ?");
            preparedStatement.setString(1, "%"+title+"%");
            resultSet = preparedStatement.executeQuery();
            searchList = FXCollections.observableArrayList();

            while(resultSet.next()){
                searchList.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate()));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return searchList;
    }
    /**
     * Method to retrieve all coming soon movies
     * @return
     */
    public ObservableList<Movie> getAllComingSoon() {
        ObservableList<Movie> comingSoonMovies = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TITLE,MOVIE_COVER,MOVIE_RELEASE FROM MOVIE WHERE MOVIE_STATUS != 'Released'");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                comingSoonMovies.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate()));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return comingSoonMovies;
    }
    /**
     * Method to retrieve all genres of coming soon movies
     * @return
     */
    public List<String> getAllGenresComingSoon(String title) {
        List<String> genres = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT STRING_AGG(GENRE_CATEGORY, ', ') AS CATEGORY FROM MOVIE JOIN MOVIE_GENRE ON MOVIE.MOVIE_ID = MOVIE_GENRE.MOVIE_ID WHERE MOVIE_STATUS != 'Released'");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            genres = new ArrayList<String>();

            while(resultSet.next()){
                genres.add(new String(resultSet.getString("CATEGORY")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return genres;
    }
    public int addMovie(String filmTitle,String budget,String overview,String revenue,LocalDate release, String runtime, String status, String cover, String username) {
        int result = 0;
        String sql = "INSERT INTO MOVIE(MOVIE_TITLE,MOVIE_BUDGET,MOVIE_OVERVIEW,MOVIE_REVENUE,MOVIE_RELEASE,MOVIE_RUNTIME,MOVIE_STATUS,MOVIE_COVER,ADMIN_ID) VALUES (?,?,?,?,?,?,?,?,(SELECT ADMIN_ID FROM ADMIN WHERE ADMIN_USER = ?))";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, filmTitle);
            preparedStatement.setString(2, budget);
            preparedStatement.setString(3, overview);
            preparedStatement.setString(4, revenue);
            preparedStatement.setDate(5, Date.valueOf(release));
            preparedStatement.setString(6, runtime);
            preparedStatement.setString(7, status);
            preparedStatement.setString(8, cover);
            preparedStatement.setString(9, username);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public ObservableList<Cast> getCastForMovie(String title){
        ObservableList<Cast> cast = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("EXEC SP_GET_MOVIE_ACTORS @title = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                cast.add(new Cast(resultSet.getString("NAME"),
                        resultSet.getString("CAST_CHARACTER"),
                        resultSet.getInt("CAST_ORDER")));
            }
        }catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            close();
        }
        return cast;
    }
    /**
     * Method to classify the movies by their genre
     * @param genre
     * @return
     */
    public ObservableList<Movie> clasifyMoviesByGenres(String genre) {
        ObservableList<Movie> searchList = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TITLE, MOVIE_COVER, MOVIE_RELEASE FROM MOVIE JOIN MOVIE_GENRE ON MOVIE.MOVIE_ID = MOVIE_GENRE.MOVIE_ID WHERE GENRE_CATEGORY = ?");
            preparedStatement.setString(1, genre);
            resultSet = preparedStatement.executeQuery();
            searchList = FXCollections.observableArrayList();

            while(resultSet.next()){
                searchList.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate()));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return searchList;
    }
    public ObservableList<Movie> clasifyMoviesByGenreAndYear(String genre, int first, int second) {
        ObservableList<Movie> searchList = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TITLE, MOVIE_COVER, MOVIE_RELEASE FROM MOVIE JOIN MOVIE_GENRE ON MOVIE.MOVIE_ID = MOVIE_GENRE.MOVIE_ID WHERE GENRE_CATEGORY = ? AND YEAR(MOVIE_RELEASE) BETWEEN ? AND ?");
            preparedStatement.setString(1, genre);
            preparedStatement.setInt(2, first);
            preparedStatement.setInt(3, second);
            resultSet = preparedStatement.executeQuery();
            searchList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                searchList.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate()));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return searchList;
    }
    public ObservableList<Movie> clasifyMoviesByYear(int first, int second) {
        ObservableList<Movie> searchList = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TITLE, MOVIE_COVER, MOVIE_RELEASE FROM MOVIE WHERE YEAR(MOVIE_RELEASE) BETWEEN ? AND ?");
            preparedStatement.setInt(1, first);
            preparedStatement.setInt(2, second);
            resultSet = preparedStatement.executeQuery();
            searchList = FXCollections.observableArrayList();

            while(resultSet.next()){
                searchList.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate()));
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
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TITLE,MOVIE_RUNTIME,MOVIE_RELEASE,MOVIE_OVERVIEW,MOVIE_COVER,MOVIE_RATING FROM MOVIE WHERE MOVIE_TITLE = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                movies.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getTime("MOVIE_RUNTIME").toLocalTime(),
                        resultSet.getDate("MOVIE_RELEASE").toLocalDate(),
                        resultSet.getString("MOVIE_OVERVIEW"),
                        resultSet.getString("MOVIE_COVER"),
                        resultSet.getDouble("MOVIE_RATING")));
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
            preparedStatement = setConnection().prepareStatement("SELECT DISTINCT GENRE_CATEGORY FROM MOVIE_GENRE");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                genres.add(new String(resultSet.getString("GENRE_CATEGORY")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return genres;
    }
    /**
     * Method to retrieve all countries where a movie was filmed
     * @param title
     * @return
     */
    public ObservableList<String> getCountries(String title){
        ObservableList<String> countries = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT STRING_AGG(COUNTRY_NAME, ', ') AS COUNTRY FROM MOVIE JOIN MOVIE_COUNTRY ON MOVIE.MOVIE_ID = MOVIE_COUNTRY.MOVIE_ID JOIN COUNTRY ON MOVIE_COUNTRY.COUNTRY_CODE = COUNTRY.COUNTRY_CODE WHERE MOVIE_TITLE = ? GROUP BY MOVIE.MOVIE_TITLE");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                countries.add(resultSet.getString("COUNTRY"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return countries;
    }
    public ObservableList<Movie> getTopRatedMovies(){
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("IF EXISTS (SELECT 1 FROM MOVIE WHERE MOVIE_RATING > 7) SELECT MOVIE_TITLE, MOVIE_RATING FROM MOVIE WHERE MOVIE_RATING > 7 GROUP BY MOVIE_TITLE, MOVIE_RATING");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                movies.add(new Movie(resultSet.getString("MOVIE_TITLE"),
                        resultSet.getDouble("MOVIE_RATING")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return movies;
    }
    /**
     * Method which deletes a movie
     * @param id
     * @return
     */
    public int deleteMovie(int id) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM MOVIE WHERE MOVIE_ID = ?");
            preparedStatement.setInt(1, id);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int updateMovie(int id,String title,String budget, String description,String revenue,LocalDate release,String runtime,String status,String cover) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("UPDATE MOVIE SET MOVIE_TITLE = ?,MOVIE_BUDGET = ?,MOVIE_OVERVIEW = ?,MOVIE_REVENUE = ?,MOVIE_RELEASE = ?,MOVIE_RUNTIME = ?,MOVIE_STATUS = ?, MOVIE_COVER = ? WHERE MOVIE_ID = ?");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, budget);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, revenue);
            preparedStatement.setDate(5, Date.valueOf(release));
            preparedStatement.setString(6, runtime);
            preparedStatement.setString(7, status);
            preparedStatement.setString(9, cover);
            preparedStatement.setInt(9, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int addCountry(String country, String iso) {
        int result = 0;
        String sql = "INSERT INTO COUNTRY(COUNTRY_ISO, COUNTRY_NAME) VALUES (?,?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, iso);
            preparedStatement.setString(2, country);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int updateCountry(int code,String country,String iso)
    {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("UPDATE COUNTRY SET COUNTRY_NAME = ?, COUNTRY_ISO = ? WHERE COUNTRY_CODE = ?");
            preparedStatement.setString(1, country);
            preparedStatement.setString(2, iso);
            preparedStatement.setInt(3, code);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int deleteCountry(int id) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM COUNTRY WHERE COUNTRY_CODE = ?");
            preparedStatement.setInt(1, id);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> all = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT * FROM COUNTRY");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all.add(new Country(
                        resultSet.getInt("COUNTRY_CODE"),
                        resultSet.getString("COUNTRY_ISO"),
                        resultSet.getString("COUNTRY_NAME")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return all;
    }

    public ObservableList<Movie> getMovieTitles() {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_ID, MOVIE_TITLE FROM MOVIE");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                movies.add(new Movie(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return movies;
    }

    public ObservableList<MovieCountry> getMovieTitlesforCountry() {
        ObservableList<MovieCountry> movies = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE.MOVIE_ID,MOVIE.MOVIE_TITLE, COUNTRY.COUNTRY_CODE, COUNTRY_NAME FROM COUNTRY JOIN MOVIE_COUNTRY ON MOVIE_COUNTRY.COUNTRY_CODE = COUNTRY.COUNTRY_CODE JOIN MOVIE ON MOVIE_COUNTRY.MOVIE_ID = MOVIE.MOVIE_ID");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                movies.add(new MovieCountry(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getInt("COUNTRY_CODE"),
                        resultSet.getString("COUNTRY_NAME")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return movies;
    }

    public int addMovieCountry(String movieTitle, String countryName) {
        int result = 0;
        String sql = "INSERT INTO MOVIE_COUNTRY(MOVIE_ID, COUNTRY_CODE) VALUES ((SELECT MOVIE_ID FROM MOVIE WHERE MOVIE_TITLE = ?),(SELECT COUNTRY_CODE FROM COUNTRY WHERE COUNTRY_NAME = ?))";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setString(2, countryName);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int deleteMovieCountry(int id, int code) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM MOVIE_COUNTRY WHERE MOVIE_ID = ? AND COUNTRY_CODE = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, code);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public ObservableList<MovieGenre> getMovieGenres() {
        ObservableList<MovieGenre> movies = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE.MOVIE_ID,MOVIE_TITLE,GENRE_CATEGORY FROM MOVIE_GENRE JOIN MOVIE ON MOVIE_GENRE.MOVIE_ID = MOVIE.MOVIE_ID");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                movies.add(new MovieGenre(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getString("GENRE_CATEGORY")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return movies;
    }
    public int addMovieGenre(String movieTitle, String genre) {
        int result = 0;
        String sql = "INSERT INTO MOVIE_GENRE(MOVIE_ID, GENRE_CATEGORY) VALUES ((SELECT MOVIE_ID FROM MOVIE WHERE MOVIE_TITLE = ?),?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setString(2, genre);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int deleteMovieGenre(int id, String genre) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM MOVIE_GENRE WHERE MOVIE_ID = ? AND GENRE_CATEGORY = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, genre);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int deleteMovieCompany(int id, int companyCode) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM MOVIE_COMPANY WHERE MOVIE_ID = ? AND COMPANY_CODE = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, companyCode);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public ObservableList<Company> getCompany() {
        ObservableList<Company> all = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT * FROM COMPANY");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all.add(new Company(
                        resultSet.getInt("COMPANY_CODE"),
                        resultSet.getString("COMPANY_NAME"),
                        resultSet.getDouble("COMPANY_REVENUE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return all;
    }

    public int deleteCompany(int id) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM COMPANY WHERE COMPANY_CODE = ?");
            preparedStatement.setInt(1, id);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public ObservableList<MovieCompany> getMovieCompany() {
        ObservableList<MovieCompany> movies = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE.MOVIE_ID,MOVIE.MOVIE_TITLE,COMPANY.COMPANY_CODE, COMPANY.COMPANY_NAME FROM COMPANY JOIN MOVIE_COMPANY ON MOVIE_COMPANY.COMPANY_CODE = COMPANY.COMPANY_CODE JOIN MOVIE ON MOVIE_COMPANY.MOVIE_ID = MOVIE.MOVIE_ID");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                movies.add(new MovieCompany(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getInt("COMPANY_CODE"),
                        resultSet.getString("COMPANY_NAME")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return movies;
    }

    public int addCompany(String company, double revenue) {
        int result = 0;
        String sql = "INSERT INTO COMPANY(COMPANY_NAME, COMPANY_REVENUE) VALUES (?,?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, company);
            preparedStatement.setDouble(2, revenue);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int updateCompany(int id,String company, double revenue) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("UPDATE COMPANY SET COMPANY_NAME = ?, COMPANY_REVENUE = ? WHERE COMPANY_CODE = ?");
            preparedStatement.setString(1, company);
            preparedStatement.setDouble(2, revenue);
            preparedStatement.setInt(3, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int addMovieCompany(String movieTitle, String company) {
        int result = 0;
        String sql = "INSERT INTO MOVIE_COMPANY(MOVIE_ID, COMPANY_CODE) VALUES ((SELECT MOVIE_ID FROM MOVIE WHERE MOVIE_TITLE = ?),(SELECT COMPANY_CODE FROM COMPANY WHERE COMPANY_NAME = ?))";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setString(2, company);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int deleteMovieLanguage(int movId, int langId) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM MOVIE_LANGUAGE WHERE MOVIE_ID = ? AND LANGUAGE_ID = ?");
            preparedStatement.setInt(1, movId);
            preparedStatement.setInt(2, langId);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public ObservableList<Language> getLanguage() {
        ObservableList<Language> all = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT * FROM LANGUAGE");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all.add(new Language(
                        resultSet.getInt("LANGUAGE_ID"),
                        resultSet.getString("LANGUAGE_NAME"),
                        resultSet.getString("LANGUAGE_CODE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return all;
    }
    public int deleteLanguage(int id) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM LANGUAGE WHERE LANGUAGE_ID = ?");
            preparedStatement.setInt(1, id);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public ObservableList<MovieLanguage> getMovieLanguage() {
        ObservableList<MovieLanguage> movies = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE.MOVIE_ID,MOVIE.MOVIE_TITLE,LANGUAGE.LANGUAGE_ID,LANGUAGE.LANGUAGE_NAME,LANGUAGE.LANGUAGE_CODE FROM MOVIE JOIN MOVIE_LANGUAGE ON MOVIE.MOVIE_ID = MOVIE_LANGUAGE.MOVIE_ID JOIN LANGUAGE ON LANGUAGE.LANGUAGE_ID = MOVIE_LANGUAGE.LANGUAGE_ID");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                movies.add(new MovieLanguage(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getInt("LANGUAGE_ID"),
                        resultSet.getString("LANGUAGE_NAME"),
                        resultSet.getString("LANGUAGE_CODE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return movies;
    }
    public ObservableList<String> getUsername() {
        ObservableList<String> users = FXCollections.observableArrayList();
        resultSet = null;
        try{
            preparedStatement = setConnection().prepareStatement("SELECT ADMIN_USER FROM ADMIN");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                users.add(new String(resultSet.getString("ADMIN_USER")));
            }
        }catch (SQLException e){
            e.printStackTrace();
            close();
        }
        return users;
    }
    public String getPassword(String user) {
        String password = null;
        resultSet = null;
        try{
            preparedStatement = setConnection().prepareStatement("SELECT ADMIN_PASS FROM ADMIN WHERE ADMIN_USER = ?");
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                password = resultSet.getString("ADMIN_PASS");
            }
        }catch (SQLException e){
            e.printStackTrace();
            close();
        }
        return password;
    }

    public int addLanguage(String lang, String code) {
        int result = 0;
        String sql = "INSERT INTO LANGUAGE(LANGUAGE_NAME, LANGUAGE_CODE) VALUES (?,?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, lang);
            preparedStatement.setString(2, code);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int updatePassword(String user, String newPass, String oldPass){
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("UPDATE ADMIN SET ADMIN_PASS = ? WHERE ADMIN_USER = ? AND ADMIN_PASS = ?");
            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, user);
            preparedStatement.setString(3, oldPass);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int updateLanguage(int id,String language, String code) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("UPDATE LANGUAGE SET LANGUAGE_NAME = ?, LANGUAGE_CODE = ? WHERE LANGUAGE_ID = ?");
            preparedStatement.setString(1, language);
            preparedStatement.setString(2, code);
            preparedStatement.setInt(3, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int addMovieLanguage(String movieTitle, String language) {
        int result = 0;
        String sql = "INSERT INTO MOVIE_LANGUAGE(MOVIE_ID, LANGUAGE_ID) VALUES ((SELECT MOVIE_ID FROM MOVIE WHERE MOVIE_TITLE = ?),(SELECT LANGUAGE_ID FROM LANGUAGE WHERE LANGUAGE_NAME = ?))";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setString(2, language);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int deleteCast(int movId, int personId) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM CAST WHERE MOVIE_ID = ? AND PERSON_ID = ?");
            preparedStatement.setInt(1, movId);
            preparedStatement.setInt(2, personId);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int deleteCrew(int movId, int personId) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM CREW WHERE MOVIE_ID = ? AND PERSON_ID = ?");
            preparedStatement.setInt(1, movId);
            preparedStatement.setInt(2, personId);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public ObservableList<Person> getActors() {
        ObservableList<Person> all = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT * FROM PERSON WHERE PERSON_TYPE = 'Cast'");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all.add(new Person(
                        resultSet.getInt("PERSON_ID"),
                        resultSet.getString("PERSON_FNAME"),
                        resultSet.getString("PERSON_MNAME"),
                        resultSet.getString("PERSON_LNAME"),
                        resultSet.getString("PERSON_GENDER"),
                        resultSet.getString("PERSON_TYPE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return all;
    }
    public ObservableList<Person> getPerson() {
        ObservableList<Person> all = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT * FROM PERSON");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all.add(new Person(
                        resultSet.getInt("PERSON_ID"),
                        resultSet.getString("PERSON_FNAME"),
                        resultSet.getString("PERSON_MNAME"),
                        resultSet.getString("PERSON_LNAME"),
                        resultSet.getString("PERSON_GENDER"),
                        resultSet.getString("PERSON_TYPE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return all;
    }
    public ObservableList<Person> getCrewMembers() {
        ObservableList<Person> all = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT * FROM PERSON WHERE PERSON_TYPE = 'Crew'");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all.add(new Person(
                        resultSet.getInt("PERSON_ID"),
                        resultSet.getString("PERSON_FNAME"),
                        resultSet.getString("PERSON_MNAME"),
                        resultSet.getString("PERSON_LNAME"),
                        resultSet.getString("PERSON_GENDER"),
                        resultSet.getString("PERSON_TYPE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return all;
    }

    public int deletePerson(int id) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("DELETE FROM PERSON WHERE PERSON_ID = ?");
            preparedStatement.setInt(1, id);
            result= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public ObservableList<Cast> getCast() {
        ObservableList<Cast> cast = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE.MOVIE_ID,MOVIE.MOVIE_TITLE,PERSON.PERSON_ID,concat(PERSON.PERSON_FNAME,' ',PERSON.PERSON_MNAME,' ',PERSON.PERSON_LNAME) AS NAME,CAST.CAST_CHARACTER,CAST.CAST_ORDER FROM PERSON JOIN CAST ON PERSON.PERSON_ID = CAST.PERSON_ID JOIN MOVIE ON MOVIE.MOVIE_ID = CAST.MOVIE_ID");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                cast.add(new Cast(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getInt("PERSON_ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("CAST_CHARACTER"),
                        resultSet.getInt("CAST_ORDER")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return cast;
    }

    public int addPerson(String fname, String mname, String lname, String gender, String type) {
        int result = 0;
        String sql = "INSERT INTO PERSON(PERSON_FNAME, PERSON_MNAME, PERSON_LNAME, PERSON_GENDER, PERSON_TYPE) VALUES (?,?,?,?,?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, mname);
            preparedStatement.setString(3, lname);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, type);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int updatePerson(int id,String fname, String mname, String lname, String gender, String type) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("UPDATE PERSON SET PERSON_FNAME=?, PERSON_MNAME=?, PERSON_LNAME=?, PERSON_GENDER=?, PERSON_TYPE=? WHERE PERSON_ID=?");
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, mname);
            preparedStatement.setString(3, lname);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, type);
            preparedStatement.setInt(6, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int addCast(String movieTitle, String actor, String character, int order) {
        int result = 0;
        String sql = "INSERT INTO CAST(MOVIE_ID, PERSON_ID, CAST_CHARACTER, CAST_ORDER) VALUES ((SELECT MOVIE_ID FROM MOVIE WHERE MOVIE_TITLE = ?),(SELECT PERSON_ID FROM PERSON WHERE CONCAT(PERSON_FNAME,' ',PERSON_MNAME,' ',PERSON_LNAME) = ?),?,?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setString(2, actor);
            preparedStatement.setString(3, character);
            preparedStatement.setInt(4, order);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }

    public int addCrew(String movieTitle, String person, String job) {
        int result = 0;
        String sql = "INSERT INTO CREW(MOVIE_ID, PERSON_ID, CREW_JOBTITLE) VALUES ((SELECT MOVIE_ID FROM MOVIE WHERE MOVIE_TITLE = ?),(SELECT PERSON_ID FROM PERSON WHERE CONCAT(PERSON_FNAME,' ',PERSON_MNAME,' ',PERSON_LNAME) = ?),?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setString(2, person);
            preparedStatement.setString(3, job);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public ObservableList<Crew> getCrew() {
        ObservableList<Crew> crew = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("EXEC SP_GET_CREW");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                crew.add(new Crew(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getInt("PERSON_ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("CREW_JOBTITLE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return crew;
    }
    public ObservableList<MovieTrailer> getTrailers() {
        ObservableList<MovieTrailer> trailers = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT MOVIE_TRAILER.MOVIE_ID,MOVIE.MOVIE_TITLE,TRAILER_ID,TRAILER,TRAILER_LINK FROM MOVIE_TRAILER JOIN MOVIE ON MOVIE_TRAILER.MOVIE_ID = MOVIE.MOVIE_ID");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                trailers.add(new MovieTrailer(
                        resultSet.getInt("MOVIE_ID"),
                        resultSet.getString("MOVIE_TITLE"),
                        resultSet.getInt("TRAILER_ID"),
                        resultSet.getString("TRAILER"),
                        resultSet.getString("TRAILER_LINK")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return trailers;
    }
    public int addTrailer(String title, String trailer, String link){
        int result = 0;
        String sql = "INSERT INTO MOVIE_TRAILER(MOVIE_ID, TRAILER, TRAILER_LINK) VALUES ((SELECT MOVIE_ID FROM MOVIE WHERE MOVIE_TITLE = ?),?,?)";
        try {
            preparedStatement = setConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, trailer);
            preparedStatement.setString(3, link);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int deleteTrailer(int movieId, int trailerId) {
        int result = 0;
        try{
            preparedStatement = setConnection().prepareStatement("DELETE FROM MOVIE_TRAILER WHERE MOVIE_ID = ? AND TRAILER_ID = ?");
            preparedStatement.setInt(1, movieId);
            preparedStatement.setInt(2, trailerId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return result;
    }
    public int updateTrailer(int movieId, int trailerId, String link) {
        int result = 0;
        try{
            preparedStatement = setConnection().prepareStatement("UPDATE MOVIE_TRAILER SET TRAILER_LINK=? WHERE MOVIE_ID = ? AND TRAILER_ID = ?");
            preparedStatement.setString(1, link);
            preparedStatement.setInt(2, movieId);
            preparedStatement.setInt(3, trailerId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return result;
    }
    public ObservableList<String> getTrailerForMovie(String title) {
        ObservableList<String> trailer = FXCollections.observableArrayList();
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT TRAILER FROM MOVIE_TRAILER JOIN MOVIE ON MOVIE.MOVIE_ID = MOVIE_TRAILER.MOVIE_ID WHERE MOVIE.MOVIE_TITLE = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                trailer.add(new String(
                        resultSet.getString("TRAILER")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return trailer;
    }
    public String getTrailerLinkForMovie(String title) {
        String trailer = "";
        resultSet = null;
        try {
            preparedStatement = setConnection().prepareStatement("SELECT TRAILER_LINK FROM MOVIE_TRAILER WHERE TRAILER = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                trailer = resultSet.getString("TRAILER_LINK");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return trailer;
    }
    public int countMovies(){
        int number = 0;
        resultSet = null;
        try{
            preparedStatement = setConnection().prepareStatement("SELECT COUNT(*) AS MOVIE_NUMBER FROM MOVIE");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                number = resultSet.getInt("MOVIE_NUMBER");
            }
        }catch (SQLException e){
            e.printStackTrace();
            close();
        }
        return number;
    }
    public int rateMovie(int rating, String title) {
        int result = 0;
        try {
            preparedStatement = setConnection().prepareStatement("UPDATE MOVIE SET MOVIE_TOTAL_RATING = MOVIE_TOTAL_RATING + ?, MOVIE_RATINGS_NR = MOVIE_RATINGS_NR +1 WHERE MOVIE_TITLE = ?");
            preparedStatement.setInt(1, rating);
            preparedStatement.setString(2, title);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            close();
        }
        return result;
    }
    /**
     * Method to close the DB connection
     */
    public void close() {
        try {
            setConnection().close();
            resultSet.close();
        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}