package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

}
