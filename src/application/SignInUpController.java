package application;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignInUpController {
	
	private String password;
	private String rePassword; 
	private String password1;
	DBQueries queries = new DBQueries();

	@FXML
	private Parent parent;
	@FXML
    private CheckBox checkBox;
    @FXML 
    private TextField emailTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField1;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField repasswordTextField;
    @FXML
    private PasswordField passwordTextField1;
    @FXML
    private TextField emailTextField2;
    @FXML
    private TextField nameTextField2;
    @FXML
    private TextField phoneTextField2;
    @FXML
    private TextField passwordTextField2;
    @FXML
    private Button passwordButton;
    @FXML
    private Label messageLabel2;
    @FXML
    private Label messageLabel;
    @FXML
    private Label messageLabel1;
    @FXML
    private Button signUpButton;
    @FXML
    private Button seeButton;
    @FXML
    private Button seeButton1;
    @FXML
    private HBox nameBox;
    @FXML
    private HBox emailBox;
    @FXML
    private HBox phoneBox;
    @FXML
    private HBox passwordBox;
    @FXML
    private HBox repasswordBox;
    @FXML
    private Button loginButton;
    @FXML
    private VBox Pane;
    @FXML
    private Button forgotButton;
    @FXML
    private Button backButton;
    @FXML
    private VBox panel;

    /**
     * Method for registering to the application
     * @param event
     */
	@FXML
	public void register(ActionEvent event) {
		if (nameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() 
				|| phoneTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()
				|| repasswordTextField.getText().isEmpty()) {
			messageLabel.setText("Please fill all the blanks");
		}else if(!passwordTextField.getText().equals(repasswordTextField.getText())){
			messageLabel.setText("Passwords do not match! Please check...");
		}else if (queries.checkEmails(emailTextField.getText())) {
			messageLabel.setText("This email has been assigned to another account");
		}else if (!messageLabel.getText().isEmpty()) {
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("ERROR");
			message.setContentText("You do not fulfill all criteria to register");
			message.show();
		}else {
			int result = queries.addUser(nameTextField.getText(), emailTextField.getText(), 
					phoneTextField.getText(), passwordTextField.getText());
			if(result == 1) {
				Alert message = new Alert(AlertType.INFORMATION);
				message.setTitle("Registered");
				message.setContentText("New user registered successfully! Welcome "+nameTextField.getText());
				message.show();
				nameTextField.clear();
				emailTextField.clear();
				phoneTextField.clear();
				passwordTextField.clear();
				repasswordTextField.clear();
				checkBox.setSelected(false);
				Stage stage = new Stage(StageStyle.TRANSPARENT);
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();
			}
			else {
				Alert message = new Alert(AlertType.ERROR);
				message.setTitle("Error");
				message.setContentText("ERROR!!! The user cannot be registered");
				message.show();
			}
				
		}
	}
	/**
	 * Method to make the password visible
	 * @param event
	 */
	@FXML
	public void Visible(MouseEvent event) {
		seeButton.setOnMousePressed(e -> {
			password = passwordTextField.getText();
			passwordTextField.clear();
			passwordTextField.setPromptText(password);
				
			rePassword = repasswordTextField.getText();
			repasswordTextField.clear();
			repasswordTextField.setPromptText(rePassword);
		});
	}
	
	@FXML
	void visible(MouseEvent me) {
		seeButton1.setOnMousePressed(e -> {
			password1 = passwordTextField1.getText();
			passwordTextField1.clear();
			passwordTextField1.setPromptText(password1);
		});
	}
	@FXML
	public void notVisible(MouseEvent event) {
		seeButton.setOnMouseReleased(e -> {
			passwordTextField.setText(password);
			passwordTextField.setPromptText("Password");
				
			repasswordTextField.setText(rePassword);
			repasswordTextField.setPromptText("Repeat Password");
		});
	}
	
	@FXML
	void notvisible(MouseEvent me) {
		seeButton1.setOnMouseReleased(e -> {
			passwordTextField1.setText(password1);
			passwordTextField1.setPromptText("Password");
		});
	}
	/**
	 * Regular expression for name
	 * @param event
	 */
	@FXML
	public void nameRegex(javafx.scene.input.KeyEvent event) {
		nameTextField.setOnKeyTyped(e -> {
			String regex = "[a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(nameTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a valid name");
				nameBox.setStyle("-fx-border-color: RED; -fx-border-width: 1 1 1 1;");
			}else {
				messageLabel.setText("");
				nameBox.setStyle("-fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
			}
				
		});
	}
	/**
	 * Regular expression for email
	 * @param event
	 */
	@FXML
	public void emailRegex(javafx.scene.input.KeyEvent event) {
		emailTextField.setOnKeyTyped(e -> {
			String regex = "^(?=.*[a-zA-Z0-9])(?=.*[._-]).{5,30}[@](?=.*[a-z]).{4,10}[.]{1}(?=.*[a-zA-Z]).{2,5}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(emailTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a valid email");
				emailBox.setStyle("-fx-border-color: RED; -fx-border-width: 1 1 1 1;");
			}else {
				messageLabel.setText("");
				emailBox.setStyle("-fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
			}
		});
	}
	/**
	 * Regular expression for phone number
	 * @param event
	 */
	@FXML
	public void phoneRegex(javafx.scene.input.KeyEvent event) {
		phoneTextField.setOnKeyTyped(e -> {
			String regex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(phoneTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a valid phone number");
				phoneBox.setStyle("-fx-border-color: RED; -fx-border-width: 1 1 1 1;");
			}else {
				messageLabel.setText("");
				phoneBox.setStyle("-fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
			}
		});
	}
	/**
	 * Regular expression for password
	 * @param event
	 */
	@FXML
	public void passwordRegex(javafx.scene.input.KeyEvent event) {
		passwordTextField.setOnKeyTyped(e -> {
			String regex = "^(?=.*[a-zA-Z0-9])(?=.*[@#$%^&*+=-]).{8,15}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(passwordTextField.getText());
			if(!matcher.matches()) {
				messageLabel.setText("Enter a stronger password! This is easily guessed.");
				passwordBox.setStyle("-fx-border-color: RED; -fx-border-width: 1 1 1 1;");
			}else {
				messageLabel.setText("");
				passwordBox.setStyle("-fx-border-color: #DEDEE4; -fx-border-width: 0 0 1 0");
			}
		});
	}
	/**
	 * Method to verify the login
	 * @param event
	 */
	@FXML
	public void login(ActionEvent event) {
		if(emailTextField1.getText().isEmpty() && passwordTextField1.getText().isEmpty()) {
			messageLabel1.setText("Invalid email and password!");
		}
		else if(emailTextField1.getText().isEmpty()) {
			messageLabel1.setText("Incorrect email");
		}
		else if(passwordTextField1.getText().isEmpty()) {
			messageLabel1.setText("Incorrect password");
		}
		else if (queries.loginUser(emailTextField1.getText(), passwordTextField1.getText())) {
			Stage stage = new Stage(StageStyle.TRANSPARENT);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}
	}
	/**
	 * Opens the forget password page
	 * @param event
	 */
	@FXML
	public void openForgot(ActionEvent event) {
		try {
			parent = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
			Pane.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Method to get the forgotten password
	 * @param event
	 */
	@FXML
	public void getPassword(ActionEvent event) {
		passwordTextField2.setText(queries.getPassword(nameTextField2.getText(), emailTextField2.getText(), phoneTextField2.getText()));
	}
	
	@FXML
	public void goBack(ActionEvent event) {
		try {
			parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			panel.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
