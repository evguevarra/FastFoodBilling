package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsMainController implements Initializable {

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
        loadUI("SettingsAbout.fxml");
    }

    @FXML
    void accountClicked(MouseEvent event) {
        aboutBtn.setTextFill(Color.GREY);
        accountSettingsBtn.setTextFill(Color.ORANGE);
        loadUI("SettingsAccount.fxml");
    }

    public void loadUI(String ui){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("/views/" + ui));
        }catch (IOException e){
            e.printStackTrace();
        }
        settingsPanel.getChildren().clear();
        settingsPanel.getChildren().setAll(node);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUI("SettingsAccount.fxml");
    }
}