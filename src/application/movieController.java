package application;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class movieController{
	
	DBQueries queries = new DBQueries();
	@FXML
	private Parent parent;
	@FXML 
	private ImageView coverPhoto;
	@FXML
	private Label genreLabel;
	@FXML
	private Label yearLabel;
	@FXML
	private Label titleLabel;
	@FXML
	private VBox vBox;
	@FXML
	private Button openMovieBtn;
	@FXML
	private Button filmBtn; 
	
	public void setMovie(Movie movie) {
		titleLabel.setText(movie.getTitle());
		coverPhoto.setImage(new Image(String.valueOf(new File(movie.getCover()))));
		yearLabel.setText(movie.getReleaseYear());
		genreLabel.setText(movie.getGenre());
	}
	/**
	 * Method to open the movie details page
	 * @param event
	 */
	@FXML
    public void clickToOpenMovie(ActionEvent event) {
		HBox hBox = new HBox();
       	hBox.setSpacing(30);
    	ObservableList<Movie> movies = FXCollections.observableArrayList(queries.movieDetails(titleLabel.getText()));
   		for (Movie movie: movies) {
   			try {
           		FXMLLoader loader = new FXMLLoader();
           		loader.setLocation(getClass().getResource("MovieDisplay.fxml"));
           		VBox vbox = loader.load();
            	MovieDisplayController mdc = loader.getController();
           		mdc.setData(movie);
           		hBox.getChildren().add(vbox);
           		Stage stage = new Stage();
           		stage.initStyle(StageStyle.TRANSPARENT);
           		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           		stage.close();
           		Scene scene = new Scene(hBox);
           		stage.setScene(scene);
           		stage.show();
        	}catch(IOException ex) {
       			ex.printStackTrace();
       		}  		
       	}
	}
} 
