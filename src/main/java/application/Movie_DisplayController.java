package application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import entities.Cast;
import entities.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Movie_DisplayController {
    double x,y = 0;
    DBQuery queries = new DBQuery();

    @FXML
    private Parent parent;
    @FXML
    private Stage stage;
    @FXML
    private VBox scene;
    @FXML
    private ImageView cover;
    @FXML
    private Label description, duration, genre, release, country, ratingLabel,title;
    @FXML
    private Button closeButton,playButton,minimizeButton,maximizeButton,openTrailerButton,rateButton;
    @FXML
    private TableView<Cast> actor;
    @FXML
    private TableColumn<Cast, String> castName,castCharacter,castGender,castOrder;
    @FXML
    private TextField ratingTextField;
    @FXML
    private ComboBox<String> trailerComboBox;

    /**
     * Method to set all the data for movie display
     * @param movie
     */
    public void setData(Movie movie,ObservableList<String> genres,ObservableList<String> countries,
                        ObservableList<Cast> castList, ObservableList<String> trailers) {
        Image image = new Image(String.valueOf(new File(movie.getCover())));
        cover.setImage(image);
        title.setText(movie.getTitle());
        duration.setText(String.valueOf(movie.getDuration()));
        release.setText(String.valueOf(movie.getReleaseDate()));
        description.setText(movie.getDescription());
        ratingLabel.setText(String.valueOf(movie.getRating()));
        trailerComboBox.getItems().addAll(trailers);
        for(String genre1: genres)
            genre.setText(genre1);
        for(String country1: countries)
            country.setText(country1);
        actor.setItems(castList);
        castCharacter.setCellValueFactory(new PropertyValueFactory<>("character"));
        castOrder.setCellValueFactory(new PropertyValueFactory<>("order"));
        castName.setCellValueFactory(new PropertyValueFactory<>("actorName"));
        castGender.setCellValueFactory(new PropertyValueFactory<>("genderType"));
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        try {
            parent = FXMLLoader.load(getClass().getResource("/views/Dashboard.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
    }
    /**
     * Method to open the movie
     * @param event
     * @throws URISyntaxException
     */
    @FXML
    public void openTrailer(ActionEvent event) {
        HBox hbox = new HBox();
        String selectedTrailer = trailerComboBox.getSelectionModel().getSelectedItem().toString();
        for (String t: trailerComboBox.getItems()){
            if(t.equals(selectedTrailer)){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/WatchTrailer.fxml"));
                    VBox vbox = loader.load();
                    WatchTrailer_Controller wmc = loader.getController();
                    wmc.setLink(selectedTrailer);
                    hbox.getChildren().add(vbox);
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    Scene openScene = new Scene(hbox);
                    stage.setScene(openScene);
                    stage.show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void rate(ActionEvent event) {
        if(ratingTextField.getText().isEmpty() || Integer.valueOf(ratingTextField.getText()) < 1 ||
                Integer.valueOf(ratingTextField.getText())> 10) {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("Empty");
            message.setContentText("Please rate from 1-10");
            message.show();
        }else if (queries.rateMovie(Integer.valueOf(ratingTextField.getText()), title.getText()) == 1) {
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("Rated");
            message.setContentText("Thank you for your rate");
            message.show();
            ratingTextField.setText("");
        }else {
            Alert message = new Alert(AlertType.ERROR);
            message.setTitle("ERROR");
            message.setContentText("Something went wrong! Please try again...");
            message.show();
        }
    }
}
