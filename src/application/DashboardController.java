package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable{
	
	double x,y = 0;
	@FXML
	private VBox verticalBox;
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
    private VBox allMoviesContainer;
    Movie movie;
    DBQueries queries;
    List<Movie> allCovers;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		allCovers = new ArrayList<>(queries.getAllCovers());
//		try {
//			for(int i=0; i<allCovers.size(); i++) {
//				FXMLLoader loader = new FXMLLoader();
//				loader.setLocation(getClass().getResource("Movie.fxml"));
//				VBox vbox = loader.load();
//				movieController movieController = loader.getController();
//				movieController.getCover(allCovers.get(i));
//				verticalBox.getChildren().add(vbox);
//			}
//		} catch (IOException e) {
//	        // TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Alert message = new Alert(AlertType.CONFIRMATION);
		message.setTitle("Exit");
		message.setContentText("Do you want to exit?");
		if(message.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
	}
	
    @FXML
    public void openLogin(ActionEvent event) {
    	try {
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
    private void clickColors(MouseEvent event) {
    	loginBtn.setOnMouseClicked(e -> {
    		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeIcon.setFill(Color.web("#A1A1A1"));
    		movieIcon.setFill(Color.web("#A1A1A1"));
    		userIcon.setFill(Color.WHITE);
    	});
    	
    	suggestMovieBtn.setOnMouseClicked(e -> {
    		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeIcon.setFill(Color.web("#A1A1A1"));
    		movieIcon.setFill(Color.WHITE);
    		userIcon.setFill(Color.web("#A1A1A1"));
    	});
    	
    	homeBtn.setOnMouseClicked(e -> {
    		homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
    				+ " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
    		suggestMovieBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		loginBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
    		homeIcon.setFill(Color.WHITE);
    		movieIcon.setFill(Color.web("#A1A1A1"));
    		userIcon.setFill(Color.web("#A1A1A1"));
    	});
    }
    
    @FXML
    void openMain(ActionEvent event) {
//    	try {
//			parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
//			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//			scene = new Scene(parent);
//			stage.setScene(scene);
//			stage.show();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    @FXML
    void openSuggest(ActionEvent event) {
    	try {
			parent = FXMLLoader.load(getClass().getResource("SuggestMovie.fxml"));
			verticalBox.getChildren().removeAll();
			verticalBox.getChildren().setAll(parent);
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
}
