package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repositories.DatabaseConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsAccountController implements Initializable {

    Connection connection = DatabaseConnection.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Label changeBtn;

    @FXML
    private Label fnameValue;

    @FXML
    private Label lnameValue;

    @FXML
    void handleChangePass(MouseEvent event) {
        Parent root = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChangePass.fxml"));
            root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Change Password");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void displayName(){
        try{
            String query = "SELECT * FROM employee WHERE employeeID = '"+LoginController.currentUserID+"' ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                fnameValue.setText(resultSet.getString("firstname"));
                lnameValue.setText(resultSet.getString("lastname"));
            }

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayName();
    }
}
