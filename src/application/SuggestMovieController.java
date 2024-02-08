package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SuggestMovieController {
	DBQueries queries = new DBQueries();
	@FXML
    private TextField directorTextField;
    @FXML
    private TextField genreTextField;
    @FXML
    private Button suggestMovieBtn;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField yearTextField;
    /**
     * Method that allows a user to suggest a movie
     * @param event
     */
	@FXML
	void suggestMovie(ActionEvent event) {
		if (titleTextField.getText().isEmpty() || directorTextField.getText().isEmpty() 
				|| yearTextField.getText().isEmpty() || genreTextField.getText().isEmpty()) {
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("ERROR");
			message.setContentText("Please fill in all the blanks");
		}else {
			int result = queries.addSuggested(titleTextField.getText(), directorTextField.getText(), 
					yearTextField.getText(), genreTextField.getText());
			if(result == 1) {
				Alert message = new Alert(AlertType.INFORMATION);
				message.setTitle("ADDED");
				message.setContentText("Your suggestion has been sent to the admin. Thank you!");
				titleTextField.setText("");;
				directorTextField.setText("");;
				yearTextField.setText("");
				genreTextField.setText("");
				
			}
			else {
				Alert message = new Alert(AlertType.ERROR);
				message.setTitle("ERROR");
				message.setContentText("An error occurred! You are not able to proceed");
			}
		}
	}
}
