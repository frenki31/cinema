package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Language;
import entities.Movie;
import entities.MovieLanguage;
import entities.Type;
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

public class Admin_LanguageController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private Button removeBtn, removeLanguageBtn, updateLanguageBtn, addLanguageBtn, addBtn;
    @FXML
    private ChoiceBox<String> LanguageChoiceBox, movieChoiceBox, typesChoiceBox;
    @FXML
    private TableColumn<Language, String> LanguageCode;
    @FXML
    private TableColumn<Language, Integer> LanguageId;
    @FXML
    private TableColumn<MovieLanguage, Integer> LanguageId1;
    @FXML
    private TableColumn<MovieLanguage, String> LanguageCode1;
    @FXML
    private TableColumn<Language, String> LanguageName;
    @FXML
    private TableColumn<MovieLanguage, String> LanguageName1;
    @FXML
    private TableColumn<MovieLanguage, String> filmID;
    @FXML
    private TableColumn<MovieLanguage, String> filmTitle;
    @FXML
    private TableColumn<MovieLanguage, String> type;
    @FXML
    private TableColumn<MovieLanguage, String> typeId;
    @FXML
    private TextField LanguageTextField, codeTextField;
    @FXML
    private VBox moviesBox;
    @FXML
    private TableView<MovieLanguage> moviesLanguageTable;
    @FXML
    private TableView<Language> LanguageTable;
    ObservableList<Language> languages = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> LanguageNames, movieTitles, typeLanguages;
    ObservableList<MovieLanguage> movieLanguage = FXCollections.observableArrayList();
    ObservableList<Type> types = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        LanguageNames = FXCollections.observableArrayList();
        typeLanguages = FXCollections.observableArrayList();
        for(Type type1: types)
            typeLanguages.add(type1.getTypeLanguage());
        for(Movie movie: movies)
            movieTitles.add(movie.getTitle());
        for(Language Language: languages)
            LanguageNames.add(Language.getLanguageName());
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

        LanguageChoiceBox.getItems().addAll(LanguageNames);
        movieChoiceBox.getItems().addAll(movieTitles);
        typesChoiceBox.getItems().addAll(typeLanguages);
    }

    @FXML
    public void refreshTable() {
        movies.clear();
        languages.clear();
        types.clear();
        types = FXCollections.observableArrayList(queries.getType());
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        languages = FXCollections.observableArrayList(queries.getLanguage());
        movieLanguage = FXCollections.observableArrayList(queries.getMovieLanguage());
        LanguageTable.setItems(languages);
        moviesLanguageTable.setItems(movieLanguage);
    }

    @FXML
    void addLanguage(ActionEvent event) {
        if (LanguageTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addLanguage(LanguageTextField.getText(),codeTextField.getText());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Language added successfully");
                message.show();
                LanguageTextField.setText("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The movie cannot be registered");
                message.show();
            }
        }
    }

    @FXML
    void addMovieLanguage(ActionEvent event) {
        if (LanguageChoiceBox.getValue().equals("") || movieChoiceBox.getValue().equals("") ||
                typesChoiceBox.getValue().equals("")) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        }else {
            int result = queries.addMovieLanguage(movieChoiceBox.getValue(),LanguageChoiceBox.getValue(),
                    typesChoiceBox.getValue());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Language attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                LanguageChoiceBox.setValue("");
                typesChoiceBox.setValue("");
            }else {
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
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    void deleteMovieLanguage(ActionEvent event) {
        if (queries.deleteMovieLanguage(moviesLanguageTable.getSelectionModel().getSelectedItem().getFilmID(),
                moviesLanguageTable.getSelectionModel().getSelectedItem().getLanguageId(),
                moviesLanguageTable.getSelectionModel().getSelectedItem().getTypeId()) == 1) {
            moviesLanguageTable.getItems().removeAll(moviesLanguageTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Movie-Language relationship deleted successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    void retrieveLanguage(MouseEvent event) {
        LanguageTextField.setText(LanguageTable.getSelectionModel().getSelectedItem().getLanguageName());
        codeTextField.setText(LanguageTable.getSelectionModel().getSelectedItem().getLanguageCode());
    }

    @FXML
    void retrieveMovieLanguage(MouseEvent event) {
        LanguageChoiceBox.setValue(moviesLanguageTable.getSelectionModel().getSelectedItem().getLanguageName());
        movieChoiceBox.setValue(moviesLanguageTable.getSelectionModel().getSelectedItem().getTitle());
        typesChoiceBox.setValue(moviesLanguageTable.getSelectionModel().getSelectedItem().getTypeLanguage());
    }

    @FXML
    void updateLanguage(ActionEvent event) {
        if(LanguageTextField.getText().isEmpty() || codeTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill in all the blanks");
            message.show();
        }else if (queries.updateLanguage(LanguageTable.getSelectionModel().getSelectedItem().getLanguageId(),
                LanguageTextField.getText(), codeTextField.getText()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Language Updated Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}