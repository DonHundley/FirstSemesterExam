package gui.controller;

import be.Movie;
import gui.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteConfirmationWindowController {
    public Button deleteConfirmButton;
    public Button deleteCancelButton;

    Model model = new Model();
    MainController mc = new MainController();

    /**
     * Method to confirm to delete a selected movie
     */

    public void deleteConfirm(ActionEvent actionEvent) {

        Movie selectedMovie = (Movie) mc.getMovieTV().getSelectionModel().getSelectedItem();
        model.deleteMovie(selectedMovie);
        Stage stage = (Stage) deleteConfirmButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Method to close the delete confirmation window when the "no" button is pressed
     */
    public void deleteCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) deleteCancelButton.getScene().getWindow();
        stage.close();
    }
}
