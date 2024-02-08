package application;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Company;
import entities.Movie;
import entities.MovieCompany;
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

public class Admin_CompanyController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private Button removeBtn, removeCompanyBtn, updateCompanyBtn, addCompanyBtn, addBtn;
    @FXML
    private ChoiceBox<String> companyChoiceBox, movieChoiceBox;
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
    private TextField companyTextField, revenueTextField;
    @FXML
    private TableView<MovieCompany> moviesCompanyTable;
    @FXML
    private TableView<Company> companyTable;
    ObservableList<Company> companies = FXCollections.observableArrayList();
    ObservableList<Movie> movies = FXCollections.observableArrayList();
    ObservableList<String> companyNames, movieTitles;
    ObservableList<MovieCompany> movieCompany = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        movieTitles = FXCollections.observableArrayList();
        companyNames = FXCollections.observableArrayList();
        for(Movie movie: movies)
            movieTitles.add(movie.getTitle());
        for(Company company: companies)
            companyNames.add(company.getCompanyName());
        companyCode.setCellValueFactory(new PropertyValueFactory<>("companyCode"));
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        companyCode1.setCellValueFactory(new PropertyValueFactory<>("companyCode"));
        companyName1.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        revenue.setCellValueFactory(new PropertyValueFactory<>("companyRevenue"));
        filmID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        filmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        companyChoiceBox.setItems(companyNames);
        movieChoiceBox.setItems(movieTitles);
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
        if (companyChoiceBox.getSelectionModel().getSelectedItem().equals("") ||
                movieChoiceBox.getSelectionModel().getSelectedItem().equals("")) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please choose from the options");
            message.show();
        }else {
            int result = queries.addMovieCompany(movieChoiceBox.getSelectionModel().getSelectedItem().toString(),
                    companyChoiceBox.getSelectionModel().getSelectedItem().toString());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Company attached to movie");
                message.show();
                movieChoiceBox.setValue("");
                companyChoiceBox.setValue("");
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
    }

    @FXML
    void retrieveMovieCompany(MouseEvent event) {
        companyChoiceBox.setValue(moviesCompanyTable.getSelectionModel().getSelectedItem().getCompanyName());
        movieChoiceBox.setValue(moviesCompanyTable.getSelectionModel().getSelectedItem().getTitle());
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