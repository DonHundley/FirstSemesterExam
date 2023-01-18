package gui.controller;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageCategoryWindowController implements Initializable {

    private Model model;

    private int id;
    private String title;
    private Movie movie;

    @FXML
    private Label setMovieLabel;

    @FXML
    private Label assignCategorySuccess;

    @FXML
    private TextField addCategoryTextField;
    @FXML
    private ComboBox<Category> categoryComboBox;

    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * method to add a new category (user input)  to the combobox and database, checks that input is not empty and category doesn't already exist
     */
    public void addCategoryButton(ActionEvent actionEvent) throws SQLServerException {

        String list = categoryComboBox.getItems().toString();
        if (!(list.contains(addCategoryTextField.getText())) && !addCategoryTextField.getText().isEmpty()) {
            model.addCategory(addCategoryTextField.getText());
        }
        addCategoryTextField.clear();
    }

    /**
     * Setting the model, getting the categories from database and populating the combobox with them
     */
    public void setModel(Model model) {
        this.model = model;
        model.fetchCategoriesFromStorage();
        categoryComboBox.setItems(model.getCategories());

    }

    /**
     * Deleting selected category from database/combobox
     */
    public void deleteCategory(ActionEvent actionEvent) {
        Category category = categoryComboBox.getSelectionModel().getSelectedItem();
        model.deleteCategory(category);
    }

    /**
     * method to assign a category selected from the combobox to a movie selected in the main window
     */
    public void addCatToMovie(ActionEvent actionEvent) throws SQLException {

            model.addCatToMovie(categoryComboBox.getSelectionModel().getSelectedItem(), movie);
            assignCategorySuccess.setText("The category " + categoryComboBox.getSelectionModel().getSelectedItem().getName() + " was assigned to the movie " + movie.getName());
            categoryComboBox.setValue(null); //clear selection from combobox
    }

    /**
     * Setting movie title and showing the label
     */
    public void setMovieTitle(String selectedMovieTitle) {
        this.title = selectedMovieTitle;
        setMovieLabel();
    }

    /**
     * Setting the movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Setting label text
     */
    private void setMovieLabel() {
        setMovieLabel.setText("Assign a category to the movie: " + title + "!");
    }

}
