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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Menu;
import model.User;
import repositories.DatabaseConnection;

import java.io.IOException;
import java.io.InputStream;
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
    private JFXButton refreshBtn;

    @FXML
    private Label empIDLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private VBox empInfoContainer;

    @FXML
    private ImageView empImage;

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;


    ObservableList<User> observableList = FXCollections.observableArrayList();
    FilteredList<User> filteredList = new FilteredList<>(observableList, b ->true);

    public Dialog<ButtonType> dialog;

    @FXML
    void handleAddEmpBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/ManagerEmployeeAdd.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            ManagerEmployeeAddController employeeAddController = fxmlLoader.getController();


            dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add New Employee");

            Optional<ButtonType> clickedBtn = dialog.showAndWait();
            if (clickedBtn.get() == ButtonType.OK){
                employeeAddController.empCreation();
                dialog.close();
                displayTableData();
                empInfoContainer.setVisible(false);
            }if (clickedBtn.get() == ButtonType.CANCEL){

                employeeAddController.closeWebcam();
            }
//            ManagerEmployeeAddController employeeAddController = fxmlLoader.getController();
//            if

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
        empInfoContainer.setVisible(false);
    }

    @FXML
    void handleDeleteBtn(ActionEvent event) {
        System.out.println("Delete");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the account? Warning: Deletion cannot be undone!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.YES){
            try{
                connection = DatabaseConnection.connect();
                String sql = "DELETE FROM employee WHERE employeeID = '"+empIDLabel.getText()+"'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Item Deleted!");
                done.showAndWait();

                preparedStatement.close();
                displayTableData();
                empInfoContainer.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleEditBtn(ActionEvent event) {
        System.out.println("Edit");
    }

    @FXML
    void handleTableClick(MouseEvent event) {
        if(empTable.getSelectionModel().getSelectedItem() != null){
            empInfoContainer.setVisible(true);

            User empItem = empTable.getSelectionModel().getSelectedItem();
            empIDLabel.setText(String.valueOf(empItem.getId()));
            nameLabel.setText(empItem.getFirstname()+" "+ empItem.getLastname());
            positionLabel.setText(empItem.getPosition());

            try{
                connection = DatabaseConnection.connect();
                String query = "SELECT employeeImage  FROM employee WHERE employeeID = '"+empItem.getId()+"'  ";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    InputStream input = resultSet.getBinaryStream("employeeImage");

                    if(input != null && input.available() > 1){
                        Image img = new Image(input);
                        empImage.setImage(img);
                    }
                }
                preparedStatement.close();


            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }


        }
    }


    public void displayTableData(){
        observableList.clear();

        try {
            connection = DatabaseConnection.connect();
            String query = "SELECT employeeID, lastname, firstname, gender, birthdate, contactNumber, position  FROM employee ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

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
        empInfoContainer.setVisible(false);
        displayTableData();
    }
}
