package gui.controller;

import be.Category;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ManageCategoryWindowController implements Initializable {

    private Model model;
    @FXML
    private TextField addCategoryTextField;
    @FXML
    private ComboBox categoryComboBox;

    private ArrayList<String> categoryList = new ArrayList<>();

    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        model.getAllCategories();
        addCategoryToComboBox();
    }


    public void addCategoryButton(ActionEvent actionEvent) throws SQLServerException {
        String value = addCategoryTextField.getText();

        if (/*!(c.contains(value.toUpperCase())) && */ value.length() > 0) {
            model.addCategory(addCategoryTextField.getText());
            categoryComboBox.getItems().add(value);
        }
        addCategoryTextField.clear();
    }


    public void addCategoryToComboBox() {
        for (Category c : model.getCategories()) {
            categoryList.add(c.getName());
        }
        categoryComboBox.getItems().addAll(categoryList);
    }


    public void setModel(Model model) {
        this.model = model;
    }
}
