package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerEmployeeAddController implements Initializable {

    @FXML
    private DatePicker birdatePicker;

    @FXML
    private ComboBox<String> genderCBox;

    @FXML
    private ComboBox<String> positionCBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderCBox.setItems(FXCollections.observableArrayList("Male","Female"));
        positionCBox.setItems(FXCollections.observableArrayList("Manager","Cashier"));
    }
}
