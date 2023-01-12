package gui.controller;

import be.Category;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageCategoryWindowController implements Initializable {

    private Model model;
    @FXML
    private TextField addCategoryTextField;
    @FXML
    private ComboBox<Category> categoryComboBox;

    public void initialize(URL location, ResourceBundle resources) {
    }


    public void addCategoryButton(ActionEvent actionEvent) throws SQLServerException {

        if (/*!(c.contains(value.toUpperCase())) && */ !addCategoryTextField.getText().isEmpty()) {
            model.addCategory(addCategoryTextField.getText());
        }
        addCategoryTextField.clear();
    }

    public void setModel(Model model) {
        this.model = model;
        model.fetchCategoriesFromStorage();
        categoryComboBox.setItems(model.getCategories());

    }

    public void deleteCategory(ActionEvent actionEvent) {
        Category category = categoryComboBox.getSelectionModel().getSelectedItem();
        model.deleteCategory(category);
    }
}
