package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * Class Dashboard which is the main display of the application
 * @author user
 *
 */
public class DashboardController implements Initializable{
	
	double x,y = 0;
	DBQueries queries = new DBQueries();
	SignInUpController sc = new SignInUpController();
	
	@FXML
	private ComboBox<String> genreComboBox;
	@FXML
	private Button allButton; //shows all movies
	@FXML
	private Button homeBtn; //opens the main view
	@FXML
	private Button loginBtn; //opens login/signup page
	@FXML
	private Button suggestMovieBtn; 
	@FXML
	private HBox horizontalBox;
	@FXML
	private HBox allMovies;
	@FXML 
	private Parent parent;
	@FXML
	private BorderPane scene;
	@FXML
	private Stage stage;
	@FXML
	private Button minimizeButton;
	@FXML
	private Button maximizeButton;
	@FXML
	private Button closeButton;
    @FXML
    private FontAwesomeIcon homeIcon;
    @FXML
    private FontAwesomeIcon movieIcon;
    @FXML
    private FontAwesomeIcon userIcon;
    @FXML 
    private TextField searchTextField;
    @FXML
    private VBox displayBox;
    @FXML
    private Label EmailLabel ;
    @FXML
    private Button adminBtn;
    @FXML
    private FontAwesomeIcon adminIcon;
    @FXML
    private Button comingSoonBtn;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		genreComboBox.setItems(queries.getGenres());
		HBox hbox = new HBox();
		hbox.setSpacing(30);
		ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllCovers());
		try {
			for(Movie movie: movies) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Movie.fxml"));
				VBox vbox = loader.load();
				movieController movieController = loader.getController();
				movieController.setMovie(movie);
				hbox.getChildren().addAll(vbox);
				horizontalBox.getChildren().setAll(hbox);
			}
		} catch (IOException e) {
	        // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void displayEmail(String email) {
//		EmailLabel.setText(email);
//	}
	/**
	 * Method to minimize the page
	 * @param event
	 */
    @FXML
	public void minimize(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
    /**
	 * Method to maximize the page
	 * @param event
	 */
	@FXML
	public void maximize(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if(stage.isMaximized())
			stage.setMaximized(false);
		else
			stage.setMaximized(true);
	}
	/**
	 * Method to close the page
	 * @param event
	 */
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
    	homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
   		homeIcon.setFill(Color.web("#A1A1A1"));
   		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
   				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
   		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
   		adminBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    	adminIcon.setFill(Color.web("#A1A1A1"));
   		movieIcon.setFill(Color.web("#A1A1A1"));
   		userIcon.setFill(Color.WHITE);
 
   		try {
       		parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
      		Stage stage = new Stage();
      		stage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(parent);
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void openMain(ActionEvent event) {
    	homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
   				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
   		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
   		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
   		adminBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
   		adminIcon.setFill(Color.web("#A1A1A1"));
    	homeIcon.setFill(Color.WHITE);
   		movieIcon.setFill(Color.web("#A1A1A1"));
   		userIcon.setFill(Color.web("#A1A1A1"));
    	
   		HBox hbox = new HBox();
   		hbox.setSpacing(30);
   		ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllCovers());
    	try {
   			for(Movie movie: movies) {
   				FXMLLoader loader = new FXMLLoader();
   				loader.setLocation(getClass().getResource("Movie.fxml"));
   				VBox vbox = loader.load();
   				movieController movieController = loader.getController();
    			movieController.setMovie(movie);
    			hbox.getChildren().addAll(vbox);
    			horizontalBox.getChildren().setAll(hbox);
   			}
   		} catch (IOException e) {
   	        // TODO Auto-generated catch block
   			e.printStackTrace();
   		}
    }

    @FXML
    void openSuggest(ActionEvent event) {
    	homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
		homeIcon.setFill(Color.web("#A1A1A1"));
		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
		adminBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
		adminIcon.setFill(Color.web("#A1A1A1"));
		movieIcon.setFill(Color.WHITE);
		userIcon.setFill(Color.web("#A1A1A1"));
    		
    	try {
    		parent = FXMLLoader.load(getClass().getResource("SuggestMovie.fxml"));
			horizontalBox.getChildren().setAll(parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void openAdmin(ActionEvent event) {
    	homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
		homeIcon.setFill(Color.web("#A1A1A1"));
		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
		adminBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
		adminIcon.setFill(Color.WHITE);
		movieIcon.setFill(Color.web("#A1A1A1"));
		userIcon.setFill(Color.web("#A1A1A1"));
		
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
    	try {
    		parent = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void openComingSoon(ActionEvent event) {
    	HBox hbox = new HBox();
    	hbox.setSpacing(30);
    	ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllComingSoon());
    	for (Movie movie: movies) {
    		try {
    			FXMLLoader loader = new FXMLLoader();
    		    loader.setLocation(getClass().getResource("Movie.fxml"));
				VBox vBox = loader.load();
				movieController mc = loader.getController();
				mc.setMovie(movie);
				hbox.getChildren().add(vBox);
				horizontalBox.getChildren().setAll(hbox);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
    public void searchByTitle(KeyEvent event) {
    	searchTextField.setOnKeyTyped(e -> {
        	HBox hBox = new HBox();
        	hBox.setSpacing(30);
    		ObservableList<Movie> movies = FXCollections.observableArrayList(queries.searchMovies(searchTextField.getText()));
    		for (Movie movie: movies) {
    			try {
            		FXMLLoader loader = new FXMLLoader();
            		loader.setLocation(getClass().getResource("Movie.fxml"));
            	    VBox vbox = loader.load();
            		movieController mc = loader.getController();
            		mc.setMovie(movie);
            		hBox.getChildren().add(vbox);
            		horizontalBox.getChildren().setAll(hBox);
            		if(searchTextField.getText().isEmpty()) {
            			HBox hbox = new HBox();
            	   		hbox.setSpacing(30);
            	   		ObservableList<Movie> allMovies = FXCollections.observableArrayList(queries.getAllCovers());
            	    	for(Movie movie1: allMovies) {
            	   			VBox vbox1 = loader.load();	   				
							mc.setMovie(movie1);
							hbox.getChildren().addAll(vbox1);
							horizontalBox.getChildren().setAll(hbox);
						}
            		}
        		}catch(IOException ex) {
        			ex.printStackTrace();
        		}  		
        	}
    	});
    }
    
    @FXML
    public void displayGenres(ActionEvent event) {
    	HBox hBox = new HBox();
    	hBox.setSpacing(30);
    	String selectedItem = genreComboBox.getSelectionModel().getSelectedItem().toString();
    	ObservableList<Movie> movies = FXCollections.observableArrayList(queries.clasifyGenres(selectedItem));
		for (Movie movie: movies) {
			try {
        		FXMLLoader loader = new FXMLLoader();
        		loader.setLocation(getClass().getResource("Movie.fxml"));
        	    VBox vbox = loader.load();
        		movieController mc = loader.getController();
        		mc.setMovie(movie);
        		hBox.getChildren().addAll(vbox);
        		horizontalBox.getChildren().setAll(hBox);
    		}catch(IOException ex) {
    			ex.printStackTrace();
    	    }
    	}
    }
}
