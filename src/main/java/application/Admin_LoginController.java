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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import algorithms.BCrypt;

public class Admin_LoginController {
    DBQuery query = new DBQuery();
    double x,y = 0;
    private String password;
    @FXML
    private Stage stage;
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
    private VBox box;

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
            parent = FXMLLoader.load(getClass().getResource("/views/Dashboard.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method to check administrators login
     * @param event
     */
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
        else if(!(query.getUsername().contains(nameTextField.getText())) || !BCrypt.checkpw(passwordTextField.getText(), query.getPassword(nameTextField.getText()))){
            messageLabel.setText("Incorrect username or password");
            nameBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
            passwordBox.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 1 1 1 1");
        }else if(query.getUsername().contains(nameTextField.getText()) && BCrypt.checkpw(passwordTextField.getText(), query.getPassword(nameTextField.getText()))){
            nameBox.setStyle("-fx-background-color: transparent; -fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
            passwordBox.setStyle("-fx-background-color: transparent; -fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
            try {
                Stage stage = new Stage();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Page.fxml"));
                parent = loader.load();
                Admin_Controller ac = loader.getController();
                ac.setUser(nameTextField.getText());
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeDraggable() {
        box.setOnMousePressed(e ->{
            x = e.getSceneX();
            y = e.getSceneY();
        });

        box.setOnMouseDragged(e -> {
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });
    }
}
