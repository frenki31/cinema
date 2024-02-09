package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Movie;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * Class Dashboard which is the main display of the application
 * @author user
 *
 */
public class Dashboard_Controller implements Initializable {

    double x, y = 0;
    DBQuery queries = new DBQuery();
    @FXML
    private ComboBox<String> genreComboBox, releaseComboBox;
    @FXML
    private Button homeBtn, adminBtn;
    @FXML
    private HBox horizontalBox;
    @FXML
    private Parent parent;
    @FXML
    private BorderPane scene;
    @FXML
    private Stage stage;
    @FXML
    private FontAwesomeIcon homeIcon, adminIcon;
    @FXML
    private TextField searchTextField;
    @FXML
    private Label genreLabel, releaseLabel;
    ObservableList<String> dates = FXCollections.observableArrayList("1990-2000",
            "2001-2010", "2011-2020", "2021-2030");


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        genreComboBox.setItems(queries.getGenres());
        releaseComboBox.setItems(dates);
        HBox hbox = new HBox();
        hbox.setSpacing(30);
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllCovers());
        for (Movie movie:movies){
            openPage(movie,hbox);
        }
        horizontalBox.getChildren().setAll(hbox);
    }

    /**
     * Method to minimize the page
     *
     * @param event
     */
    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Method to maximize the page
     *
     * @param event
     */
    @FXML
    public void maximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
    }

    /**
     * Method to close the page
     *
     * @param event
     */
    @FXML
    public void close(ActionEvent event) {
        Alert message = new Alert(AlertType.CONFIRMATION);
        message.setTitle("Exit");
        message.setContentText("Do you want to exit?");
        if (message.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
            ;
        }
    }

    @FXML
    void openMain(ActionEvent event) {
        homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
        adminBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
        adminIcon.setFill(Color.web("#A1A1A1"));
        homeIcon.setFill(Color.WHITE);

        HBox hbox = new HBox();
        hbox.setSpacing(30);
        resetData();
        horizontalBox.getChildren().clear();
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllCovers());
        for (Movie movie : movies) {
            openPage(movie, hbox);
        }
        horizontalBox.getChildren().setAll(hbox);
    }

    @FXML
    public void openAdmin(ActionEvent event) {
        homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
        homeIcon.setFill(Color.web("#A1A1A1"));
        adminBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
        adminIcon.setFill(Color.WHITE);

        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        try {
            parent = FXMLLoader.load(getClass().getResource("/views/Admin_Login.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openComingSoon(ActionEvent event) {
        HBox hbox = new HBox();
        hbox.setSpacing(30);
        resetData();
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllComingSoon());
        for (Movie movie : movies) {
            try {
                ObservableList<String> genres = FXCollections.observableArrayList(queries.getAllGenresComingSoon(movie.getTitle()));
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Movie.fxml"));
                VBox vBox = loader.load();
                MovieController mc = loader.getController();
                mc.setMovie(movie, genres);
                hbox.getChildren().add(vBox);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        horizontalBox.getChildren().setAll(hbox);
    }

    public void makeDraggable() {
        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    @FXML
    public void searchByTitle(KeyEvent event) {
        searchTextField.setOnKeyTyped(e -> {
            HBox hBox = new HBox();
            hBox.setSpacing(30);
            genreLabel.setText("GENRE");
            releaseLabel.setText("RELEASE YEAR");
            genreComboBox.getSelectionModel().clearSelection();
            releaseComboBox.getSelectionModel().clearSelection();
            ObservableList<Movie> movies;
            if (searchTextField.getText().isEmpty())
                movies = FXCollections.observableArrayList(queries.getAllCovers());
            else {
                movies = FXCollections.observableArrayList(queries.searchMovies(searchTextField.getText()));
            }
            for (Movie movie : movies) {
                openPage(movie, hBox);
            }
            horizontalBox.getChildren().setAll(hBox);
        });
    }

    @FXML
    public void displayGenresAndYear(ActionEvent event) {
        HBox hBox = new HBox();
        hBox.setSpacing(30);
        // Clear existing nodes in horizontalBox
        horizontalBox.getChildren().clear();

        String selectedYear = releaseComboBox.getSelectionModel().getSelectedItem();
        String selectedGenre = genreComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Movie> movies = null;
        searchTextField.setText("");

        if (selectedYear != null && selectedGenre != null) {
            // Both year and genre selected
            genreLabel.setText(selectedGenre);
            releaseLabel.setText(selectedYear);
            String[] selectedYears = selectedYear.split("-");
            int firstYear = Integer.parseInt(selectedYears[0]);
            int secondYear = Integer.parseInt(selectedYears[1]);
            movies = FXCollections.observableArrayList(
                    queries.clasifyMoviesByGenreAndYear(selectedGenre, firstYear, secondYear));
        } else if (selectedYear != null) {
            // Only year selected
            releaseLabel.setText(selectedYear);
            String[] selectedYears = selectedYear.split("-");
            int firstYear = Integer.parseInt(selectedYears[0]);
            int secondYear = Integer.parseInt(selectedYears[1]);
            movies = FXCollections.observableArrayList(
                    queries.clasifyMoviesByYear(firstYear, secondYear));
        } else if (selectedGenre != null) {
            // Only genre selected
            genreLabel.setText(selectedGenre);
            movies = FXCollections.observableArrayList(
                    queries.clasifyMoviesByGenres(selectedGenre));
        }
        if (movies == null) {
            movies = FXCollections.observableArrayList();
        }
        for (Movie movie : movies) {
            openPage(movie, hBox);
        }
        horizontalBox.getChildren().setAll(hBox);
    }

    public void resetData() {
        genreLabel.setText("GENRE");
        releaseLabel.setText("RELEASE YEAR");
        genreComboBox.getSelectionModel().clearSelection();
        releaseComboBox.getSelectionModel().clearSelection();
        searchTextField.setText("");
    }

    public void openPage(Movie movie, HBox hBox) {
        try {
            ObservableList<String> genres = FXCollections.observableArrayList(queries.getAllGenres(movie.getTitle()));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Movie.fxml"));
            VBox vbox = loader.load();
            MovieController mc = loader.getController();
            mc.setMovie(movie, genres);
            hBox.getChildren().addAll(vbox);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}