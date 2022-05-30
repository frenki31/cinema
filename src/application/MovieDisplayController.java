package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Button closeButton, minimizeButton, maximizeButton, pauseButton, playButton, openMovieButton;
    @FXML
    private WebView trailerMedia = new WebView();
    @FXML
    private HBox trailerBox;
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = trailerMedia.getEngine();
	}
    
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
	
	@FXML
	public void play(ActionEvent event) {
		
	}
	
	@FXML
	public void pause(ActionEvent event) {
		
	}
	
	@FXML
	public void openMovie(ActionEvent event) {
		
	}
}
