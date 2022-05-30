package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminLoginController {

	private String password;
	@FXML
	private Parent parent;
	@FXML
	private Button backButton; 
	@FXML
	private Button loginButton;
	@FXML
	private HBox nameBox;
	@FXML
	private TextField nameTextField;
	@FXML
	private HBox passwordBox;
	@FXML
	private PasswordField passwordTextField;
	@FXML
    private Button seeButton;
	@FXML
	private Label messageLabel;

	@FXML
	void Visible(MouseEvent event) {
		seeButton.setOnMousePressed(e -> {
			password = passwordTextField.getText();
			passwordTextField.clear();
			passwordTextField.setPromptText(password);
		});
	}
	
	@FXML
	void notVisible(MouseEvent event) {
		seeButton.setOnMouseReleased(e -> {
			passwordTextField.setText(password);
			passwordTextField.setPromptText("Password");
		});
	}

	@FXML
	void goBack(ActionEvent event) {
		try {
			Stage stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@FXML
	void goForward(ActionEvent event) {
		if(nameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty()) {
			messageLabel.setText("Fill all the blanks");
			nameBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
			passwordBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
		}
		else if(nameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
			messageLabel.setText("Please input username");
			nameBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
			passwordBox.setStyle("-fx-background-color: transparent; -fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
		}
		else if(passwordTextField.getText().isEmpty() && !nameTextField.getText().isEmpty()) {
			messageLabel.setText("Please input password");
			passwordBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
			nameBox.setStyle("-fx-background-color: transparent; -fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
		}
		else if(!(nameTextField.getText().equals("Frenki") && passwordTextField.getText().equals("Frenki3112") || 
				nameTextField.getText().equals("Klajdi") && passwordTextField.getText().equals("KlajdiJanku") ||
				nameTextField.getText().equals("Spiro") && passwordTextField.getText().equals("12345678"))){
			messageLabel.setText("Incorrect username or password");
			nameBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
			passwordBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
		}else if(nameTextField.getText().equals("Frenki") && passwordTextField.getText().equals("Frenki3112") || 
				nameTextField.getText().equals("Klajdi") && passwordTextField.getText().equals("KlajdiJanku") ||
				nameTextField.getText().equals("Spiro") && passwordTextField.getText().equals("12345678")){
			nameBox.setStyle("-fx-background-color: transparent; -fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
			passwordBox.setStyle("-fx-background-color: transparent; -fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");	
			try {
				Stage stage = new Stage();
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();
				parent = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
				Scene scene = new Scene(parent);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
