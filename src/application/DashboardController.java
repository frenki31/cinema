package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable{
	
	double x,y = 0;
	SignInUpController loginController = new SignInUpController();
	allMoviesController allMoviesController = new allMoviesController();
	ObservableList<String> users = FXCollections.observableArrayList("USER","ADMIN");
	SignInUpController signController = new SignInUpController();
	DBQueries queries = new DBQueries();
	@FXML
	private HBox horizontalBox;
	@FXML 
	private Parent parent;
	@FXML
	private BorderPane scene;
	@FXML
	private Stage stage;
	@FXML
	private Button minimizeButton, maximizeButton, closeButton;
	@FXML
	private ImageView coverPhoto;
	@FXML
	private HBox allMovies;
	@FXML
	private Button homeBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Button suggestMovieBtn;
    @FXML
    private FontAwesomeIcon homeIcon;
    @FXML
    private FontAwesomeIcon movieIcon;
    @FXML
    private FontAwesomeIcon userIcon;
    @FXML
    private FontAwesomeIcon searchByTitleBtn;
    @FXML
    private TextField searchTextField;
    @FXML
    private ComboBox<String> comboBox;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			editComboBox();
			parent = FXMLLoader.load(getClass().getResource("allMovies.fxml"));
			horizontalBox.getChildren().removeAll();
			horizontalBox.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @FXML
	public void minimize(ActionEvent event) {
		try {
			parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void maximize(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if(stage.isMaximized())
			stage.setMaximized(false);
		else
			stage.setMaximized(true);
	}
	
	@FXML
	public void close(ActionEvent event) {
		Alert message = new Alert(AlertType.CONFIRMATION);
		message.setTitle("Exit");
		message.setContentText("Do you want to exit?");
		if(message.showAndWait().get() == ButtonType.OK) {
			System.exit(0);;
		}
	}
	
    @FXML
    public void openLogin(ActionEvent event) {
    	try {
    		homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeIcon.setFill(Color.web("#A1A1A1"));
    		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		movieIcon.setFill(Color.web("#A1A1A1"));
    		userIcon.setFill(Color.WHITE);
    		
			parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void openMain(ActionEvent event) {
    	try {
    		homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeIcon.setFill(Color.WHITE);
    		movieIcon.setFill(Color.web("#A1A1A1"));
    		userIcon.setFill(Color.web("#A1A1A1"));
    		
			parent = FXMLLoader.load(getClass().getResource("allMovies.fxml"));
			horizontalBox.getChildren().removeAll();
			horizontalBox.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void openSuggest(ActionEvent event) {
    	try {
    		homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeIcon.setFill(Color.web("#A1A1A1"));
    		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		movieIcon.setFill(Color.WHITE);
    		userIcon.setFill(Color.web("#A1A1A1"));
			
    		parent = FXMLLoader.load(getClass().getResource("SuggestMovie.fxml"));
			horizontalBox.getChildren().removeAll();
			horizontalBox.getChildren().setAll(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    
    @FXML
    public void searchByTitle(MouseEvent event) {
//		FilteredList<Movie> filteredList = new FilteredList<>(allMoviesController.searchByTitle(), b-> true);
//		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredList.setPredicate(null)
//		});
    }
    
    public void editComboBox() {
    	comboBox.setItems(users);
		comboBox.setValue("USER");
		comboBox.setOnAction(e -> {
			String user = comboBox.getSelectionModel().getSelectedItem().toLowerCase().toString();
			if (user.equals("admin")) {
				try {
					Stage stage = new Stage();
					stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					stage.close();
					parent = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
					Scene scene = new Scene(parent);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Stage stage = new Stage();
				stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.show();
			}
		});
    }
}
