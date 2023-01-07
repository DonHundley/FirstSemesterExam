package gui.controller;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DatabaseConnector;
import gui.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {


    @FXML
    private Button addZoomMIButton, subZoomMIButton, movieSearchMIButton, homeMIButton, refreshMIButton,
            forwardMIButton, backMIButton, addMovieButton, deleteMovieButton, mediaPlayerButton, rateMovieButton, refreshTVButton;

    @FXML
    private TableColumn<Movie, Integer> columnID;
    @FXML
    private TableColumn<Movie, String> columnTitle;
    @FXML
    private TableColumn<Movie, Float> columnRating;
    @FXML
    private TableColumn<Movie, String> columnFile;
    @FXML
    private TableColumn<Movie, Date> columnLastView;
    @FXML
    private TableColumn<Movie, Float> columnIMDBRating;
    @FXML
    private TableView<Movie> movieTV;

    @FXML
    private WebView webView;
    private WebEngine engine;


    @FXML
    private TextField filterTextField;
    @FXML
    private TextField movieInfoTextField;


    private Model model = new Model();



    @Override
    public void initialize(URL url, ResourceBundle resource) {

        setTV();

        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        columnLastView.setCellValueFactory(new PropertyValueFactory<>("lastView"));
        columnIMDBRating.setCellValueFactory(new PropertyValueFactory<>("IMDBRating"));

        tableFilter();
        webViewIMDB();
    }

    /**
     * This takes our table view text input and compares it to the data in the table view.
     * If there is a match with the user search, it will display those matches.
     *
     * TO DO: Allow for search of a rating + all ratings above that rating.
     */
    private void tableFilter()
    {
        FilteredList<Movie> filteredData = new FilteredList<>(model.getObsMovies(), b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Movie -> {

                // If there is no search value then it will continue to display as normal.
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (Movie.toString().toLowerCase().contains(searchKeyword)) {
                    return true; //This means we found a match.
                } else
                    return false;
            });

        });

        SortedList<Movie> sortedData = new SortedList<>(filteredData);

        // Bind sorted result to Tableview.
        sortedData.comparatorProperty().bind(movieTV.comparatorProperty());

        // Apply filtered and sorted data to the Tableview.
        movieTV.setItems(sortedData);
    }

    /**
     * This method is used to set up our webview.
     * The listener searches for the movie title when clicked in the table view.
     */
    private void webViewIMDB()  {

        engine = webView.getEngine();

        engine.load("https://www.allmovie.com");
        webView.setZoom(0.75);

        movieTV.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldUser, selectedUser) -> {
                    engine.load("https://www.allmovie.com/search/all/" + selectedUser.getName());

                });
    }
    
    @FXML
    private void addZoomMI(ActionEvent actionEvent) {webView.setZoom(webView.getZoom() + 0.25);}
    @FXML
    private void subZoomMI(ActionEvent actionEvent) {webView.setZoom(webView.getZoom() - 0.25);}
    @FXML
    private void movieSearchMI(ActionEvent actionEvent) {engine.load("https://www.allmovie.com/search/all/"+movieInfoTextField.getText());}
    @FXML
    private void homeMI(ActionEvent actionEvent) {engine.load("https://www.allmovie.com");}
    @FXML
    private void refreshMI(ActionEvent actionEvent) {engine.reload();}
    @FXML
    private void forwardMI(ActionEvent actionEvent) {engine.getHistory().go(1);}
    @FXML
    private void backMI(ActionEvent actionEvent) {engine.getHistory().go(-1);}
    @FXML
    private void refreshTV(ActionEvent actionEvent) {setTV();}
    @FXML
    private void rateMovie(ActionEvent actionEvent) {}

    /**
     * opens a new window to add a new movie
     */
    public void openNewMovieWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/NewMovieWindow.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Add new movie");
        stage.setScene(scene);
        stage.show();
    }

    public void launchMediaPlayer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/MediaPlayer.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Media Player");
        stage.show();

    }
    /**
     * Method opens a confirmation window to confirm to delete the selected movie
     */
    @FXML
    private void openDeleteConfirmationWindow(ActionEvent actionEvent) throws IOException {

        Movie selectedMovie = movieTV.getSelectionModel().getSelectedItem();
        int selectedMovieID = selectedMovie.getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Do you really want to DELETE the movie?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) { //... user chose OK
            model.deleteMovie(selectedMovie);
            movieTV.getItems().remove(selectedMovie);
            alert.close();
        } else {
            alert.close();
        }
    }
    private void setTV(){movieTV.setItems(model.getObsMovies());model.loadMovieList();}
}
