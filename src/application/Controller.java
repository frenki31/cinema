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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable{
	@FXML
	private VBox vBox; 
	private Parent parent;
	private Button closeButton1, closeButton2;
	
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
	public void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Alert message = new Alert(AlertType.CONFIRMATION);
		message.setTitle("Exit");
		message.setContentText("Do you want to exit?");
		if(message.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
	}
}
