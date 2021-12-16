package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import repositories.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePassController implements Initializable {

    Connection connection = DatabaseConnection.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    String password = null;

    @FXML
    private PasswordField confirmNewField;

    @FXML
    private PasswordField currentPassField;

    @FXML
    private PasswordField newPassField;

    @FXML
    void handleCancelBtn(MouseEvent event) {
        Stage stage = (Stage) confirmNewField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSaveBtn(MouseEvent event) {

        System.out.println(currentPassField.getText());
        if(password.equals(currentPassField.getText()) || newPassField.getText().equals(confirmNewField.getText()) ){
                try {
                    String sql = "UPDATE employee SET password ='" + newPassField.getText() + "' WHERE employeeID = '"+LoginController.currentUserID+"' ";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee Information Updated!");
                    alert.showAndWait();
                    preparedStatement.close();

                    Stage stage = (Stage) confirmNewField.getScene().getWindow();
                    stage.close();

                } catch (SQLException error) {
                    // TODO Auto-generated catch block
                    error.printStackTrace();
                }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please make sure that current password is correct or new password and confirm password matches");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            String query = "SELECT password FROM employee WHERE employeeID = '"+LoginController.currentUserID+"' ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                password = resultSet.getString("password");

            }

            preparedStatement.close();




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
