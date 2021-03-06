package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repositories.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    final BooleanProperty fTime = new SimpleBooleanProperty(true);

    private String empPosition;

    Connection connectDB = DatabaseConnection.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public static String currentUserID;

    @FXML
    private JFXRadioButton cashierRB;

    @FXML
    private Label exitBtn;

    @FXML
    private ImageView leftPanelImage;

    @FXML
    private Label loginMessage;

    @FXML
    private JFXRadioButton managerRB;

    @FXML
    private PasswordField passwordField;

    @FXML
    private JFXButton signinBtn;

    @FXML
    private TextField usernameField;

    ToggleGroup rdGroup = new ToggleGroup();

    @FXML
    void handleExit(MouseEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSignin(ActionEvent event) throws IOException {

        if(managerRB.isSelected()){
            empPosition = "manager";
        }else if(cashierRB.isSelected()){
            empPosition = "cashier";
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Employee Position Selected");
            alert.setContentText("Please select your position!");
            alert.showAndWait();
        }
        if(usernameField.getText().isBlank() == false && passwordField.getText().isBlank()== false) {
            loginMessage.setText("");
            authCheck();
        }else {
            loginMessage.setText("Please enter username and password!");
        }

    }

    public void authCheck(){

        try{
            String verifyLogin = "SELECT count(1) FROM employee WHERE employeeID = '" + usernameField.getText() + "' " +
                    "AND password ='" + passwordField.getText() + "' AND position ='" + empPosition + "'";
            preparedStatement = connectDB.prepareStatement(verifyLogin);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                if(resultSet.getInt(1) == 1){
                    loginMessage.setText("");
                    loginSuccess();
                    currentUserID = usernameField.getText();
                }else{
                    loginMessage.setText("Login Failed! Wrong username and password");
                }
            }
            preparedStatement.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                preparedStatement.close();
            }catch(Exception e) {

            }
        }
    }

    public void loginSuccess() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/LoginLoadingUI.fxml"));
        Parent root = fxmlLoader.load();

        LoginLoadingController loadingController = fxmlLoader.getController();
        loadingController.setEmployeePosition(empPosition);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.NONE);
        stage.show();
        Stage previousStage = (Stage) exitBtn.getScene().getWindow();
        previousStage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerRB.setToggleGroup(rdGroup);
        cashierRB.setToggleGroup(rdGroup);

        usernameField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue && fTime.get()){
                leftPanelImage.requestFocus();
                fTime.setValue(false);
            }
        });



    }
}
