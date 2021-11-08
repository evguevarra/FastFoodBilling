package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMenuAddController implements Initializable {

    @FXML
    private ComboBox<String> categoryCBox;

    //private String[] category = {"Food","Beverage","Dessert","Add-on"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryCBox.setItems(FXCollections.observableArrayList("Food","Beverage","Dessert","Add-on"));

        //categoryCBox.getItems().addAll();
    }
}
