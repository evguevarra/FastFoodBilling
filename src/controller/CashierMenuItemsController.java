package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Menu;

import java.awt.desktop.AppReopenedListener;

public class CashierMenuItemsController {

    @FXML
    private AnchorPane addToOrderBtn;

    @FXML
    private Label menuItemName;

    @FXML
    private ImageView menuImage;

    @FXML
    private Label menuItemPrice;

    private Menu menu;

    public void setData(Menu menu){
        this.menu = menu;
        menuItemName.setText(menu.getName());
        menuItemPrice.setText("₱"+menu.getPrice());
        Image image = new Image(getClass().getResourceAsStream(menu.getImageSrc()));
        menuImage.setImage(image);

    }

}
