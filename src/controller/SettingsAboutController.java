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
        textInfo.setText("Fast Food System is a software that helps in order taking \n and billing transactions." +
                "\n\n\tDevelopers:\n" +
                "\tEdison Guevarra\n" +
                "\tJasmin Timban\n" +
                "\tCarlo Ciasico\n" +
                "\tRussel Guiterrez\n" +
                "\tJom Ramirez\n" +
                "\tReidrickx Yumang\n");
    }
}
