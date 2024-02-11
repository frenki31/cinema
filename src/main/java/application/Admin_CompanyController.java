package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Company;
import entities.Movie;
import entities.MovieCompany;
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

public class Admin_CompanyController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> movieChoiceBox;
    @FXML
    private TableColumn<Company, Integer> companyCode;
    @FXML
    private TableColumn<MovieCompany, Integer> companyCode1;
    @FXML
    private TableColumn<Company, String> companyName, revenue;
    @FXML
    private TableColumn<MovieCompany, String> companyName1;
    @FXML
    private TableColumn<MovieCompany, Integer> filmID;
    @FXML
    private TableColumn<MovieCompany, String> filmTitle;
    @FXML
    private TextField companyTextField, revenueTextField, searchCompanyTextField, searchMovieTextField,
            companyTextField1, searchMovieCompTextField;
    @FXML
    private TableView<MovieCompany> moviesCompanyTable;
    @FXML
    private TableView<Company> companyTable;
    ObservableList<Company> companies = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> movieTitles;
    ObservableList<MovieCompany> movieCompany = FXCollections.observableArrayList();
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
        companyCode.setCellValueFactory(new PropertyValueFactory<>("companyCode"));
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        companyCode1.setCellValueFactory(new PropertyValueFactory<>("companyCode"));
        companyName1.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        revenue.setCellValueFactory(new PropertyValueFactory<>("companyRevenue"));
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        movieChoiceBox.setItems(movieTitles);

        Admin_CrewController.Filtering(movieTitles, searchMovieTextField, movieChoiceBox);
        FilteredList<Company> filteredCompany = new FilteredList<>(companies, b -> true);
        searchCompanyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCompany.setPredicate(companySearchModel -> {
                if(newValue.isEmpty() || newValue.isBlank())
                    return true;
                String searchCompanyKeyword = newValue.toLowerCase();
                return companySearchModel.getCompanyName().toLowerCase().contains(searchCompanyKeyword);
            });
        });
        SortedList<Company> sortedCompany = new SortedList<>(filteredCompany);
        sortedCompany.comparatorProperty().bind(companyTable.comparatorProperty());
        companyTable.setItems(sortedCompany);
        FilteredList<MovieCompany> movieCompanyFilteredList = new FilteredList<>(movieCompany, b->true);
        searchMovieCompTextField.textProperty().addListener((obs,old,newVal) -> {
            movieCompanyFilteredList.setPredicate(searchMovieCompModel -> {
                if (newVal.isEmpty())
                    return true;
                String movieCompKey = newVal.toLowerCase();
                return searchMovieCompModel.getTitle().toLowerCase().contains(movieCompKey) ||
                        searchMovieCompModel.getCompanyName().toLowerCase().contains(movieCompKey);
            });
        });
        SortedList<MovieCompany> movieCompanySortedList = new SortedList<>(movieCompanyFilteredList);
        movieCompanySortedList.comparatorProperty().bind(moviesCompanyTable.comparatorProperty());
        moviesCompanyTable.setItems(movieCompanySortedList);
    }
    @FXML
    public void refreshTable() {
        movies.clear();
        companies.clear();
        movies = FXCollections.observableArrayList(queries.getMovieTitles());
        companies = FXCollections.observableArrayList(queries.getCompany());
        movieCompany = FXCollections.observableArrayList(queries.getMovieCompany());
        companyTable.setItems(companies);
        moviesCompanyTable.setItems(movieCompany);
    }
    @FXML
    void addCompany(ActionEvent event) {
        if (companyTextField.getText().isEmpty() || revenueTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addCompany(companyTextField.getText(), Double.parseDouble(revenueTextField.getText()));
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Company added successfully");
                message.show();
                companyTextField.setText("");
                revenueTextField.setText("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The movie cannot be registered");
                message.show();
            }
        }
    }
    @FXML
    void addMovieCompany(ActionEvent event) {
        if(movieChoiceBox.getSelectionModel().getSelectedItem().isEmpty() || companyTextField1.getText().isEmpty()){
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        }else {
            int result = queries.addMovieCompany(movieChoiceBox.getSelectionModel().getSelectedItem().toString(),
                    companyTextField1.getText());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Company attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                companyTextField1.setText("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! Cannot attach Company to movie");
                message.show();
            }
        }
    }
    @FXML
    void deleteCompany(ActionEvent event) {
        if (queries.deleteCompany(companyTable.getSelectionModel().getSelectedItem().getCompanyCode()) == 1) {
            companyTable.getItems().removeAll(companyTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Company Deleted Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void deleteMovieCompany(ActionEvent event) {
        if (queries.deleteMovieCompany(moviesCompanyTable.getSelectionModel().getSelectedItem().getFilmID(),
                moviesCompanyTable.getSelectionModel().getSelectedItem().getCompanyCode()) == 1) {
            companyTable.getItems().removeAll(companyTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Movie-Company relationship deleted successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void retrieveCompany(MouseEvent event) {
        companyTextField.setText(companyTable.getSelectionModel().getSelectedItem().getCompanyName());
        revenueTextField.setText(String.valueOf(companyTable.getSelectionModel().getSelectedItem().getCompanyRevenue()));
        companyTextField1.setText(companyTable.getSelectionModel().getSelectedItem().getCompanyName());
    }
    @FXML
    void retrieveMovieCompany(MouseEvent event) {
        movieChoiceBox.setValue(moviesCompanyTable.getSelectionModel().getSelectedItem().getTitle());
        companyTextField1.setText(moviesCompanyTable.getSelectionModel().getSelectedItem().getCompanyName());
    }
    @FXML
    void updateCompany(ActionEvent event) {
        if(companyTextField.getText().isEmpty() || revenueTextField.getText().isEmpty()) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill in all the blanks");
            message.show();
        }else if (queries.updateCompany(companyTable.getSelectionModel().getSelectedItem().getCompanyCode(),
                companyTextField.getText(), Double.parseDouble(revenueTextField.getText())) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Company Updated Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}