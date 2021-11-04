package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.MyListener;
import model.Menu;


import java.awt.desktop.AppReopenedListener;
import java.awt.event.MouseEvent;

public class CashierMenuItemsController {

    @FXML
    private AnchorPane addToOrderBtn;

    @FXML
    private Label menuItemName;

    @FXML
    private ImageView menuImage;

    @FXML
    private Label menuItemPrice;

//    @FXML
//    private void addClick(MouseEvent mouseEvent){
//
//    }

//    @FXML
//    void addClick(MouseEvent event) {
//
//    }

    private Menu menu;
    private MyListener myListener;

    public void setData(Menu menu, MyListener myListener){
        this.menu = menu;
        this.myListener = myListener;
        menuItemName.setText(menu.getName());
        menuItemPrice.setText("₱"+menu.getPrice());
        Image image = new Image(getClass().getResourceAsStream(menu.getImageSrc()));
        menuImage.setImage(image);

        Rectangle clip = new Rectangle();
        clip.setWidth(200.0f);
        clip.setHeight(200.0f);

        clip.setArcHeight(20);
        clip.setArcWidth(20);
        clip.setStroke(Color.BLACK);
        menuImage.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage wImage = menuImage.snapshot(parameters, null);

        menuImage.setClip(null);
        menuImage.setEffect(new DropShadow(10, Color.BLACK));
        menuImage.setImage(wImage);


    }

    public void addClick(javafx.scene.input.MouseEvent mouseEvent) {
        myListener.onClickListener(menu);
    }
}
