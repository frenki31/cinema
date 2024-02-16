package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Language;
import entities.Movie;
import entities.MovieLanguage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class Admin_LanguageController implements Initializable {

    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> movieChoiceBox;
    @FXML
    private TableColumn<Language, String> LanguageCode, LanguageName;
    @FXML
    private TableColumn<Language, Integer> LanguageId;
    @FXML
    private TableColumn<MovieLanguage, Integer> LanguageId1, filmID, typeId;
    @FXML
    private TableColumn<MovieLanguage, String> LanguageCode1, LanguageName1, filmTitle, type;
    @FXML
    private TextField LanguageTextField, codeTextField, searchMovieTextField, searchLanguageTextField, LanguageTextField1,
            searchMovieLangTextField;
    @FXML
    private TableView<MovieLanguage> moviesLanguageTable;
    @FXML
    private TableView<Language> LanguageTable;
    ObservableList<Language> languages = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> movieTitles;
    ObservableList<MovieLanguage> movieLanguage = FXCollections.observableArrayList();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        for (Movie movie : movies)
            movieTitles.add(movie.getTitle());
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        LanguageId1.setCellValueFactory(new PropertyValueFactory<>("languageId"));
        LanguageCode.setCellValueFactory(new PropertyValueFactory<>("languageCode"));
        LanguageName.setCellValueFactory(new PropertyValueFactory<>("languageName"));
        type.setCellValueFactory(new PropertyValueFactory<>("typeLanguage"));
        typeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        LanguageId.setCellValueFactory(new PropertyValueFactory<>("languageId"));
        LanguageCode1.setCellValueFactory(new PropertyValueFactory<>("languageCode"));
        LanguageName1.setCellValueFactory(new PropertyValueFactory<>("languageName"));

        movieChoiceBox.setItems(movieTitles);
        Admin_CrewController.Filtering(movieTitles, searchMovieTextField, movieChoiceBox);
        FilteredList<Language> filteredLanguage = new FilteredList<>(languages, b -> true);
        searchLanguageTextField.textProperty().addListener((obs, old, newValue) -> {
            filteredLanguage.setPredicate(searchLanguageModel -> {
                if (newValue.isEmpty())
                    return true;
                String languageKeyword = newValue.toLowerCase();
                return searchLanguageModel.getLanguageCode().toLowerCase().contains(languageKeyword) ||
                        searchLanguageModel.getLanguageName().toLowerCase().contains(languageKeyword);
            });
        });
        SortedList<Language> sortedLanguage = new SortedList<>(filteredLanguage);
        sortedLanguage.comparatorProperty().bind(LanguageTable.comparatorProperty());
        LanguageTable.setItems(sortedLanguage);
        FilteredList<MovieLanguage> filteredMovieLanguage = new FilteredList<>(movieLanguage, b -> true);
        searchMovieLangTextField.textProperty().addListener((obs, old, newValue) -> {
            filteredMovieLanguage.setPredicate(searchMovieLanguageModel -> {
                if (newValue.isEmpty())
                    return true;
                String languageKeyword = newValue.toLowerCase();
                return searchMovieLanguageModel.getLanguageCode().toLowerCase().contains(languageKeyword) ||
                        searchMovieLanguageModel.getLanguageName().toLowerCase().contains(languageKeyword) ||
                        searchMovieLanguageModel.getTitle().toLowerCase().contains(languageKeyword);
            });
        });
        SortedList<MovieLanguage> sortedMovieLanguage = new SortedList<>(filteredMovieLanguage);
        sortedMovieLanguage.comparatorProperty().bind(moviesLanguageTable.comparatorProperty());
        moviesLanguageTable.setItems(sortedMovieLanguage);
    }
    @FXML
    public void refreshTable() {
        movies.clear();
        languages.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        languages = FXCollections.observableArrayList(queries.getLanguage());
        movieLanguage = FXCollections.observableArrayList(queries.getMovieLanguage());
        LanguageTable.setItems(languages);
        moviesLanguageTable.setItems(movieLanguage);
    }
    @FXML
    void addLanguage(ActionEvent event) {
        if (LanguageTextField.getText().isEmpty() || codeTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        } else {
            int result = queries.addLanguage(LanguageTextField.getText(), codeTextField.getText());
            if (result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Language added successfully");
                message.show();
                LanguageTextField.setText("");
            } else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The movie cannot be registered");
                message.show();
            }
        }
    }
    @FXML
    void addMovieLanguage(ActionEvent event) {
        if (LanguageTextField1.getText().isEmpty() || movieChoiceBox.getValue().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        } else {
            int result = queries.addMovieLanguage(movieChoiceBox.getValue(), LanguageTextField1.getText());
            if (result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Language attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                LanguageTextField1.setText("");
            } else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! Cannot attach Language to movie");
                message.show();
            }
        }
    }
    @FXML
    void deleteLanguage(ActionEvent event) {
        if (queries.deleteLanguage(LanguageTable.getSelectionModel().getSelectedItem().getLanguageId()) == 1) {
            LanguageTable.getItems().removeAll(LanguageTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Language Deleted Successfully");
            message.show();
        } else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void deleteMovieLanguage(ActionEvent event) {
        if (queries.deleteMovieLanguage(moviesLanguageTable.getSelectionModel().getSelectedItem().getFilmID(),
                moviesLanguageTable.getSelectionModel().getSelectedItem().getLanguageId()) == 1) {
            moviesLanguageTable.getItems().removeAll(moviesLanguageTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Movie-Language relationship deleted successfully");
            message.show();
        } else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void retrieveLanguage(MouseEvent event) {
        LanguageTextField.setText(LanguageTable.getSelectionModel().getSelectedItem().getLanguageName());
        LanguageTextField1.setText(LanguageTable.getSelectionModel().getSelectedItem().getLanguageName());
        codeTextField.setText(LanguageTable.getSelectionModel().getSelectedItem().getLanguageCode());
    }
    @FXML
    void retrieveMovieLanguage(MouseEvent event) {
        LanguageTextField1.setText(moviesLanguageTable.getSelectionModel().getSelectedItem().getLanguageName());
        movieChoiceBox.setValue(moviesLanguageTable.getSelectionModel().getSelectedItem().getTitle());
    }
    @FXML
    void updateLanguage(ActionEvent event) {
        if (LanguageTextField.getText().isEmpty() || codeTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill in all the blanks");
            message.show();
        } else if (queries.updateLanguage(LanguageTable.getSelectionModel().getSelectedItem().getLanguageId(),
                LanguageTextField.getText(), codeTextField.getText()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Language Updated Successfully");
            message.show();
        } else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}