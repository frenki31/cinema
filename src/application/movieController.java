package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class movieController {
	
	@FXML
	private ImageView coverPhoto;
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private Label yearLabel;
	
	@FXML
	private Label genreLabel;
	
	@FXML
	private Button filmBtn;
	
	@FXML
	private VBox vBox;
	
	public void setMovie(Movie movie) {
		titleLabel.setText(movie.getTitle());
		coverPhoto.setImage(new Image(String.valueOf(new File(movie.getCover()))));
		yearLabel.setText(movie.getReleaseYear());
		genreLabel.setText(movie.getGenre());
	}
	
	@FXML
	public void clickToOpen(MouseEvent event) {
		vBox.setOnMouseClicked(e -> {
			
		});
	}
} 
