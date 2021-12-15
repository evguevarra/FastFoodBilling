package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class SettingsMainController {

    @FXML
    private Label aboutBtn;

    @FXML
    private Label accountSettingsBtn;

    @FXML
    private AnchorPane settingsPanel;

    @FXML
    void aboutClicked(MouseEvent event) {
        aboutBtn.setTextFill(Color.ORANGE);
        accountSettingsBtn.setTextFill(Color.GREY);
    }

    @FXML
    void accountClicked(MouseEvent event) {
        aboutBtn.setTextFill(Color.GREY);
        accountSettingsBtn.setTextFill(Color.ORANGE);
    }

}