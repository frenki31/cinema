package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AdminSuggestedController implements Initializable{
	DBQueries queries = new DBQueries();
	ObservableList<SuggestMovie> suggestedMoviesList = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<SuggestMovie, String> directorCol;
	@FXML
	private TableColumn<SuggestMovie, String> filmCol;
	@FXML
    private TableColumn<SuggestMovie, String> genreCol;
	@FXML
    private TableColumn<SuggestMovie, Integer> idCol;
    @FXML
    private VBox suggestedBox;
    @FXML
    private TableView<SuggestMovie> suggestedTable;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private TableColumn<SuggestMovie, String> yearCol;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
	  	loadTable();
	}

    public void loadTable() {
    	queries.setConnection();
    	refreshTable();
    	idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    	filmCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    	directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));
    	yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
    	genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }
    
    @FXML
    public void refreshTable() {		
    	suggestedMoviesList.clear();
    	suggestedMoviesList = FXCollections.observableArrayList(queries.getAllSuggested());
    	suggestedTable.setItems(suggestedMoviesList);
    }
}
