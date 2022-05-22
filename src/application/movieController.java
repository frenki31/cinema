package application;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class movieController {
	
	@FXML
	private ImageView coverPhoto;
	
	public void getCover(Movie movie) {
		Image image = new Image(getClass().getResourceAsStream(movie.getCover()));
		coverPhoto.setImage(image);
	}
} 
