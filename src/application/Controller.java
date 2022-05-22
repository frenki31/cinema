package application;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable{
	
	double x,y=0;
	@FXML
	private VBox vBox; 
	@FXML
	private Parent parent;
	@FXML
	private Button backBtn1, backBtn2;
	@FXML
	private Stage stage;
	@FXML
	private AnchorPane scene;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		TranslateTransition tt= new TranslateTransition(Duration.seconds(1),vBox);
		tt.setToX(vBox.getLayoutX()*14.5);
		tt.play();
		tt.setOnFinished((e) -> {
			try {
				parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
				vBox.getChildren().removeAll();
				vBox.getChildren().setAll(parent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	public void display_signin(ActionEvent event) {
		TranslateTransition tt= new TranslateTransition(Duration.seconds(1),vBox);
		tt.setToX(vBox.getLayoutX()*14.5);
		tt.play();
		tt.setOnFinished((e) -> {
			try {
				parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
				vBox.getChildren().removeAll();
				vBox.getChildren().setAll(parent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	public void display_signup(ActionEvent event) {
		TranslateTransition tt= new TranslateTransition(Duration.seconds(1),vBox);
		tt.setToX(11);
		tt.play();
		tt.setOnFinished((e) -> {
			try {
				parent = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
				vBox.getChildren().removeAll();
				vBox.getChildren().setAll(parent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	public void back(ActionEvent event) {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public void makeDraggable() {
		scene.setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});
		
		scene.setOnMouseDragged(event -> {
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX() - x);
			stage.setY(event.getScreenY() - y);
		});
	}
	
}
