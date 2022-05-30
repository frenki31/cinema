package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdminController implements Initializable{
	
    double x,y = 0;
	
    @FXML
    private Parent parent;
    @FXML
    private Button closeButton;
    @FXML
    private FontAwesomeIcon movieIcon;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private FontAwesomeIcon suggestedIcon;
    @FXML
    private Button moviesBtn;
    @FXML
    private BorderPane scene;
    @FXML
    private Button suggestedBtn;
    @FXML
    private FontAwesomeIcon userIcon;
    @FXML
    private Button usersBtn;   
    @FXML
    private VBox allBox;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	try {
			parent = FXMLLoader.load(getClass().getResource("AdminMovies.fxml"));
			allBox.getChildren().removeAll();
			allBox.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @FXML
    boolean close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
		return true;
    }

    @FXML
    void makeDraggable(MouseEvent event) {
    	scene.setOnMousePressed(e -> {
    		x = e.getSceneX();
    		y = e.getSceneY();
    	});
    	
    	scene.setOnMouseDragged(e -> {
    		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		stage.setX(e.getScreenX() - x);
    		stage.setY(e.getScreenY() - y);
    	});
    }

    @FXML
    void maximize(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if(stage.isMaximized())
			stage.setMaximized(false);
		else
			stage.setMaximized(true);
    }

    @FXML
    void minimize(ActionEvent event) {
		try {
			parent = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void openMovies(ActionEvent event) {
    	try {
    		moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		suggestedBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		usersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		userIcon.setFill(Color.web("#A1A1A1"));
    		movieIcon.setFill(Color.WHITE);
    		suggestedIcon.setFill(Color.web("#A1A1A1"));
    		
			parent = FXMLLoader.load(getClass().getResource("AdminMovies.fxml"));
			allBox.getChildren().removeAll();
			allBox.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void openSuggested(ActionEvent event) {
    	try {
    		moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		movieIcon.setFill(Color.web("#A1A1A1"));
    		suggestedBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		usersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		suggestedIcon.setFill(Color.WHITE);
    		userIcon.setFill(Color.web("#A1A1A1"));
    		
			parent = FXMLLoader.load(getClass().getResource("AdminSuggested.fxml"));
			allBox.getChildren().removeAll();
			allBox.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void openUsers(ActionEvent event) {
    	try {
    		moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		movieIcon.setFill(Color.web("#A1A1A1"));
    		usersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		suggestedBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		userIcon.setFill(Color.WHITE);
    		suggestedIcon.setFill(Color.web("#A1A1A1"));
    		
			parent = FXMLLoader.load(getClass().getResource("AdminUsers.fxml"));
			allBox.getChildren().removeAll();
			allBox.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
