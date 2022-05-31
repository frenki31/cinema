package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WatchMovieController implements Initializable{
	
	private WebEngine engine;
    @FXML
    private WebView watchMovieView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = watchMovieView.getEngine();
	}
    
	public void setLink(Movie movie) {
		engine.load(movie.getMovieLink());
	}
    

}
