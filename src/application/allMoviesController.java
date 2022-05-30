package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class allMoviesController implements Initializable{
	
	DBQueries queries = new DBQueries();
	ObservableList<Movie> movies;
	@FXML
	private HBox hBox;

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
				hBox.getChildren().addAll(vbox);
			}
		} catch (IOException e) {
	        // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
