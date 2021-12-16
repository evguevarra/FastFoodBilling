package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsAboutController implements Initializable {

    @FXML
    private Label textInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textInfo.setText("Fast Food System is a software that helps in order taking \nand billing transactions." +
                "This software is developed for \nSoftware Engineering 2 course of the developers." +
                "\n\nDevelopers:\n\n" +
                "\tEdison Guevarra\n" +
                "\tJasmin Timban\n" +
                "\tCarlo Ciasico\n" +
                "\tRussel Gutierrez\n" +
                "\tJom Ramirez\n" +
                "\tReidrickx Yumang\n");
    }
}
