package gui.controller;

import be.Movie;
import gui.model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewMovieWindowController {
    public TextField movieTitleTextfield;
    public ComboBox categoryCombobox;
    public Button addCategoryButton;
    public TextField ratingIMDBTextField;
    public Button newMovieSaveButton;
    public Button newMovieCancelButton;
    Model model = new Model();


    public void createNewCategory(ActionEvent actionEvent) {
    }


    /**
     * when save button is pressed, takes input values and uses them as parameters in the add movie method. First checks that rating is a valid number and title is not empty. other checks might be added*/
    public void newMovieSave(ActionEvent actionEvent) {
        if ((Integer.parseInt(ratingIMDBTextField.getText()) > 0 && Integer.parseInt(ratingIMDBTextField.getText()) <= 10) && (movieTitleTextfield.getText().length() > 0)) {
            //model.addMovie(movieTitleTextfield.getText(), Integer.parseInt(ratingIMDBTextField.getText()));
        }
        movieTitleTextfield.clear();
        categoryCombobox.cancelEdit();
        ratingIMDBTextField.clear();

        Stage stage = (Stage) newMovieSaveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * closes a window when cancel button is pressed
     */
    public void newMovieCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) newMovieCancelButton.getScene().getWindow();
        stage.close();
    }
}
