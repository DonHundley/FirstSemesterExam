package gui.controller;

import gui.model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class RateMovieWindowController implements Initializable {

    @FXML
    private TextField ratingTextField;
    @FXML
    private Button myRatingCancelButton, myRatingSaveButton;
    @FXML
    private Label errorLabel, rateMovieLabel;
    private Model model;
    private int id;
    private float rating;
    private String title;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filterTF();
    }

    /**
     * We fetch all the information needed from the Maincontrollers' instance of the model.
     * Using the ID of that movie we update the rating to the new user designated number.
     * If that number is less than 11 we return an error.
     */
    private void updateReview(){
        float ratingTFinput = Float.parseFloat(ratingTextField.getText());
            if (ratingTFinput < 11){
                rating = ratingTFinput;
                model.addUserReview(rating, id);
                model.loadMovieList();
                Stage stage = (Stage) myRatingSaveButton.getScene().getWindow();
                stage.close();
            } else {
                errorLabel.setText("Please choose a number 1-10");
            }

    }

    /**
     * Simple text filter for the user input to allow only numbers.
     * TO DO: Fix so that it will allow the user to input to allow a float. In the given state it only allows whole numbers.
     */
    private void filterTF(){
        ratingTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ratingTextField.setText(newValue.replaceAll("[\\D]", ""));
            }
        });
    }



    public void setMovieIDRE(int selectedMovieID) {this.id = selectedMovieID;}

    public void setMovieRatingRE(float selectedMovieRating) {this.rating = selectedMovieRating;}
    public void setModelRating(Model model) {this.model = model;}
    public void setMovieTitleRE(String selectedMovieTitle) {this.title = selectedMovieTitle;setRateMovieLabel();}

    private void setRateMovieLabel(){rateMovieLabel.setText("Rate " + title + "!");}

    @FXML
    private void saveMyRatingWindow(ActionEvent actionEvent) {
        try {
            updateReview();
        }
        catch (RuntimeException e){System.out.println("User did not input a value");}
    }
    @FXML
    private void closeMyRatingWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) myRatingCancelButton.getScene().getWindow();
        stage.close();
    }
    
}
