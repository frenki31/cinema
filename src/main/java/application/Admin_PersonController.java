package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Crew;
import entities.Department;
import entities.Movie;
import entities.Person;
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

public class Admin_PersonController implements Initializable{

    DBQuery queries = new DBQuery();
    @FXML
    private ChoiceBox<String> genderChoiceBox,typeChoiceBox;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private Button addPersonBtn,removePersonBtn,updatePersonBtn, refreshBtn;
    @FXML
    private TextField lastTextField,firstTextField,middleTextField;
    @FXML
    private TableColumn<Person, String> firstName,lastName,middleName, gender, type;
    @FXML
    private TableColumn<Person, Integer> personId;

    ObservableList<Person> people = FXCollections.observableArrayList();
    ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
    ObservableList<String> types = FXCollections.observableArrayList("Cast", "Crew");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }

    public void loadData() {
        queries.setConnection();
        refreshTable();
        personId.setCellValueFactory(new PropertyValueFactory<>("actorId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        genderChoiceBox.getItems().addAll(genders);
        typeChoiceBox.getItems().addAll(types);
    }
    @FXML
    public void refreshTable() {
        people.clear();
        people = FXCollections.observableArrayList(queries.getPerson());
        personTable.setItems(people);
    }
    @FXML
    void addPerson(ActionEvent event) {
        if (firstTextField.getText().isEmpty() || lastTextField.getText().isEmpty() ||
        genderChoiceBox.getValue().equals("") || typeChoiceBox.getValue().equals("")) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else {
            int result = queries.addPerson(firstTextField.getText(),middleTextField.getText(),
                    lastTextField.getText(),genderChoiceBox.getValue(),typeChoiceBox.getValue());
            if(result == 1) {
                Alert message = new Alert(AlertType.INFORMATION);
                message.setTitle("Added");
                message.setContentText("Person added successfully");
                message.show();
                firstTextField.setText("");
                middleTextField.setText("");
                lastTextField.setText("");
                genderChoiceBox.setValue("");
                typeChoiceBox.setValue("");
            }else {
                Alert message = new Alert(AlertType.ERROR);
                message.setTitle("ERROR");
                message.setContentText("ERROR!!! The person cannot be registered");
                message.show();
            }
        }
    }
    @FXML
    void deletePerson(ActionEvent event) {
        if (queries.deletePerson(personTable.getSelectionModel().getSelectedItem().getActorId()) == 1) {
            personTable.getItems().removeAll(personTable.getSelectionModel().getSelectedItem());
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Deleted");
            message.setContentText("Person Deleted Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
    @FXML
    void retrievePerson(MouseEvent event) {
        firstTextField.setText(personTable.getSelectionModel().getSelectedItem().getFirstName());
        middleTextField.setText(personTable.getSelectionModel().getSelectedItem().getMiddleName());
        lastTextField.setText(personTable.getSelectionModel().getSelectedItem().getLastName());
        genderChoiceBox.setValue(personTable.getSelectionModel().getSelectedItem().getGender());
        typeChoiceBox.setValue(personTable.getSelectionModel().getSelectedItem().getType());
    }
    @FXML
    void updatePerson(ActionEvent event) {
        if (firstTextField.getText().isEmpty() || lastTextField.getText().isEmpty() ||
                genderChoiceBox.getValue().equals("") || typeChoiceBox.getValue().equals("")) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please fill all the fields");
            message.show();
        }else if (queries.updatePerson(personTable.getSelectionModel().getSelectedItem().getActorId(),
                firstTextField.getText(),middleTextField.getText(), lastTextField.getText(),
                genderChoiceBox.getValue(), typeChoiceBox.getValue()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Updated");
            message.setContentText("Person Updated Successfully");
            message.show();
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}