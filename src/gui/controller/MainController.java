package gui.controller;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sun.jdi.event.MonitorContendedEnteredEvent;
import dal.DatabaseConnector;
import be.Result;
import be.TMDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {



    @FXML
    private ImageView imgPoster, backdropImage;
    @FXML
    private Label ratingTMDB, lblTitle, releaseDateTMDB, descriptionText;

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
    private TableColumn<Movie, String> columnCategories;
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

    static long millis = System.currentTimeMillis();
    static java.sql.Date date = new java.sql.Date(millis);

    private Model model = new Model();


    @Override
    public void initialize(URL url, ResourceBundle resource) {

        movieTV.setItems(model.getObsMovies());
        model.loadMovieList();


        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnCategories.setCellValueFactory(new PropertyValueFactory<>("categoriesAsString"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        columnLastView.setCellValueFactory(new PropertyValueFactory<>("lastView"));
        columnIMDBRating.setCellValueFactory(new PropertyValueFactory<>("IMDBRating"));

        tableFilter();
        webViewIMDB();
        movieInformation();
        moviesToDelete();
    }

    /**
     * This takes our table view text input and compares it to the data in the table view.
     * If there is a match with the user search, it will display those matches.
     * <p>
     * TO DO: Allow for search of a rating + all ratings above that rating.
     */
    private void tableFilter() {
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
    private void webViewIMDB() {

        engine = webView.getEngine();

        engine.load("https://www.allmovie.com");
        webView.setZoom(0.75);

        movieTV.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldUser, selectedUser) -> {
                    if (selectedUser == null){
                        engine.load("https://www.allmovie.com");
                    }
                    else {
                        engine.load("https://www.allmovie.com/search/all/" + selectedUser.getName());
                    }
                });
    }

    /**
     * This method is how we are fetching the data from TMDB to be displayed. Some of this code is reused from the example given by Jeppe.
     * It was not fully functional and only displayed the title.
     *
     * My additions:
     * This method takes our focused movie from the table view and immediately searches for it, replacing spaces in the title with +, otherwise the website would fail the search.
     * next we collect the information from TMDB and give it structure to be used for our needs.
     * We fetch the movie poster, the movie title, the user voted average rating from TMDB, their description of the movie, the release date, and finally we use the backdrop image from them as well.
     * The images require a very specific path. This was outlined on TMDBs website. The original code from Jeppe had the beginnings of this, but it did not function.
     * Using the example given by the website I found that there are only certain resolutions you can request.
     * However, if you request the original as I did in the image path it will give you that instead of your specified resolution and this image will scale to your specified window size.
     *
     * There are a lot more things that could be implemented with the API and some functions could be automated with this information.
     * More information on using the API can be found here https://developers.themoviedb.org/3/getting-started/introduction.
     */
    private void movieInformation(){
        movieTV.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldUser, selectedUser) -> {
                        try {
                            String imagePath="https://image.tmdb.org/t/p/original/";
                            String uri =
                                    "https://api.themoviedb.org/3/search/movie?api_key=b576033e42ffbda195c7a11b2a406a10&language=en-US&query="
                                            + selectedUser.getName().replace(" ","+") +
                                            "&page=1&include_adult=false";

                            URL url = new URL(uri);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setRequestProperty("Accept", "application/json");

                            if (conn.getResponseCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code :"
                                        + conn.getResponseCode());
                            }
                            try(Reader reader = new BufferedReader(new InputStreamReader(
                                    (conn.getInputStream())))){
                                Gson gson = new GsonBuilder().create();
                                TMDB p = gson.fromJson(reader, TMDB.class);
                                Result r = p.getResults().get(0);
                                lblTitle.setText(r.getTitle());
                                imgPoster.imageProperty().setValue(new Image(imagePath+r.getPoster_path()));
                                ratingTMDB.setText(r.getVote_average().toString());
                                descriptionText.setText(r.getOverview());
                                releaseDateTMDB.setText(r.getRelease_date());
                                backdropImage.imageProperty().setValue(new Image(imagePath+r.getBackdrop_path()));
                            }
                            conn.disconnect();
                        } catch (IOException e) {e.printStackTrace();}
                });
    }


    @FXML
    private void addZoomMI(ActionEvent actionEvent) {webView.setZoom(webView.getZoom() + 0.25);}

    @FXML
    private void subZoomMI(ActionEvent actionEvent) {webView.setZoom(webView.getZoom() - 0.25);}

    @FXML
    private void movieSearchMI(ActionEvent actionEvent) {engine.load("https://www.allmovie.com/search/all/" + movieInfoTextField.getText());}

    @FXML
    private void homeMI(ActionEvent actionEvent) {engine.load("https://www.allmovie.com");}

    @FXML
    private void refreshMI(ActionEvent actionEvent) {engine.reload();}

    @FXML
    private void forwardMI(ActionEvent actionEvent) {engine.getHistory().go(1);}

    @FXML
    private void backMI(ActionEvent actionEvent) {engine.getHistory().go(-1);}

    @FXML
    private void rateMovie(ActionEvent actionEvent) throws IOException, NullPointerException{
        Movie selectedMovie = movieTV.getSelectionModel().getSelectedItem();
        int selectedMovieID = selectedMovie.getId();
        float selectedMovieRating = selectedMovie.getRating();
        String selectedMovieTitle= selectedMovie.getName();

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/gui/view/RateMovieWindow.fxml"));
        Parent root = loader.load();
        RateMovieWindowController controller = loader.getController();
        controller.setModelRating(model);
        controller.setMovieIDRE(selectedMovieID);
        controller.setMovieRatingRE(selectedMovieRating);
        controller.setMovieTitleRE(selectedMovieTitle);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Rate Movie!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * opens a new window to add a new movie
     */
    public void openNewMovieWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/gui/view/NewMovieWindow.fxml"));
        Parent root = loader.load();
        NewMovieWindowController controller = loader.getController();
        controller.setModel(model);
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
        alert.setHeaderText("Do you really want to DELETE "+selectedMovie.getName()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) { //... user chose OK
            model.deleteMovie(selectedMovie);
            movieTV.getItems().remove(selectedMovie);
            alert.close();
        } else {
            alert.close();
        }
    }

    /**
     * Method to show a warning when the application is launched. It warns the user about unopened movies for 2 years
     * and with ratings lower than 6, and suggests to delete them
     */
    public void moviesToDelete() {
        ArrayList<String> moviesToDelete = new ArrayList<>();
        for (Movie m : movieTV.getItems()) {

            LocalDateTime localDateTime = LocalDateTime.now().minusYears(2); //2 years ago date
            Date dateTime = Date.valueOf(localDateTime.toLocalDate()); //converts to Date

            if (m.getLastView().before(dateTime) && m.getRating() < 6) {
                moviesToDelete.add(m.getName());
            }
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete movies warning");
        alert.setHeaderText("Remember to delete these unopened movies in the last 2 years and with low ratings:");
        alert.setContentText(String.valueOf(moviesToDelete));
        Optional<ButtonType> result = alert.showAndWait();
        alert.getContentText();
        if (result.get() == ButtonType.CLOSE) {
            alert.close();
        }

    }


    public void openManageCategoryWindow(ActionEvent actionEvent) throws IOException {
        Movie selectedMovie = movieTV.getSelectionModel().getSelectedItem();
        int selectedMovieID = selectedMovie.getId();
        String selectedMovieTitle= selectedMovie.getName();


        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/gui/view/ManageCategoryWindow.fxml"));
        Parent root = loader.load();
        ManageCategoryWindowController manageCategoryWindowController = loader.getController();
        manageCategoryWindowController.setModel(model);
        manageCategoryWindowController.setMovie(selectedMovie);
        manageCategoryWindowController.setMovieTitle(selectedMovieTitle);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Category Manager");
        stage.setScene(scene);
        stage.show();
    }
}