package controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    final BooleanProperty fTime = new SimpleBooleanProperty(true);

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private TextField searchField;

    @FXML
    private FontAwesomeIconView clearText;

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
    FilteredList<User> filteredList = new FilteredList<>(observableList, b ->true);

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

    @FXML
    void handleClearTextBtn(MouseEvent event) {
        searchField.clear();
        mainContainer.requestFocus();
    }

    @FXML
    void handleRefreshBtn(ActionEvent event) {
        displayTableData();
    }

    public void displayTableData(){
        observableList.clear();

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
            preparedStatement.close();

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

        searchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredList.setPredicate(userModel -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if(userModel.getFirstname().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(userModel.getLastname().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(userModel.getGender().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(String.valueOf(userModel.getId()).indexOf(searchKeyword) > -1){
                    return true;
                }else if(userModel.getPosition().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });

        SortedList<User> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(empTable.comparatorProperty());

        empTable.setItems(sortedList);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue && fTime.get()){
                mainContainer.requestFocus();
                fTime.setValue(false);
            }
        });

        displayTableData();
    }
}
