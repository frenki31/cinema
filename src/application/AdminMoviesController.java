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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AdminMoviesController implements Initializable {
	ObservableList<Movie> movieList = FXCollections.observableArrayList();
    DBQueries queries = new DBQueries();
	
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
    private Button updateBtn;
    @FXML
    private TableView<Movie> moviesTable;
    
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
    }

	@FXML
	public void refreshTable() {
		movieList.clear();
		movieList = FXCollections.observableArrayList(queries.getAllMovies());
		moviesTable.setItems(movieList);
	}
	
	  @FXML
	    public void openAdd(ActionEvent event) {
	    	try {
				parent = FXMLLoader.load(getClass().getResource("AddMovieView.fxml"));
				moviesBox.getChildren().removeAll();
				moviesBox.getChildren().setAll(parent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
}
