package gui.controller;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button addMovieButton;
    public Button deleteMovieButton;
    @FXML
    private TableView<Movie> movieViewTable;
    @FXML
    private TableColumn<Movie, Integer> movieIDColumn;
    @FXML
    private TableColumn<Movie, String> movieTitleColumn;
    @FXML
    private TableColumn<Movie, String> categoryColumn;
    @FXML
    private TableColumn<Movie, Integer> ratingIMDBColumn;
    @FXML
    private TableColumn<Movie, Integer> myRatingColumn;
    @FXML
    private TableColumn<Movie, Integer> lastViewColumn;
    @FXML
    private TextField filterTextField;

    ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resource) {

        /**
         // SQL Query - Executed in the database.
         String moviesViewQuery = "SELECT MovieID, MovieTitle, MovieCategory, ratingIMDB, MyRating, LastView FROM MOVIE";
         DatabaseConnector connectNow = new DatabaseConnector();

         try {
         Connection connectDB = connectNow.getConnection();

         Statement statement = connectDB.createStatement();
         ResultSet queryOutput = statement.executeQuery(moviesViewQuery);

         while (queryOutput.next()){
         Integer queryMovieID = queryOutput.getInt("MovieID");
         String queryMovieTitle = queryOutput.getString("MovieTitle");
         String queryMovieCategory = queryOutput.getString("Category");
         Integer queryratingIMDB = queryOutput.getInt("RatingIMDB");
         Integer queryMyRating = queryOutput.getInt("myRating");
         Integer queryLastView = queryOutput.getInt("LastView");

         // This populates the ObservableList from the server.
         movieObservableList.add(new Movie(queryMovieID, queryMovieTitle, queryMovieCategory, queryratingIMDB, queryMyRating, queryLastView));
         }

         // PropertyValueFactory corresponds to the new movie table fields.
         // The table colum is annotated above.
         movieIDColumn.setCellValueFactory(new PropertyValueFactory<>("movieID"));
         movieTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
         categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
         ratingIMDBColumn.setCellValueFactory(new PropertyValueFactory<>("ratingIMDB"));
         myRatingColumn.setCellValueFactory(new PropertyValueFactory<>("myRating"));
         lastViewColumn.setCellValueFactory(new PropertyValueFactory<>("lastView"));

         movieViewTable.setItems(movieObservableList);


         } catch (SQLException e) {
         throw new RuntimeException(e);
         }
         **/


    }

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

    public void openConfirmWindow(ActionEvent actionEvent) {
    }
}
