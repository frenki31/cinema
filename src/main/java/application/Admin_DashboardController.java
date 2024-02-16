package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import algorithms.BCrypt;
import entities.Movie;
import javafx.scene.control.cell.PropertyValueFactory;

public class Admin_DashboardController implements Initializable {
    DBQuery query = new DBQuery();
    @FXML
    private Label numberLabel;
    @FXML
    private TextField userField, oldPassField, newPassField;
    @FXML
    private TableView<Movie> topRatedTable;
    @FXML
    private TableColumn<Movie, String> title;
    @FXML
    private TableColumn<Movie, Double> rating;
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        query.setConnection();
        numberLabel.setText(String.valueOf(query.countMovies()));
        movies.clear();
        movies = FXCollections.observableArrayList(query.getTopRatedMovies());
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        topRatedTable.setItems(movies);
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