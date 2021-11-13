package controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Menu;
import repositories.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {

    final BooleanProperty fTime = new SimpleBooleanProperty(true);

    String currentCategory = "";

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private TextField searchField;

    @FXML
    private JFXButton addItemBtn;

    @FXML
    private Label addOnsBtn;

    @FXML
    private Label beverageBtn;

    @FXML
    private Label dessertBtn;

    @FXML
    private Label foodBtn;

    @FXML
    private TableColumn<Menu, String> menuCategory;

    @FXML
    private TableColumn<Menu, Integer> menuId;

    @FXML
    private TableColumn<Menu, String> menuName;

    @FXML
    private TableColumn<Menu, Double> menuPrice;

    @FXML
    private TableView<Menu> menuTable;

    @FXML
    private VBox orderContainer;

    @FXML
    private JFXButton refreshBtn;

    ObservableList<Menu> observableList = FXCollections.observableArrayList();

    @FXML
    void handleAddItemBtn(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/ManagerMenuAdd.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add Menu Item");

            Optional<ButtonType> clickedBtn = dialog.showAndWait();
            if (clickedBtn.get() == ButtonType.OK){
                System.out.println("Added");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddons(MouseEvent event) {
        currentCategory = "Add ons";
        displayTableData();
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.ORANGE);
    }

    @FXML
    void handleBeverage(MouseEvent event) {
        currentCategory = "Beverages";
        displayTableData();
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.ORANGE);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);
    }

    @FXML
    void handleDessert(MouseEvent event) {
        currentCategory = "Desserts";
        displayTableData();
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.ORANGE);
        addOnsBtn.setTextFill(Color.GREY);
    }

    @FXML
    void handleFood(MouseEvent event) {
        currentCategory = "Food";
        displayTableData();
        foodBtn.setTextFill(Color.ORANGE);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);

    }

    @FXML
    void handleRefreshBtn(ActionEvent event) {

    }

    public void displayTableData(){
        menuTable.getItems().clear();

        try {
            Connection connection = DatabaseConnection.connect();
            String query = "SELECT menuID, menuName, menuPrice,menuCategory FROM menu WHERE menuCategory ='"+currentCategory+"' ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int mID = resultSet.getInt("menuID");
                String name = resultSet.getString("menuName");
                double price = Double.parseDouble(resultSet.getString("menuPrice"));
                String category = resultSet.getString("menuCategory");

                observableList.add(new Menu(mID,name,price,category));
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        menuId.setCellValueFactory(new PropertyValueFactory<>("id"));
        menuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        menuPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        menuCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        menuTable.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentCategory = "Food";
        foodBtn.setTextFill(Color.ORANGE);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);

        searchField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue && fTime.get()){
                mainContainer.requestFocus();
                fTime.setValue(false);
            }
        });

        displayTableData();

    }
}
