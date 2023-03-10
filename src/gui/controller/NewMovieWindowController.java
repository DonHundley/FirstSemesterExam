package gui.controller;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NewMovieWindowController implements Initializable {
    @FXML
    private TextField movieTitleTextfield, myRatingTextField, fileInput, categoryTextField, ratingIMDBTextField;

    @FXML
    private Button newMovieSaveButton;
    @FXML
    private Button newMovieCancelButton;

    private String selectedFile;

    static long millis = System.currentTimeMillis();
    static java.sql.Date date = new java.sql.Date(millis);

    private Model model;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newMovieSaveButton.setDisable(true);
    }

    /**
     * Method to open file explorer and choose a movie file
     */
    public void chooseFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        setFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(new Stage());

        Path movefrom = FileSystems.getDefault().getPath(file.getPath());
        Path target = FileSystems.getDefault().getPath("moviesLocalFolder/"+file.getName());
        try{Files.move(movefrom,target, StandardCopyOption.ATOMIC_MOVE); newMovieSaveButton.setDisable(false);}catch (IOException e){}

        selectedFile = target.toString();
        fileInput.setText(file.getName());
    }

    /**
     * Mehod to configure the file chooser and select which file type are accepted
     */
    private static void setFileChooser(FileChooser fileChooser) {

        fileChooser.setTitle("Select movie");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("files", "*.mp4", "*.mpeg4")
        );
    }

    /**
     * when save button is pressed, takes input values and uses them as parameters in the add movie method. First checks that rating is a valid number and title is not empty. other checks might be added
     */
    public void newMovieSave(ActionEvent actionEvent) throws SQLServerException {
        if ((Float.parseFloat(ratingIMDBTextField.getText()) >= 0 && Float.parseFloat(ratingIMDBTextField.getText()) <= 10) && ((Float.parseFloat(myRatingTextField.getText()) >= 0 && Float.parseFloat(myRatingTextField.getText()) <= 10)) && (movieTitleTextfield.getText().length() > 0) && (selectedFile != null)) {
            Movie m = new Movie(movieTitleTextfield.getText(), Float.parseFloat(myRatingTextField.getText()), selectedFile, date, Float.parseFloat(ratingIMDBTextField.getText()));
            model.addMovie(m);
            model.loadMovieList();

        }

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


    public void setModel(Model model) {
        this.model = model;
    }

}
