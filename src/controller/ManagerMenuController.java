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
import javafx.scene.paint.Color;
import model.Menu;
import repositories.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
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
    private FontAwesomeIconView clearText;

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

    FilteredList<Menu> filteredList = new FilteredList<>(observableList, b ->true);

    @FXML
    void handleAddItemBtn(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/ManagerMenuAdd.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            ManagerMenuAddController menuAddController = fxmlLoader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add Menu Item");

//            Optional<ButtonType> clickedBtn =
            dialog.showAndWait();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddons(MouseEvent event) {
        currentCategory = "Add ons";
        clear();
        displayTableData();
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.ORANGE);
    }

    @FXML
    void handleBeverage(MouseEvent event) {
        currentCategory = "Beverages";
        clear();
        displayTableData();
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.ORANGE);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);
    }

    @FXML
    void handleDessert(MouseEvent event) {
        currentCategory = "Desserts";
        clear();
        displayTableData();
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.ORANGE);
        addOnsBtn.setTextFill(Color.GREY);
    }

    @FXML
    void handleFood(MouseEvent event) {
        currentCategory = "Food";
        clear();
        displayTableData();
        foodBtn.setTextFill(Color.ORANGE);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);

    }

    @FXML
    void handleRefreshBtn(ActionEvent event) {
        displayTableData();
    }

    @FXML
    void handleClearTextBtn(MouseEvent event) {
        clear();
    }


    @FXML
    void handleSearch(KeyEvent event) {
        searchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredList.setPredicate(menuModel -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if(menuModel.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(String.valueOf(menuModel.getId()).indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });

        SortedList<Menu> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(menuTable.comparatorProperty());

        menuTable.setItems(sortedList);
    }

    public void displayTableData(){
        observableList.clear();
        //menuTable.getItems().clear();

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
    public void clear(){
        searchField.clear();
        mainContainer.requestFocus();
    }

    private void removeSearchFocus() {

        searchField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(newValue && fTime.get()){
                mainContainer.requestFocus();
                fTime.setValue(false);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentCategory = "Food";
        foodBtn.setTextFill(Color.ORANGE);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);

        removeSearchFocus();

        displayTableData();

    }
}
