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
import javafx.scene.paint.Color;
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
    private Label menuIDLabel;

    @FXML
    private ImageView menuImage;

    @FXML
    private VBox menuInfoContainer;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;


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

            Optional<ButtonType> clickedBtn = dialog.showAndWait();
            if (clickedBtn.get() == ButtonType.OK){
                menuAddController.addToDB();
                dialog.close();
                displayTableData();
                menuInfoContainer.setVisible(false);
            }



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
        menuInfoContainer.setVisible(false);
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

    @FXML
    void handleDeleteBtn(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the item? Warning: Deletion cannot be undone!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.YES){
            try{
                connection = DatabaseConnection.connect();
                String sql = "DELETE FROM menu WHERE menuID = '"+menuIDLabel.getText()+"'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Item Deleted!");
                done.showAndWait();

                preparedStatement.close();
                displayTableData();
                menuInfoContainer.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleEditBtn(ActionEvent event) {
        System.out.println("Edit");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/ManagerMenuEdit.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            ManagerMenuEditController menuEditController = fxmlLoader.getController();
            menuEditController.nameField.setText(nameLabel.getText());
            menuEditController.priceField.setText(priceLabel.getText());
            //menuEditController.mID = menuIDLabel.getText();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Edit Menu Item");


            Optional<ButtonType> clickedBtn = dialog.showAndWait();
            if (clickedBtn.get() == ButtonType.OK){

                Alert editConfirmation = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to save changes?",ButtonType.YES,ButtonType.NO);
                editConfirmation.showAndWait();
                if(editConfirmation.getResult()==ButtonType.YES){
                    menuEditController.updateDb(menuIDLabel.getText());
                    dialog.close();
                    displayTableData();
                    menuInfoContainer.setVisible(false);
                }else if(editConfirmation.getResult()==ButtonType.NO){
                    Alert success = new Alert(Alert.AlertType.INFORMATION,"Update Forfeited!");
                    success.showAndWait();
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleTableClick(MouseEvent event) {
        if(menuTable.getSelectionModel().getSelectedItem() != null){
            menuInfoContainer.setVisible(true);

            Menu menuItem = menuTable.getSelectionModel().getSelectedItem();
            menuIDLabel.setText(String.valueOf(menuItem.getId()));
            nameLabel.setText(menuItem.getName());
            priceLabel.setText(String.valueOf(menuItem.getPrice()));

            try{
                connection = DatabaseConnection.connect();
                String query = "SELECT menuImage  FROM menu WHERE menuID = '"+menuItem.getId()+"'  ";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    InputStream input = resultSet.getBinaryStream("menuImage");

                    if(input != null && input.available() > 1){
                        Image img = new Image(input);
                        menuImage.setImage(img);
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
        //menuTable.getItems().clear();

        try {
            connection = DatabaseConnection.connect();
            String query = "SELECT menuID, menuName, menuPrice,menuCategory FROM menu WHERE menuCategory ='"+currentCategory+"' ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

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
        menuInfoContainer.setVisible(false);
        displayTableData();

    }
}
