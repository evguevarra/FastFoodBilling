package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.MyListener;
import model.Menu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CashierMainController implements Initializable {

    @FXML
    private ImageView addOnsIconImage;

    @FXML
    private ImageView beverageIconImage;

    @FXML
    private ImageView dessertIconImage;

    @FXML
    private Label employeeName;

    @FXML
    private ImageView foodIconImage;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox orderContainer;

    @FXML
    private ScrollPane orderScroll;

    private List<Menu> menu = new ArrayList<>();
    private MyListener myListener;

    private List<Menu> getData(){
        List<Menu> menu = new ArrayList<>();
        Menu menuModel;



//        menuModel = new Menu();
//        menuModel.setName("Deluxe Burger");
//        menuModel.setPrice(120.00);
//        menuModel.setImageSrc("/img/deluxe-burger.jpg");
//        menu.add(menuModel);
//
//        menuModel = new Menu();
//        menuModel.setName("Cheese Burger");
//        menuModel.setPrice(50.00);
//        menuModel.setImageSrc("/img/cheese-burger.jpg");
//        menu.add(menuModel);
//
//        menuModel = new Menu();
//        menuModel.setName("Double Cheese Burger");
//        menuModel.setPrice(120.00);
//        menuModel.setImageSrc("/img/double-cheeseburger.jpg");
//        menu.add(menuModel);

        return menu;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int column = 0;
        int row = 1;

        orderScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");


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

//                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
//                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
//
//                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
//                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane,new Insets(15));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
