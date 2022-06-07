package application;

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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WatchMovieController implements Initializable{
	
	private WebEngine engine;
	@FXML
	private Parent parent;
    @FXML
    private WebView watchMovieView;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = watchMovieView.getEngine();
	}
    
	public void setLink(Movie movie) {
		engine.load(movie.getMovieLink());
	}
	
	@FXML
	public void close(ActionEvent ev) {
		Stage stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
		stage.close();
		try {
			parent = FXMLLoader.load(getClass().getResource("MovieDisplay.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void minimize(ActionEvent ev) {
		Stage stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
	
	@FXML
	public void maximize(ActionEvent ev) {
		Stage stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
		if(stage.isMaximized()) 
			stage.setMaximized(false);
		else
			stage.setMaximized(true);
	}
}
