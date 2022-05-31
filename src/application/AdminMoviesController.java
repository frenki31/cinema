package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AdminMoviesController implements Initializable {
	ObservableList<Movie> movieList = FXCollections.observableArrayList();
    DBQueries queries = new DBQueries();
	
    @FXML
    private Stage stage;
    @FXML
	private VBox moviesBox;
    @FXML
    private Parent parent;
    @FXML
    private Button addBtn;
    @FXML
    private TableColumn<Movie, String> cover;
    @FXML
    private TableColumn<Movie, Integer> filmID;
    @FXML
    private TableColumn<Movie, String> filmTitle;
    @FXML
    private TableColumn<Movie, String> genre;
    @FXML
    private FontAwesomeIcon homeIcon;
    @FXML
    private TableColumn<Movie, String> link;
    @FXML
    private Button refreshBtn;
    @FXML
    private TableColumn<Movie, String> releaseYear;
    @FXML
    private Button removeBtn;
    @FXML
    private TableColumn<Movie, String> runningTime;
    @FXML
    private TableColumn<Movie, String> script;
    @FXML
    private TableColumn<Movie, String> movieLink;
    @FXML
    private Button updateBtn;
    @FXML
    private TableView<Movie> moviesTable;
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
    private Button uploadButton;
    @FXML
    private TextField movieLinkTextField;
    @FXML
    private Button clearButton;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
	}
    
    public void loadData() {
    	queries.setConnection();
    	refreshTable();
    	
    	filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
    	filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    	runningTime.setCellValueFactory(new PropertyValueFactory<>("duration"));
    	cover.setCellValueFactory(new PropertyValueFactory<>("cover"));
    	link.setCellValueFactory(new PropertyValueFactory<>("trailer"));
    	script.setCellValueFactory(new PropertyValueFactory<>("description"));
    	releaseYear.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    	genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
    	movieLink.setCellValueFactory(new PropertyValueFactory<>("movieLink"));
    }

	@FXML
	public void refreshTable() {
		movieList.clear();
		movieList = FXCollections.observableArrayList(queries.getAllMovies());
		moviesTable.setItems(movieList);
	}
	
	 @FXML
	 public void addMovie(ActionEvent event) {
	    if (titleTextField.getText().isEmpty() || durationTextField.getText().isEmpty() 
				|| coverTextField.getText().isEmpty() || trailerTextField.getText().isEmpty()
				|| descrtiptionTextField.getText().isEmpty() || yearTextField.getText().isEmpty() || genreTextField.getText().isEmpty()) {
				Alert message = new Alert(AlertType.ERROR);
				message.setTitle("Empty");
				message.setContentText("Please fill all the fields");
				message.show();
		}else {
			int result = queries.addMovie(titleTextField.getText(), durationTextField.getText(), 
					coverTextField.getText(), trailerTextField.getText(), descrtiptionTextField.getText(),	
					yearTextField.getText(), genreTextField.getText(), movieLinkTextField.getText());
			if(result == 1) {
				Alert message = new Alert(AlertType.INFORMATION);
				message.setTitle("Added");
				message.setContentText("The movie has been added successfully");					
				message.show();
				titleTextField.setText("");;
				durationTextField.setText("");;
				coverTextField.setText("");;
				trailerTextField.setText("");				
				descrtiptionTextField.setText("");
				yearTextField.setText("");
				genreTextField.setText("");
				movieLinkTextField.setText("");
			}
			else {
				Alert message = new Alert(AlertType.ERROR);
				message.setTitle("ERROR");
				message.setContentText("ERROR!!! The movie cannot be registered");			
				message.show();
			}
		}
	}
	
	@FXML
	public void deleteData(ActionEvent event) {
		if (queries.deleteMovie(moviesTable.getSelectionModel().getSelectedItem().getFilmID()) == 1) {
			moviesTable.getItems().removeAll(moviesTable.getSelectionModel().getSelectedItem());
			Alert message = new Alert(AlertType.INFORMATION);
			message.setTitle("Deleted");
			message.setContentText("Movie Deleted Successfully");
			message.show();
		}else {
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("ERROR");
			message.setContentText("Something went wrong! Please try again...");
			message.show();
		}
	}
	
	 @FXML
	 public void uploadPath(ActionEvent event) {
	   	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Files");	    
    	fileChooser.setInitialDirectory(new File("C:/Users/user/eclipse-workspace/Application/images"));
	   	File selected = fileChooser.showOpenDialog(stage);
	   	if(selected != null) {
	   		coverTextField.setText(selected.getPath());
	   	}
    }
	 
	 @FXML
	 public void clearFields(ActionEvent event) {
		 coverTextField.setText(null);
		 descrtiptionTextField.setText(null);
		 durationTextField.setText(null);
		 genreTextField.setText(null);
		 trailerTextField.setText(null);
		 titleTextField.setText(null);
		 movieLinkTextField.setText(null);
		 yearTextField.setText(null);
	 }
	 
	 @FXML
	 public void updateMovie(ActionEvent event) {
		 if(titleTextField.getText().isEmpty() || durationTextField.getText().isEmpty() || coverTextField.getText().isEmpty()
			|| trailerTextField.getText().isEmpty() || descrtiptionTextField.getText().isEmpty() || yearTextField.getText().isEmpty() 
			|| genreTextField.getText().isEmpty() || movieLinkTextField.getText().isEmpty()) {
			 Alert message = new Alert(AlertType.ERROR);
				message.setTitle("Empty");		
				message.setContentText("Please fill in all the blanks");
				message.show();
		 }else if (queries.updateMovie(moviesTable.getSelectionModel().getSelectedItem().getFilmID(), 
				 titleTextField.getText(), durationTextField.getText(), coverTextField.getText(), 
				 trailerTextField.getText(), descrtiptionTextField.getText(), yearTextField.getText(), 
				 genreTextField.getText(), movieLinkTextField.getText()) == 1) {
			     Alert message = new Alert(AlertType.INFORMATION);
				 message.setTitle("Updated");
				 message.setContentText("Movie Updated Successfully");
				 message.show();
		 }else {
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("ERROR");		
			message.setContentText("Something went wrong! Please try again...");
			message.show();
		 }
	 }
	 
	 @FXML
	 public void retrieveData(MouseEvent event) {
		 titleTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getTitle());
		 durationTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getDuration());
		 coverTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getCover());
		 descrtiptionTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getDescription());
		 trailerTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getTrailer());
		 yearTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getReleaseYear());
		 genreTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getGenre());
		 movieLinkTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getMovieLink());
	 }
}
