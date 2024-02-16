package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Movie;
import entities.MovieGenre;
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

public class Admin_GenreController implements Initializable{
    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> movieChoiceBox, genreChoiceBox;
    @FXML
    private TableColumn<MovieGenre, String> genreCategory, filmTitle;
    @FXML
    private TableColumn<MovieGenre, Integer> filmID;
    @FXML
    private TextField searchGenreTextField, searchMovieTextField, searchMovieGenreTextField;
    @FXML
    private TableView<MovieGenre> moviesGenreTable;
    String[] genre = {"Adventure","Fantasy","Animation","Drama","Horror","Action",
    "Comedy","History","Western","Thriller","Crime","Documentary","Science Fiction",
    "Mystery","Music","Romance","Family","War","TV Movie"};
    ObservableList<String> genres = FXCollections.observableArrayList(genre);
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> movieTitles;
    ObservableList<MovieGenre> movieGenre = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        for(Movie movie: movies)
            movieTitles.add(movie.getTitle());
        genreCategory.setCellValueFactory(new PropertyValueFactory<>("genreCategory"));
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreChoiceBox.getItems().addAll(genres);
        movieChoiceBox.getItems().addAll(movieTitles);
        Admin_CrewController.Filtering(genres,searchGenreTextField,genreChoiceBox);
        Admin_CrewController.Filtering(movieTitles,searchMovieTextField,movieChoiceBox);
        FilteredList<MovieGenre> filteredMovie = new FilteredList<>(movieGenre, b-> true);
        searchMovieGenreTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMovie.setPredicate(searchMovieModel -> {
                if(newValue.isEmpty())
                    return true;
                String movieKeyword = newValue.toLowerCase();
                return searchMovieModel.getTitle().toLowerCase().contains(movieKeyword) ||
                        searchMovieModel.getGenreCategory().toLowerCase().contains(movieKeyword);
            });
        });
        SortedList<MovieGenre> sortedMovie = new SortedList<>(filteredMovie);
        sortedMovie.comparatorProperty().bind(moviesGenreTable.comparatorProperty());
        moviesGenreTable.setItems(sortedMovie);
    }

    @FXML
    public void refreshTable() {
        movies.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        movieGenre = FXCollections.observableArrayList(queries.getMovieGenres());
        moviesGenreTable.setItems(movieGenre);
    }
    @FXML
    void addMovieGenre(ActionEvent event) {
        if (genreChoiceBox.getSelectionModel().getSelectedItem().isEmpty() || movieChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        }else {
            int result = queries.addMovieGenre(movieChoiceBox.getSelectionModel().getSelectedItem(),
                    genreChoiceBox.getSelectionModel().getSelectedItem());
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
    void deleteMovieGenre(ActionEvent event) {
        if (queries.deleteMovieGenre(moviesGenreTable.getSelectionModel().getSelectedItem().getFilmID(),
                moviesGenreTable.getSelectionModel().getSelectedItem().getGenreCategory()) == 1) {
            moviesGenreTable.getItems().removeAll(moviesGenreTable.getSelectionModel().getSelectedItem());
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
    void retrieveMovieGenre(MouseEvent event) {
        genreChoiceBox.setValue(moviesGenreTable.getSelectionModel().getSelectedItem().getGenreCategory());
        movieChoiceBox.setValue(moviesGenreTable.getSelectionModel().getSelectedItem().getTitle());
    }
}