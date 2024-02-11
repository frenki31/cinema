package application;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Admin_MoviesController implements Initializable {
    DBQuery queries = new DBQuery();
    @FXML
    private Stage stage;
    @FXML
    private Label username;
    @FXML
    private TableColumn<Movie, String> cover, filmTitle, runningTime, script, status;
    @FXML
    private TableColumn<Movie, Integer> filmID, ratingNo, totalRating;
    @FXML
    private TableColumn<Movie, Double> budget, revenue, rating;
    @FXML
    private TableColumn<Movie, LocalDate> release;
    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TextField coverTextField, descrtiptionTextField, durationTextField, budgetTextField,
    titleTextField, revenueTextField, searchMovieTextField;
    @FXML
    private DatePicker calendar;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    String[] statuses = {"Released", "Rumored", "In Production"};
    ObservableList<Movie> movieList = FXCollections.observableArrayList();

    public void setAdminUser(String user){
        username.setText(user);
        movieList.clear();
        movieList.addAll(queries.getAllMovies(username.getText()));
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }
    /**
     * Method to load all movies data into a table
     */
    public void loadData() {
        queries.setConnection();
        movieList = FXCollections.observableArrayList(queries.getAllMovies(username.getText()));
        refreshTable();

        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        runningTime.setCellValueFactory(new PropertyValueFactory<>("duration"));
        cover.setCellValueFactory(new PropertyValueFactory<>("cover"));
        script.setCellValueFactory(new PropertyValueFactory<>("description"));
        release.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        budget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        revenue.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingNo.setCellValueFactory(new PropertyValueFactory<>("nrOfRatings"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalRating.setCellValueFactory(new PropertyValueFactory<>("totalRating"));
        statusChoiceBox.getItems().setAll(statuses);
        FilteredList<Movie> filteredMovies = new FilteredList<>(movieList, b->true);
        searchMovieTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredMovies.setPredicate(searchMovieModel -> {
                if(newValue.isEmpty())
                    return true;
                String movieKeyword = newValue.toLowerCase();
                return searchMovieModel.getTitle().toLowerCase().contains(movieKeyword)
                        || searchMovieModel.getStatus().toLowerCase().contains(movieKeyword);
            });
        });
        SortedList<Movie> sortedMovie = new SortedList<>(filteredMovies);
        sortedMovie.comparatorProperty().bind(moviesTable.comparatorProperty());
        moviesTable.setItems(sortedMovie);
    }

    @FXML
    public void refreshTable() {
        movieList.clear();
        movieList.addAll(queries.getAllMovies(username.getText()));
        moviesTable.setItems(movieList);
    }
    /**
     * Method to add a movie
     * @param event
     */
    @FXML
    public void addMovie(ActionEvent event) {
        if (titleTextField.getText().isEmpty() || durationTextField.getText().isEmpty()
                || coverTextField.getText().isEmpty() || descrtiptionTextField.getText().isEmpty()
                || calendar.getValue().toString().isEmpty() || budgetTextField.getText().isEmpty()
                || revenueTextField.getText().isEmpty() || statusChoiceBox.getSelectionModel().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addMovie(titleTextField.getText(),budgetTextField.getText(),
                    descrtiptionTextField.getText(),revenueTextField.getText(),
                    calendar.getValue(), durationTextField.getText(),statusChoiceBox.getValue(),
                    coverTextField.getText(), username.getText());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("The movie has been added successfully");
                message.show();
                titleTextField.setText("");;
                durationTextField.setText("");;
                coverTextField.setText("");;
                descrtiptionTextField.setText("");
                budgetTextField.setText("");
                revenueTextField.setText("");
                calendar.setValue(null);
                statusChoiceBox.setValue(null);
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The movie cannot be registered");
                message.show();
            }
        }
    }
    /**
     * Method to delete a movie
     * @param event
     */
    @FXML
    public void deleteData(ActionEvent event) {
        if (queries.deleteMovie(moviesTable.getSelectionModel().getSelectedItem().getFilmID()) == 1) {
            moviesTable.getItems().removeAll(moviesTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Movie Deleted Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    public void uploadPath(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Files");
        fileChooser.setInitialDirectory(new File("C:/Users/user/IdeaProjects/Cinematrix/src/main/resources/images"));
        File selected = fileChooser.showOpenDialog(stage);
        if(selected != null) {
            coverTextField.setText(selected.getPath());
        }
    }
    @FXML
    public void clearFields(ActionEvent event) {
        titleTextField.setText("");;
        durationTextField.setText("");;
        coverTextField.setText("");;
        descrtiptionTextField.setText("");
        budgetTextField.setText("");
        revenueTextField.setText("");
        calendar.setValue(null);
        statusChoiceBox.setValue(null);
    }
    /**
     * Method to update movie information
     * @param event
     */
    @FXML
    public void updateMovie(ActionEvent event) {
        if(titleTextField.getText().isEmpty() || durationTextField.getText().isEmpty()
                || coverTextField.getText().isEmpty() || descrtiptionTextField.getText().isEmpty()
                || calendar.getValue().toString().isEmpty() || budgetTextField.getText().isEmpty()
                || revenueTextField.getText().isEmpty() || statusChoiceBox.getSelectionModel().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill in all the blanks");
            message.show();
        }else if (queries.updateMovie(moviesTable.getSelectionModel().getSelectedItem().getFilmID(),
                titleTextField.getText(),budgetTextField.getText(),
                descrtiptionTextField.getText(),revenueTextField.getText(),
                calendar.getValue(), durationTextField.getText(),statusChoiceBox.getValue(),
                coverTextField.getText()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Movie Updated Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }

    @FXML
    public void retrieveData(MouseEvent event) {
        titleTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getTitle());
        durationTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getDuration().toString());
        coverTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getCover());
        descrtiptionTextField.setText(moviesTable.getSelectionModel().getSelectedItem().getDescription());
        calendar.setValue(moviesTable.getSelectionModel().getSelectedItem().getReleaseDate());
        budgetTextField.setText(String.valueOf(moviesTable.getSelectionModel().getSelectedItem().getBudget()));
        revenueTextField.setText(String.valueOf(moviesTable.getSelectionModel().getSelectedItem().getRevenue()));
        statusChoiceBox.setValue(moviesTable.getSelectionModel().getSelectedItem().getStatus());
    }
}
