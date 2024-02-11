package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Genre;
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
import javafx.scene.layout.VBox;

public class Admin_GenreController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> movieChoiceBox;
    @FXML
    private TableColumn<Genre, Integer> genreId;
    @FXML
    private TableColumn<MovieGenre, String> genreId1, genreCategory, genreCategory1, filmTitle;
    @FXML
    private TableColumn<MovieGenre, Integer> filmID;
    @FXML
    private TextField genreTextField, searchGenreTextField, genreTextField1, searchMovieTextField, searchMovieGenreTextField;
    @FXML
    private TableView<MovieGenre> moviesGenreTable;
    @FXML
    private TableView<Genre> genreTable;
    ObservableList<Genre> genres = FXCollections.observableArrayList();
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
        genreId.setCellValueFactory(new PropertyValueFactory<>("id"));
        genreCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        genreId1.setCellValueFactory(new PropertyValueFactory<>("genreID"));
        genreCategory1.setCellValueFactory(new PropertyValueFactory<>("genreCategory"));
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        movieChoiceBox.getItems().addAll(movieTitles);
        FilteredList<Genre> filteredGenre = new FilteredList<>(genres);
        searchGenreTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredGenre.setPredicate(searchGenreModel -> {
                if (newValue.isEmpty())
                    return true;
                String genreKeyword = newValue.toLowerCase();
                return searchGenreModel.getCategory().toLowerCase().contains(genreKeyword);
            });
        });
        SortedList<Genre> sortedGenre = new SortedList<>(filteredGenre);
        sortedGenre.comparatorProperty().bind(genreTable.comparatorProperty());
        genreTable.setItems(sortedGenre);
        FilteredList<String> filteredMovies = new FilteredList<>(movieTitles, b-> true);
        searchMovieTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMovies.setPredicate(searchMovieModel -> {
                if(newValue.isEmpty())
                    return true;
                String movieKeyword = newValue.toLowerCase();
                return searchMovieModel.toLowerCase().contains(movieKeyword);
            });
        });
        SortedList<String> sortedMovies = new SortedList<>(filteredMovies);
        movieChoiceBox.setItems(sortedMovies);
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
        if (genreTextField1.getText().isEmpty() || movieChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        }else {
            int result = queries.addMovieGenre(movieChoiceBox.getSelectionModel().getSelectedItem(),
                    genreTextField1.getText());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Genre attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                genreTextField1.setText("");
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
        genreTextField1.setText(genreTable.getSelectionModel().getSelectedItem().getCategory());
    }

    @FXML
    void retrieveMovieGenre(MouseEvent event) {
        genreTextField1.setText(moviesGenreTable.getSelectionModel().getSelectedItem().getGenreCategory());
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