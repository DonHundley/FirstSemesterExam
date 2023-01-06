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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button addMovieButton;
    @FXML
    private Button deleteMovieButton;
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
    private TextField filterTextField;

    private Model model = new Model();

    @Override
    public void initialize(URL url, ResourceBundle resource) {

        movieTV.setItems(model.getObsMovies());
        model.loadMovieList();


        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        columnFile.setCellValueFactory(new PropertyValueFactory<>("fileLink"));
        columnLastView.setCellValueFactory(new PropertyValueFactory<>("lastView"));
        columnIMDBRating.setCellValueFactory(new PropertyValueFactory<>("IMDBRating"));


        FilteredList<Movie> filteredData = new FilteredList<>(model.getObsMovies(), b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Movie -> {

                // If there is no search value then it will continue to display as normal.
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (Movie.getName().toLowerCase().contains(searchKeyword)) {
                    return true; //This means we found a matching title.
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
