package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Class for administrator interface
 * @author user
 *
 */
public class Admin_Controller implements Initializable{

    double x,y = 0;

    @FXML
    private Parent parent;
    @FXML
    private VBox allBox;
    @FXML
    private Button castBtn, closeButton, companiesBtn, countriesBtn, crewBtn,
            genresBtn, languagesBtn, maximizeButton, minimizeButton, moviesBtn,
            trailersBtn, personBtn, homeBtn;
    @FXML
    private FontAwesomeIcon castIcon, companyIcon, countryIcon, crewIcon, genresIcon,
            languageIcon, movieIcon, personIcon, trailerIcon, homeIcon;
    @FXML
    private BorderPane scene;
    @FXML
    private Label usernameLabel;

    public void setUser(String username){
        usernameLabel.setText(username);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            parent = FXMLLoader.load(getClass().getResource("/views/Admin_Dashboard.fxml"));
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    public void makeDraggable(MouseEvent event) {
        scene.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });

        scene.setOnMouseDragged(e -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });
    }

    @FXML
    public void maximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
    }

    @FXML
    public void minimize(ActionEvent event) {
        try {
            parent = FXMLLoader.load(getClass().getResource("/views/Admin_Page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @FXML
    public void openHome(ActionEvent event) {
        try {
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            homeIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));

            parent = FXMLLoader.load(getClass().getResource("/views/Admin_Dashboard.fxml"));
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Method to open the movies page
     * @param event
     */
    @FXML
    public void openMovies(ActionEvent event) {
        try {
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            movieIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Movies.fxml"));
            parent = loader.load();
            Admin_MoviesController amc = loader.getController();
            amc.setAdminUser(usernameLabel.getText());
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void openCountries(ActionEvent event) {
        try {
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            countryIcon.setFill(Color.WHITE);
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Countries.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void openCast(ActionEvent event) {
        try {
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            castIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Cast.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @FXML
    void openPerson(ActionEvent event) {
        try {
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            personIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Person.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void openCompanies(ActionEvent event) {
        try {
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            companyIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Company.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void openCrew(ActionEvent event) {
        try {
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            crewIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Crew.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void openGenres(ActionEvent event) {
        try {
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            genresIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Genre.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void openLanguages(ActionEvent event) {
        try {
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            languageIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            trailerIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Languages.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @FXML
    void openTrailers(ActionEvent event) {
        try {
            trailersBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-border-width: 0 0 0 5;"
                    + " -fx-border-color: #1ED760; -fx-text-fill: WHITE;");
            trailerIcon.setFill(Color.WHITE);
            countriesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            countryIcon.setFill(Color.web("#A1A1A1"));
            companiesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            companyIcon.setFill(Color.web("#A1A1A1"));
            moviesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            movieIcon.setFill(Color.web("#A1A1A1"));
            genresBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            genresIcon.setFill(Color.web("#A1A1A1"));
            castBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            castIcon.setFill(Color.web("#A1A1A1"));
            crewBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            crewIcon.setFill(Color.web("#A1A1A1"));
            personBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            personIcon.setFill(Color.web("#A1A1A1"));
            languagesBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            languageIcon.setFill(Color.web("#A1A1A1"));
            homeBtn.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: #A1A1A1;");
            homeIcon.setFill(Color.web("#A1A1A1"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Admin_Trailers.fxml"));
            parent = loader.load();
            allBox.getChildren().removeAll();
            allBox.getChildren().setAll(parent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
