package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class allMoviesController implements Initializable{
	@FXML
	private HBox hBox;
	ObservableList<Movie> movies;
	DBQueries queries = new DBQueries();
	DashboardController dc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		movies = FXCollections.observableArrayList(queries.getAllCovers());
		try {
			for(Movie movie: movies) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Movie.fxml"));
				VBox vbox = loader.load();
				movieController movieController = loader.getController();
				movieController.setMovie(movie);
				hBox.getChildren().removeAll();
				hBox.getChildren().add(vbox);
			}
		} catch (IOException e) {
	        // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ObservableList<Movie> searchByTitle(){
		movies = FXCollections.observableArrayList(queries.getAllCovers());
		try {
			for(Movie movie: movies) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Movie.fxml"));
				VBox vbox = loader.load();
				movieController movieController = loader.getController();
				movieController.setMovie(movie);
				hBox.getChildren().removeAll();
				hBox.getChildren().add(vbox);
			}
		} catch (IOException e) {
	        // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movies;
	}
}
