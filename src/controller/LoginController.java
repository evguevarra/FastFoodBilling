package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
    void handleSignin(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(LoginController.class.getResource("/views/CashierMainUI.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Welcome to FastFood Billing System");
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.show();
        Stage previousStage = (Stage) exitBtn.getScene().getWindow();
        previousStage.close();


    }

}
