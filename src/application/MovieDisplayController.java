package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MovieDisplayController implements Initializable{
	double x,y = 0;
	private WebEngine engine;
	DBQueries queries = new DBQueries();
	
	@FXML
	private Parent parent;
	@FXML
	private Stage stage;
	@FXML
	private VBox scene;
	@FXML
    private ImageView cover;
    @FXML
    private Label description, duration, genre, year;
    @FXML
    private Label title;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button playButton;
    @FXML
    private Button openMovieButton;
    @FXML
    private WebView trailerMedia = new WebView();
    @FXML
    private HBox trailerBox;
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = trailerMedia.getEngine();
	}
    /**
     * Method to set all the data for movie display
     * @param movie
     */
    public void setData(Movie movie) {
    	Image image = new Image(String.valueOf(new File(movie.getCover())));
		cover.setImage(image);
		title.setText(movie.getTitle());
		duration.setText(movie.getDuration());
		year.setText(movie.getReleaseYear());
		genre.setText(movie.getGenre());
		description.setText(movie.getDescription());
		engine.load(movie.getTrailer());
    }
    
    @FXML
	public void close(ActionEvent event) {
    	Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		try {
			parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void minimize(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
	
	@FXML
	public void maximize(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if(stage.isMaximized())
			stage.setMaximized(false);
		else
			stage.setMaximized(true);
	}
	/**
	 * Method to open the movie
	 * @param event
	 * @throws URISyntaxException
	 */
	@FXML
	public void openMovie(ActionEvent event) throws URISyntaxException {
//		HBox hbox = new HBox();
		ObservableList<Movie> movieLinks = FXCollections.observableArrayList(queries.getMovieLink(title.getText()));
		for(Movie movie: movieLinks) {
			try {
//				FXMLLoader loader = new FXMLLoader();
//			    loader.setLocation(getClass().getResource("WatchMovie.fxml"));
//				VBox vbox = loader.load();
//				WatchMovieController wmc = loader.getController();
				WatchMovieController wmc = new WatchMovieController();
//				wmc.setLink(movie);
				Desktop.getDesktop().browse(new URL(wmc.setLink(movie)).toURI());
//				hbox.getChildren().add(vbox);
//				Stage stage = new Stage();
//				stage.initStyle(StageStyle.TRANSPARENT);
//				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//				stage.close();
//				Scene openScene = new Scene(hbox);
//				stage.setScene(openScene);
//				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
