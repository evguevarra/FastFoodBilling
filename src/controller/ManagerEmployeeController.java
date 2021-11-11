package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Menu;
import model.User;
import repositories.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerEmployeeController implements Initializable {

    @FXML
    private JFXButton addEmployeeBtn;

    @FXML
    private TableColumn<User,String> empBirthdate;

    @FXML
    private TableColumn<User,String> empContact;

    @FXML
    private TableColumn<User,String> empFirstname;

    @FXML
    private TableColumn<User,String> empGender;

    @FXML
    private TableColumn<User, Integer> empID;

    @FXML
    private TableColumn<User,String> empLastname;

    @FXML
    private TableColumn<User,String> empPosition;

    @FXML
    private TableView<User> empTable;

    @FXML
    private VBox orderContainer;

    @FXML
    private JFXButton refreshBtn;

    ObservableList<User> observableList = FXCollections.observableArrayList();

    @FXML
    void handleAddEmpBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/ManagerEmployeeAdd.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add New Employee");

            Optional<ButtonType> clickedBtn = dialog.showAndWait();
            if (clickedBtn.get() == ButtonType.OK){
                System.out.println("Added");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayTableData(){


        try {
            Connection connection = DatabaseConnection.connect();
            String query = "SELECT employeeID, lastname, firstname, gender, birthdate, contactNumber, position  FROM employee ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int eID = resultSet.getInt("employeeID");
                String lName = resultSet.getString("lastname");
                String fName = resultSet.getString("firstname");
                String gender = resultSet.getString("gender");
                String birthdate = resultSet.getString("birthdate");
                String contactNumber = resultSet.getString("contactNumber");
                String position = resultSet.getString("position");

               observableList.add(new User(eID,fName,lName,gender,birthdate,contactNumber,position));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        empID.setCellValueFactory(new PropertyValueFactory<>("id"));
        empLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        empFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        empGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        empBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        empContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        empPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        empTable.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayTableData();
    }
}
