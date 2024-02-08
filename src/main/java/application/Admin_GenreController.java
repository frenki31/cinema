package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Genre;
import entities.Movie;
import entities.MovieGenre;
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

public class Admin_GenreController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private Button removeBtn, removeGenreBtn, updateGenreBtn, addGenreBtn, addBtn;
    @FXML
    private ChoiceBox<String> genreChoiceBox, movieChoiceBox;
    @FXML
    private TableColumn<Genre, Integer> genreId;
    @FXML
    private TableColumn<MovieGenre, String> genreId1;
    @FXML
    private TableColumn<Genre, String> genreCategory;
    @FXML
    private TableColumn<MovieGenre, String> genreCategory1;
    @FXML
    private TableColumn<MovieGenre, String> filmID;
    @FXML
    private TableColumn<MovieGenre, String> filmTitle;
    @FXML
    private TextField genreTextField;
    @FXML
    private VBox moviesBox;
    @FXML
    private TableView<MovieGenre> moviesGenreTable;
    @FXML
    private TableView<Genre> genreTable;
    ObservableList<Genre> genres = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> genreNames, movieTitles;
    ObservableList<MovieGenre> movieGenre = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        genreNames = FXCollections.observableArrayList();
        for(Movie movie: movies)
            movieTitles.add(movie.getTitle());
        for(Genre genre: genres)
            genreNames.add(genre.getCategory());
        genreId.setCellValueFactory(new PropertyValueFactory<>("id"));
        genreCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        genreId1.setCellValueFactory(new PropertyValueFactory<>("genreID"));
        genreCategory1.setCellValueFactory(new PropertyValueFactory<>("genreCategory"));
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        genreChoiceBox.getItems().addAll(genreNames);
        movieChoiceBox.getItems().addAll(movieTitles);
    }

    @FXML
    public void refreshTable() {
        movies.clear();
        genres.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        genres = FXCollections.observableArrayList(queries.getGenre());
        movieGenre = FXCollections.observableArrayList(queries.getMovieGenres());
        genreTable.setItems(genres);
        moviesGenreTable.setItems(movieGenre);
    }

    @FXML
    void addGenre(ActionEvent event) {
        if (genreTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addGenre(genreTextField.getText());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Genre added successfully");
                message.show();
                genreTextField.setText("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The movie cannot be registered");
                message.show();
            }
        }
    }

    @FXML
    void addMovieGenre(ActionEvent event) {
        if (genreChoiceBox.getSelectionModel().getSelectedItem().equals("") ||
                movieChoiceBox.getSelectionModel().getSelectedItem().equals("")) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        }else {
            int result = queries.addMovieGenre(movieChoiceBox.getSelectionModel().getSelectedItem().toString(),
                    genreChoiceBox.getSelectionModel().getSelectedItem().toString());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Genre attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                genreChoiceBox.setValue("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! Cannot attach genre to movie");
                message.show();
            }
        }
    }

    @FXML
    void deleteGenre(ActionEvent event) {
        if (queries.deleteGenre(genreTable.getSelectionModel().getSelectedItem().getId()) == 1) {
            genreTable.getItems().removeAll(genreTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Genre Deleted Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    void deleteMovieGenre(ActionEvent event) {
        if (queries.deleteMovieGenre(moviesGenreTable.getSelectionModel().getSelectedItem().getFilmID(),
                moviesGenreTable.getSelectionModel().getSelectedItem().getGenreID()) == 1) {
            genreTable.getItems().removeAll(genreTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Movie-Genre relationship deleted successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    void retrieveGenre(MouseEvent event) {
        genreTextField.setText(genreTable.getSelectionModel().getSelectedItem().getCategory());
    }

    @FXML
    void retrieveMovieGenre(MouseEvent event) {
        genreChoiceBox.setValue(moviesGenreTable.getSelectionModel().getSelectedItem().getGenreCategory());
        movieChoiceBox.setValue(moviesGenreTable.getSelectionModel().getSelectedItem().getTitle());
    }

    @FXML
    void updateGenre(ActionEvent event) {
        if(genreTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill in all the blanks");
            message.show();
        }else if (queries.updateGenre(genreTable.getSelectionModel().getSelectedItem().getId(),
                genreTextField.getText()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Genre Updated Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}