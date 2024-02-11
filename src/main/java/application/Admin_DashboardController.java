package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import algorithms.BCrypt;

public class Admin_DashboardController implements Initializable {
    DBQuery query = new DBQuery();
    @FXML
    private Label numberLabel;
    @FXML
    private TextField userField, oldPassField, newPassField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberLabel.setText(String.valueOf(query.countMovies()));
    }
    @FXML
    public void changePassword(ActionEvent event) {
        if (userField.getText().isEmpty() || oldPassField.getText().isEmpty()
                || newPassField.getText().isEmpty()) {
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        } else {
            String newHashPassword = BCrypt.hashpw(newPassField.getText(), BCrypt.gensalt());
            if (BCrypt.checkpw(oldPassField.getText(), query.getPassword(userField.getText()))){
                int result = query.updatePassword(userField.getText(), newHashPassword,
                        query.getPassword(userField.getText()));
                if (result == 1) {
                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                    message.setTitle("Updated");
                    message.setContentText("Your password has been updated successfully");
                    message.show();
                    userField.setText("");
                    ;
                    oldPassField.setText("");
                    ;
                    newPassField.setText("");
                    ;
                } else {
                    Alert message = new Alert(Alert.AlertType.ERROR);
                    message.setTitle("ERROR");
                    message.setContentText("Username and password do not match!");
                    message.show();
                }
            }
        }
    }
}