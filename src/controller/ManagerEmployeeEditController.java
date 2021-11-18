package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import repositories.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerEmployeeEditController implements Initializable {

    @FXML
    public TextField contactField;

    @FXML
    public TextField fnField;

    @FXML
    public TextField lnField;

    @FXML
    public ComboBox<String> positionCBox;

    public void updateDb(String eID){
        try {
            Connection connection = DatabaseConnection.connect();
            String sql = "UPDATE employee SET lastname ='"+lnField.getText()+"', firstname = '"+fnField.getText()+"',contactNumber='"+contactField.getText()+"', position = '"+positionCBox.getValue()+"' WHERE employeeID = '"+eID+"' ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Employee Information Updated!");
            alert.showAndWait();
            preparedStatement.close();

        } catch (SQLException error) {
            // TODO Auto-generated catch block
            error.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        positionCBox.setItems(FXCollections.observableArrayList("manager","cashier"));
    }
}
