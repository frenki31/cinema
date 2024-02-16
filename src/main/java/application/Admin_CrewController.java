package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Crew;
import entities.Movie;
import entities.Person;
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

public class Admin_CrewController implements Initializable{
    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> actorChoiceBox,jobChoiceBox,movieChoiceBox;
    @FXML
    private TableView<Crew> crewTable;
    @FXML
    private TextField searchMovieTextField, searchCrewTextField, searchDepartmentTextField;
    @FXML
    private TableColumn<Crew, Integer> filmID,personId;
    @FXML
    private TableColumn<Crew, String> filmTitle,job,personName;
    String[] departments = {"Camera","Directing","Production","Writing","Editing",
    "Sound","Art","Costume & Make-Up","Visual Effects","Lighting"};
    ObservableList<Person> people = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> movieTitles, actorNames;
    ObservableList<Crew> crew = FXCollections.observableArrayList();
    ObservableList<String> departmentNames = FXCollections.observableArrayList(departments);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        actorNames = FXCollections.observableArrayList();
        departmentNames = FXCollections.observableArrayList();
        for(Movie movie: movies)
            movieTitles.add(movie.getTitle());
        for(Person person: people)
            actorNames.add(person.getName());
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        personId.setCellValueFactory(new PropertyValueFactory<>("personId"));
        personName.setCellValueFactory(new PropertyValueFactory<>("personName"));
        job.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        actorChoiceBox.getItems().addAll(actorNames);
        movieChoiceBox.getItems().addAll(movieTitles);
        jobChoiceBox.getItems().addAll(departmentNames);
        Filtering(movieTitles, searchMovieTextField, movieChoiceBox);
        Filtering(actorNames, searchCrewTextField, actorChoiceBox);
        Filtering(departmentNames, searchDepartmentTextField, jobChoiceBox);
        FilteredList<Crew> filteredCrew = new FilteredList<>(crew, b->true);
        searchCrewTextField.textProperty().addListener((obs, old, newVal)->{
            filteredCrew.setPredicate(searchCrewModel -> {
                if (newVal.isEmpty())
                    return true;
                String crewKeyword = newVal.toLowerCase();
                return searchCrewModel.getJobTitle().toLowerCase().contains(crewKeyword) ||
                        searchCrewModel.getTitle().toLowerCase().contains(crewKeyword) ||
                        searchCrewModel.getPersonName().toLowerCase().contains(crewKeyword);
            });
        });
        SortedList<Crew> sortedCrew = new SortedList<>(filteredCrew);
        sortedCrew.comparatorProperty().bind(crewTable.comparatorProperty());
        crewTable.setItems(sortedCrew);
    }
    public static void Filtering(ObservableList<String> Names, TextField searchTextField, ChoiceBox<String> ChoiceBox) {
        FilteredList<String> filteredList = new FilteredList<>(Names, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(depSearchModel -> {
                if(newValue.isEmpty() || newValue.isBlank())
                    return true;
                String searchKeyword = newValue.toLowerCase();
                return depSearchModel.toLowerCase().contains(searchKeyword);
            });
        });
        SortedList<String> sortedList = new SortedList<>(filteredList);
        ChoiceBox.setItems(sortedList);
    }

    @FXML
    public void refreshTable() {
        movies.clear();
        people.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        people = FXCollections.observableArrayList(queries.getCrewMembers());
        crew = FXCollections.observableArrayList(queries.getCrew());
        crewTable.setItems(crew);
    }
    @FXML
    void addCrew(ActionEvent event) {
        if (actorChoiceBox.getValue().isEmpty() || movieChoiceBox.getValue().isEmpty() ||
                jobChoiceBox.getValue().isEmpty()){
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addCrew(movieChoiceBox.getValue(),actorChoiceBox.getValue(),
                    jobChoiceBox.getValue());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Worker attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                actorChoiceBox.setValue("");
                jobChoiceBox.setValue("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! Cannot attach worker to movie");
                message.show();
            }
        }
    }
    @FXML
    void deleteCrew(ActionEvent event) {
        if (queries.deleteCrew(crewTable.getSelectionModel().getSelectedItem().getFilmID(),
                crewTable.getSelectionModel().getSelectedItem().getPersonId()) == 1) {
            crewTable.getItems().removeAll(crewTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Crew deleted successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void retrieveCrew(MouseEvent event) {
        actorChoiceBox.setValue(crewTable.getSelectionModel().getSelectedItem().getPersonName());
        movieChoiceBox.setValue(crewTable.getSelectionModel().getSelectedItem().getTitle());
        jobChoiceBox.setValue(crewTable.getSelectionModel().getSelectedItem().getJobTitle());
    }
}