package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class AdminUserController implements Initializable{
	ObservableList<User> userList = FXCollections.observableArrayList();
    DBQueries queries = new DBQueries();
    
    @FXML
    private Parent parent;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, String> phoneCol;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TableColumn<User, Integer> userIDCol;
    @FXML
    private VBox usersBox;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button clearBtn;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTable();
	}

    @FXML
    void refreshTable() {
    	userList.clear();
    	userList = FXCollections.observableArrayList(queries.getAllUsers());
    	usersTable.setItems(userList);
    }
    
    public void loadTable() {
    	queries.setConnection();
    	refreshTable();
    	
    	userIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    	emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    	phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
    	passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
    }
    
    @FXML
    public void deleteUser(ActionEvent event) {
    	if (queries.deleteUser(usersTable.getSelectionModel().getSelectedItem().getId()) == 1) {
    		usersTable.getItems().removeAll(usersTable.getSelectionModel().getSelectedItem());
    		Alert message = new Alert(AlertType.INFORMATION);
			message.setTitle("Deleted");
			message.setContentText("User Deleted Successfully");
			message.show();
		}else {
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("ERROR");
			message.setContentText("Something went wrong! Please try again...");
			message.show();
		}
    }
    
    @FXML
    public void retrieveUser(MouseEvent event) {
    	nameTextField.setText(usersTable.getSelectionModel().getSelectedItem().getName());
    	emailTextField.setText(usersTable.getSelectionModel().getSelectedItem().getEmail());
    	phoneTextField.setText(usersTable.getSelectionModel().getSelectedItem().getPhoneNo());
    	passwordTextField.setText(usersTable.getSelectionModel().getSelectedItem().getPassword());
    }
    
    @FXML
    public void updateUser(ActionEvent event) {
    	if (nameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
    			phoneTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
    		messageLabel.setText("Please fill all the blanks");
    	} else if(!messageLabel.getText().isEmpty()) {
    		Alert message = new Alert(AlertType.ERROR);
			message.setTitle("ERROR");		
			message.setContentText("You do not fulfill all criteria");
			message.show();
    	}else if (queries.updateUser(usersTable.getSelectionModel().getSelectedItem().getId(), nameTextField.getText(), 
    			emailTextField.getText(), phoneTextField.getText(), passwordTextField.getText()) == 1) {
    		 Alert message = new Alert(AlertType.INFORMATION);
			 message.setTitle("Updated");
			 message.setContentText("User Updated Successfully");
			 message.show();
    	}else {
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("ERROR");		
			message.setContentText("Something went wrong! Please try again...");
			message.show();
		 }
    }
    
    @FXML
	public void nameRegex(javafx.scene.input.KeyEvent event) {
		nameTextField.setOnKeyTyped(e -> {
			String regex = "[a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(nameTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a valid name");
			}else {
				messageLabel.setText("");
			}
				
		});
	}
	
	@FXML
	public void emailRegex(javafx.scene.input.KeyEvent event) {
		emailTextField.setOnKeyTyped(e -> {
			String regex = "^(?=.*[a-zA-Z0-9])(?=.*[._-]).{5,10}[@](?=.*[a-z]).{4,10}[.]{1}(?=.*[a-zA-Z]).{2,5}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(emailTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a valid email");
			}else {
				messageLabel.setText("");
			}
		});
	}
	
	@FXML
	public void phoneRegex(javafx.scene.input.KeyEvent event) {
		phoneTextField.setOnKeyTyped(e -> {
			String regex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(phoneTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a valid phone number");
			}else {
				messageLabel.setText("");
			}
		});
	}
	
	@FXML
	public void passwordRegex(javafx.scene.input.KeyEvent event) {
		passwordTextField.setOnKeyTyped(e -> {
			String regex = "^(?=.*[a-zA-Z0-9])(?=.*[@#$%^&*+=-]).{8,15}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(passwordTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a stronger password! This is easily guessed.");
			}else {
				messageLabel.setText("");
			}
		});
	}
	
	@FXML
	public void clearFields(ActionEvent ev) {
		nameTextField.setText(null);
		emailTextField.setText(null);
		phoneTextField.setText(null);
		passwordTextField.setText(null);
	}
}
