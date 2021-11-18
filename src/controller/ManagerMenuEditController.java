package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import repositories.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManagerMenuEditController {

    @FXML
    public TextField nameField;

    @FXML
    public TextField priceField;


    public void updateDb(String mID){
        try {
            Connection connection = DatabaseConnection.connect();
            String sql = "UPDATE menu SET menuName ='"+nameField.getText()+"' ,menuPrice='"+priceField.getText()+"' WHERE menuID = '"+mID+"' ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Menu Updated!");
            alert.showAndWait();
            preparedStatement.close();

        } catch (SQLException error) {
            // TODO Auto-generated catch block
            error.printStackTrace();
        }
    }

}
