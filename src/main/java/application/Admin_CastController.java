package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Cast;
import entities.Movie;
import entities.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Admin_CastController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> actorChoiceBox, movieChoiceBox;
    @FXML
    private TableColumn<Cast, Integer> actorId, filmID, order;
    @FXML
    private TableColumn<Cast, String> actorName, filmTitle, characterName;
    @FXML
    private TableView<Person> actorTable;
    @FXML
    private Button addBtn, refreshBtn, removeBtn;
    @FXML
    private TableView<Cast> castTable;
    @FXML
    private TextField characterTextField, orderTextField;
    @FXML
    private VBox moviesBox;
    ObservableList<Person> actors = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> movieTitles, actorNames;
    ObservableList<Cast> cast = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        actorNames = FXCollections.observableArrayList();
        for(Movie movie: movies)
            movieTitles.add(movie.getTitle());
        for(Person actor: actors)
            actorNames.add(actor.getName());
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        actorId.setCellValueFactory(new PropertyValueFactory<>("actorId"));
        actorName.setCellValueFactory(new PropertyValueFactory<>("actorName"));
        characterName.setCellValueFactory(new PropertyValueFactory<>("character"));
        order.setCellValueFactory(new PropertyValueFactory<>("order"));

        actorChoiceBox.getItems().addAll(actorNames);
        movieChoiceBox.getItems().addAll(movieTitles);
    }
    @FXML
    public void refreshTable() {
        movies.clear();
        actors.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        actors = FXCollections.observableArrayList(queries.getActors());
        cast = FXCollections.observableArrayList(queries.getCast());
        castTable.setItems(cast);
    }
    @FXML
    void addCast(ActionEvent event) {
        if (actorChoiceBox.getValue().equals("") || movieChoiceBox.getValue().equals("") || characterTextField.getText().equals("")
                || orderTextField.getText().equals("")){
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addCast(movieChoiceBox.getValue(),actorChoiceBox.getValue(),
                    characterTextField.getText(), Integer.parseInt(orderTextField.getText()));
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Actor attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                actorChoiceBox.setValue("");
                characterTextField.setText("");
                orderTextField.setPromptText("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! Cannot attach actor to movie");
                message.show();
            }
        }
    }
    @FXML
    void deleteCast(ActionEvent event) {
        if (queries.deleteCast(castTable.getSelectionModel().getSelectedItem().getFilmID(),
                castTable.getSelectionModel().getSelectedItem().getActorId()) == 1) {
            castTable.getItems().removeAll(castTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Cast deleted successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void retrieveCast(MouseEvent event) {
        actorChoiceBox.setValue(castTable.getSelectionModel().getSelectedItem().getActorName());
        movieChoiceBox.setValue(castTable.getSelectionModel().getSelectedItem().getTitle());
        characterTextField.setText(castTable.getSelectionModel().getSelectedItem().getCharacter());
        orderTextField.setText(String.valueOf(castTable.getSelectionModel().getSelectedItem().getOrder()));
    }
}