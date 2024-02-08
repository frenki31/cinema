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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
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
public class Dashboard_Controller implements Initializable{

    double x,y = 0;
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
    ObservableList<String> dates = FXCollections.observableArrayList("1990-2000",
            "2001-2010","2011-2020","2021-2030");


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        genreComboBox.setItems(queries.getGenres());
        releaseComboBox.setItems(dates);
        HBox hbox = new HBox();
        hbox.setSpacing(30);
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllCovers());
        try {
            for(Movie movie: movies) {
                ObservableList<String> genres = FXCollections.observableArrayList(queries.getAllGenres(movie.getTitle()));
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Movie.fxml"));
                VBox vbox = loader.load();
                MovieController movieController = loader.getController();
                movieController.setMovie(movie,genres);
                hbox.getChildren().addAll(vbox);
                horizontalBox.getChildren().setAll(hbox);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Method to minimize the page
     * @param event
     */
    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    /**
     * Method to maximize the page
     * @param event
     */
    @FXML
    public void maximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
    }
    /**
     * Method to close the page
     * @param event
     */
    @FXML
    public void close(ActionEvent event) {
        Alert message = new Alert(AlertType.CONFIRMATION);
        message.setTitle("Exit");
        message.setContentText("Do you want to exit?");
        if(message.showAndWait().get() == ButtonType.OK) {
            System.exit(0);;
        }
    }
    @FXML
    public void displayYears(ActionEvent event){
        HBox hBox = new HBox();
        hBox.setSpacing(30);
        horizontalBox.getChildren().clear();
        String selectedItem = releaseComboBox.getSelectionModel().getSelectedItem().toString();
        String[] selectedYears = selectedItem.split("-");
        String firstYear = selectedYears[0];
        String secondYear = selectedYears[1];
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.clasifyMoviesByYear(Integer.parseInt(firstYear),Integer.parseInt(secondYear)));
        for (Movie movie: movies) {
            try {
                ObservableList<String> genres = FXCollections.observableArrayList(queries.getAllGenres(movie.getTitle()));
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Movie.fxml"));
                VBox vbox = loader.load();
                MovieController mc = loader.getController();
                mc.setMovie(movie, genres);
                hBox.getChildren().addAll(vbox);

            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        horizontalBox.getChildren().setAll(hBox);
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
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllCovers());
        try {
            for(Movie movie: movies) {
                ObservableList<String> genres = FXCollections.observableArrayList(queries.getAllGenres(movie.getTitle()));
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Movie.fxml"));
                VBox vbox = loader.load();
                MovieController movieController = loader.getController();
                movieController.setMovie(movie,genres);
                hbox.getChildren().addAll(vbox);
                horizontalBox.getChildren().setAll(hbox);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        horizontalBox.getChildren().clear();
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.getAllComingSoon());
        for (Movie movie: movies) {
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
            horizontalBox.getChildren().clear();
            ObservableList<Movie> movies;
            if (searchTextField.getText().isEmpty())
                movies = FXCollections.observableArrayList(queries.getAllCovers());
            else
                movies = FXCollections.observableArrayList(queries.searchMovies(searchTextField.getText()));
            for (Movie movie: movies) {
                try {
                    ObservableList<String> genres = FXCollections.observableArrayList(queries.getAllGenres(movie.getTitle()));
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/Movie.fxml"));
                    VBox vbox = loader.load();
                    MovieController mc = loader.getController();
                    mc.setMovie(movie,genres);
                    hBox.getChildren().add(vbox);
                    horizontalBox.getChildren().setAll(hBox);
                }catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void displayGenres(ActionEvent event) {
        HBox hBox = new HBox();
        hBox.setSpacing(30);
        horizontalBox.getChildren().clear();
        String selectedItem = genreComboBox.getSelectionModel().getSelectedItem().toString();
        ObservableList<Movie> movies = FXCollections.observableArrayList(queries.clasifyMoviesByGenres(selectedItem));
        for (Movie movie: movies) {
            try {
                ObservableList<String> genres = FXCollections.observableArrayList(queries.getAllGenres(movie.getTitle()));
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Movie.fxml"));
                VBox vbox = loader.load();
                MovieController mc = loader.getController();
                mc.setMovie(movie, genres);
                hBox.getChildren().addAll(vbox);
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        horizontalBox.getChildren().setAll(hBox);
    }
}
