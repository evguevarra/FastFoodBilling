package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.MyListener;
import model.Menu;
import model.User;
import repositories.DatabaseConnection;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CashierMainController implements Initializable {

    private String currentTab = "Food";

    @FXML
    private Pane addBtn;

    @FXML
    private Pane addIndicator;

    @FXML
    private ImageView addOnsIconImage;

    @FXML
    private ImageView beverageIconImage;

    @FXML
    private Pane dessertIndicator;

    @FXML
    private Pane dessertBtn;

    @FXML
    private ImageView dessertIconImage;

    @FXML
    private Pane drinkBtn;

    @FXML
    private Pane drinkIndicator;

    @FXML
    private Label employeeName;

    @FXML
    private Pane foodBtn;

    @FXML
    private ImageView foodIconImage;

    @FXML
    private Pane foodIndicator;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox orderContainer;

    @FXML
    private ScrollPane orderScroll;

    private MyListener myListener;

    private ObservableList<Menu> menu = FXCollections.observableArrayList();

    @FXML
    void handleAddonBtn(MouseEvent event) {
        currentTab = "Add ons";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();
    }

    @FXML
    void handleDessertBtn(MouseEvent event) {
        currentTab = "Desserts";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();
    }

    @FXML
    void handleDrinkBtn(MouseEvent event) {
        currentTab = "Beverages";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();
    }

    @FXML
    void handleFoodBtn(MouseEvent event) {
        currentTab = "Food";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();
    }

    public void loadIndicator(){
        if(currentTab.equals("Food")){
            foodIndicator.setStyle("-fx-background-color: #FFA500; ");
            drinkIndicator.setStyle("-fx-background-color: transparent; ");
            dessertIndicator.setStyle("-fx-background-color: transparent; ");
            addIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("Beverages")){
            foodIndicator.setStyle("-fx-background-color: transparent; ");
            drinkIndicator.setStyle("-fx-background-color: #FFA500; ");
            dessertIndicator.setStyle("-fx-background-color: transparent; ");
            addIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("Desserts")){
            foodIndicator.setStyle("-fx-background-color: transparent; ");
            drinkIndicator.setStyle("-fx-background-color: transparent; ");
            dessertIndicator.setStyle("-fx-background-color: #FFA500; ");
            addIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("Add ons")){
            foodIndicator.setStyle("-fx-background-color: transparent; ");
            drinkIndicator.setStyle("-fx-background-color: transparent; ");
            dessertIndicator.setStyle("-fx-background-color: transparent; ");
            addIndicator.setStyle( "-fx-background-color: #FFA500; ");
        }
    }



    private ObservableList<Menu> getData(){
        menu.clear();
        ObservableList<Menu> menu = FXCollections.observableArrayList();
        Menu menuModel;


        try {
            Connection connection = DatabaseConnection.connect();
            String query = "SELECT menuName,menuPrice,menuCategory,menuImage  FROM menu WHERE menuCategory = '"+currentTab+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                menuModel = new Menu();
                menuModel.setName(resultSet.getString("menuName"));
                menuModel.setPrice(resultSet.getDouble("menuPrice"));

                InputStream input = resultSet.getBinaryStream("menuImage");
                if(input != null) {
                    InputStreamReader inputReader = new InputStreamReader(input);
                    if (inputReader.ready()) {
                        File tempFile = new File(menuModel.getName() + ".jpg");

                        FileOutputStream fos = new FileOutputStream(tempFile);
                        byte[] buffer = new byte[1024];
                        while (input.read(buffer) > 0) {
                            fos.write(buffer);
                        }
                        Image image = new Image(tempFile.toURI().toURL().toString());
                        menuModel.setImageSrc(image);
                    }
                }

                menu.add(menuModel);

            }

        } catch (SQLException | IOException e ) {
            e.printStackTrace();
        }

        return menu;
    }

    public void loadMenuData(){

        int column = 0;
        int row = 1;

        menu.addAll(getData());
        if(menu.size()>0){
            myListener = new MyListener() {
                @Override
                public void onClickListener(Menu menu) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/views/CashierOrderTabCard.fxml"));
                        AnchorPane aPane = fxmlLoader.load();

                        CashierOrderTabController orderController = fxmlLoader.getController();
                        orderController.setOrderItemName(menu.getName());

                        orderContainer.getChildren().add(aPane);
                        orderContainer.setSpacing(10);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        try {
            for (int i=0; i<menu.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/CashierMenuItemsCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CashierMenuItemsController itemController = fxmlLoader.getController();
                itemController.setData(menu.get(i),myListener);

                if(column == 3){
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane,column++,row);
                GridPane.setMargin(anchorPane,new Insets(15));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadIndicator();
        orderScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        loadMenuData();


    }
}
