package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Country;
import entities.Movie;
import entities.MovieCountry;
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

public class Admin_CountriesController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private Button removeBtn, removeCountryBtn, updateCountryBtn, addcountryBtn, addBtn;
    @FXML
    private ChoiceBox<String> countryChoiceBox, movieChoiceBox;
    @FXML
    private TableColumn<Country, Integer> countryCode;
    @FXML
    private TableColumn<Country, String> countryIso;
    @FXML
    private TableColumn<MovieCountry, Integer> countryCode1;
    @FXML
    private TableColumn<Country, String> countryName;
    @FXML
    private TableColumn<MovieCountry, String> countryName1;
    @FXML
    private TableColumn<MovieCountry, Integer> filmID;
    @FXML
    private TableColumn<MovieCountry, String>filmTitle;
    @FXML
    private TextField countryTextField, countryIsoTextField;
    @FXML
    private VBox moviesBox;
    @FXML
    private TableView<MovieCountry> moviesCountryTable;
    @FXML
    private TableView<Country> countryTable;
    ObservableList<Country> countries = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> countryNames, movieTitles;
    ObservableList<MovieCountry> movieCountry = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        countryNames = FXCollections.observableArrayList();
        for(Movie movie: movies)
            movieTitles.add(movie.getTitle());
        for(Country country: countries)
            countryNames.add(country.getCountryName());
        countryCode.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
        countryIso.setCellValueFactory(new PropertyValueFactory<>("countryIso"));
        countryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        countryCode1.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
        countryName1.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        countryChoiceBox.getItems().addAll(countryNames);
        movieChoiceBox.getItems().addAll(movieTitles);
    }

    @FXML
    public void refreshTable() {
        movies.clear();
        countries.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        countries = FXCollections.observableArrayList(queries.getAllCountries());
        movieCountry = FXCollections.observableArrayList(queries.getMovieTitlesforCountry());
        countryTable.setItems(countries);
        moviesCountryTable.setItems(movieCountry);
    }

    @FXML
    void addCountry(ActionEvent event) {
        if (countryTextField.getText().isEmpty() || countryIsoTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addCountry(countryTextField.getText(),countryIsoTextField.getText());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Country added successfully");
                message.show();
                countryTextField.setText("");;
                countryIsoTextField.setText("");;
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The movie cannot be registered");
                message.show();
            }
        }
    }

    @FXML
    void addMovieCountry(ActionEvent event) {
        if (countryChoiceBox.getValue().equals("") || movieChoiceBox.getValue().equals("")) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        }else {
            int result = queries.addMovieCountry(movieChoiceBox.getSelectionModel().getSelectedItem().toString(),
                    countryChoiceBox.getSelectionModel().getSelectedItem().toString());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Country attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                countryChoiceBox.setValue("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! Cannot attach country to movie");
                message.show();
            }
        }
    }

    @FXML
    void deleteCountry(ActionEvent event) {
        if (queries.deleteCountry(countryTable.getSelectionModel().getSelectedItem().getCountryCode()) == 1) {
            countryTable.getItems().removeAll(countryTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Country Deleted Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    void deleteMovieCountry(ActionEvent event) {
        if (queries.deleteMovieCountry(moviesCountryTable.getSelectionModel().getSelectedItem().getFilmID(),
                moviesCountryTable.getSelectionModel().getSelectedItem().getCountryCode()) == 1) {
            countryTable.getItems().removeAll(countryTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Movie-Country relationship deleted successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    void retrieveCountry(MouseEvent event) {
        countryTextField.setText(countryTable.getSelectionModel().getSelectedItem().getCountryName());
        countryIsoTextField.setText(countryTable.getSelectionModel().getSelectedItem().getCountryIso());
    }

    @FXML
    void retrieveMovieCountry(MouseEvent event) {
        countryChoiceBox.setValue(moviesCountryTable.getSelectionModel().getSelectedItem().getCountryName());
        movieChoiceBox.setValue(moviesCountryTable.getSelectionModel().getSelectedItem().getTitle());
    }

    @FXML
    void updateCountry(ActionEvent event) {
        if(countryTextField.getText().isEmpty() || countryIsoTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill in all the blanks");
            message.show();
        }else if (queries.updateCountry(countryTable.getSelectionModel().getSelectedItem().getCountryCode(),
                countryTextField.getText(),countryIsoTextField.getText()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Country Updated Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}
