package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;

public class AddMovieController {
	DBQueries queries = new DBQueries();
	@FXML
	private VBox moviesBox;
	@FXML
	private Button backBtn;
	
	@FXML
    private Button addBtn;

    @FXML
    private TextField coverTextField;

    @FXML
    private TextField descrtiptionTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField trailerTextField;

    @FXML
    private TextField yearTextField;
    
    @FXML
    private Label message;

    @FXML
    void addMovie(ActionEvent event) {
    	if (titleTextField.getText().isEmpty() || durationTextField.getText().isEmpty() 
				|| coverTextField.getText().isEmpty() || trailerTextField.getText().isEmpty()
				|| descrtiptionTextField.getText().isEmpty() || yearTextField.getText().isEmpty() || genreTextField.getText().isEmpty()) {
			message.setText("Please fill all the blanks");
		}else {
			int result = queries.addMovie(titleTextField.getText(), durationTextField.getText(), 
					coverTextField.getText(), trailerTextField.getText(), descrtiptionTextField.getText(),
					yearTextField.getText(), genreTextField.getText());
			if(result == 1) {
				Alert message = new Alert(AlertType.INFORMATION);
				message.setTitle("ADDED");
				message.setContentText("The movie has been added successfully");
				titleTextField.setText("");;
				durationTextField.setText("");;
				coverTextField.setText("");;
				trailerTextField.setText("");
				descrtiptionTextField.setText("");
				yearTextField.setText("");
				genreTextField.setText("");
			}
			else {
				Alert message = new Alert(AlertType.ERROR);
				message.setTitle("ERROR");
				message.setContentText("ERROR!!! The user cannot be registered");
			}
		}
    }
    
    @FXML
    public void goBack(ActionEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("AdminMovies.fxml"));
			moviesBox.getChildren().removeAll();
			moviesBox.getChildren().setAll(parent);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
