package gui.controller;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DatabaseConnector;
import gui.model.Model;
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
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button addMovieButton;
    public Button deleteMovieButton;
    public TableColumn<Movie, Integer> columnID;
    public TableColumn<Movie, String> columnTitle;
    public TableColumn<Movie, Float> columnRating;
    public TableColumn<Movie, String> columnFile;
    public TableColumn<Movie, Date> columnLastView;
    public TableColumn<Movie, Float> columnIMDBRating;
    public TableView<Movie> movieTV;





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



    /**
     * Method opens a confirmation window to confirm to delete the selected movie*/
    public void openDeleteConfirmationWindow(ActionEvent actionEvent) throws IOException {
        int selectedMovieID = movieTV.getSelectionModel().getSelectedItem().getId();
        System.out.print(selectedMovieID);
        Movie selectedMovie = (Movie) movieTV.getSelectionModel().getSelectedItem();

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Do you really want to DELETE the movie?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){ //... user chose OK
            model.deleteMovie(selectedMovie);
            movieTV.getItems().remove(selectedMovie);
            alert.close();
        } else {
            alert.close();
        }
    }



    public TableView getMovieTV() {
        return movieTV;
    }
}
