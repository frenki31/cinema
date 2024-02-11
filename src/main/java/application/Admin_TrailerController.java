package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.*;
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

public class Admin_TrailerController implements Initializable{
    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> movieChoiceBox;
    @FXML
    private TableView<MovieTrailer> trailerTable;
    @FXML
    private TextField trailerTextField, searchMovieTextField, linkTextField;
    @FXML
    private TableColumn<MovieTrailer, String> title,trailer,link;
    @FXML
    private TableColumn<MovieTrailer, Integer> movieId,trailerId;
    ObservableList<MovieTrailer> trailers = FXCollections.observableArrayList();
    ObservableList<String> movieTitles;
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        for (Movie movie: movies){
            movieTitles.add(movie.getTitle());
        }
        movieId.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        trailerId.setCellValueFactory(new PropertyValueFactory<>("trailerId"));
        trailer.setCellValueFactory(new PropertyValueFactory<>("trailer"));
        link.setCellValueFactory(new PropertyValueFactory<>("trailerLink"));

        movieChoiceBox.getItems().addAll(movieTitles);
        movieChoiceBox.valueProperty().addListener((obs,old,newVal) ->{
            trailerTextField.setText(newVal + " Trailer");
        });
        Admin_CrewController.Filtering(movieTitles,searchMovieTextField,movieChoiceBox);
        FilteredList<MovieTrailer> movieTrailerFilteredList = new FilteredList<>(trailers, b->true);
        searchMovieTextField.textProperty().addListener((obs,old,newVal) -> {
            movieTrailerFilteredList.setPredicate(searchMovieTrailerModel -> {
                if (newVal.isEmpty())
                    return true;
                String movieTrailerKey = newVal.toLowerCase();
                return searchMovieTrailerModel.getTitle().toLowerCase().contains(movieTrailerKey);
            });
        });
        SortedList<MovieTrailer> movieTrailerSortedList = new SortedList<>(movieTrailerFilteredList);
        movieTrailerSortedList.comparatorProperty().bind(trailerTable.comparatorProperty());
        trailerTable.setItems(movieTrailerSortedList);
    }
    @FXML
    public void refreshTable() {
        trailers.clear();
        movies.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        trailers = FXCollections.observableArrayList(queries.getTrailers());
        trailerTable.setItems(trailers);
    }
    @FXML
    void addTrailer(ActionEvent event) {
        if (trailerTextField.getText().isEmpty() || movieChoiceBox.getValue().isEmpty() ||
        linkTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addTrailer(movieChoiceBox.getValue(),trailerTextField.getText(),linkTextField.getText());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Trailer added successfully");
                message.show();
                trailerTextField.setText("");
                movieChoiceBox.setValue("");
                linkTextField.setText("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The trailer cannot be registered");
                message.show();
            }
        }
    }
    @FXML
    void deleteTrailer(ActionEvent event) {
        if (queries.deleteTrailer(trailerTable.getSelectionModel().getSelectedItem().getFilmID(),
                trailerTable.getSelectionModel().getSelectedItem().getTrailerId()) == 1) {
            trailerTable.getItems().removeAll(trailerTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Trailer Deleted Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void retrieveTrailer(MouseEvent event) {
        trailerTextField.setText(trailerTable.getSelectionModel().getSelectedItem().getTrailer());
        movieChoiceBox.setValue(trailerTable.getSelectionModel().getSelectedItem().getTitle());
        linkTextField.setText(trailerTable.getSelectionModel().getSelectedItem().getTrailerLink());
    }
    @FXML
    void updateTrailer(ActionEvent event) {
        if (trailerTextField.getText().isEmpty() || movieChoiceBox.getValue().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else if (queries.updateTrailer(trailerTable.getSelectionModel().getSelectedItem().getFilmID(),
                trailerTable.getSelectionModel().getSelectedItem().getTrailerId(), linkTextField.getText()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Trailer Updated Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}