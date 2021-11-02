package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private JFXRadioButton cashierRB;

    @FXML
    private Label exitBtn;

    @FXML
    private ImageView leftPanelImage;

    @FXML
    private JFXRadioButton managerRB;

    @FXML
    private PasswordField passwordField;

    @FXML
    private JFXButton signinBtn;

    @FXML
    private TextField usernameField;

    @FXML
    void handleExit(MouseEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSignin(ActionEvent event) {

    }

}
