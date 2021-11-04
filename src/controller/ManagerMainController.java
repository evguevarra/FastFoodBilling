package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ManagerMainController {

    private String currentTab;

    @FXML
    private Pane employeeBtn;

    @FXML
    private ImageView employeeIconImage;

    @FXML
    private Pane employeeIndicator;

    @FXML
    private Label employeeName;

    @FXML
    private ImageView foodIconImage;

    @FXML
    private Pane menuBtn;

    @FXML
    private Pane menuIndicator;

    @FXML
    private Pane reportBtn;

    @FXML
    private ImageView reportIconImage;

    @FXML
    private Pane reportIndicator;
    @FXML
    void handleEmployeeBtn(MouseEvent event) {
        currentTab = "employee";
        System.out.println(currentTab);

    }

    @FXML
    void handleMenuBtn(MouseEvent event) {
        currentTab = "menu";
//        Image image = new Image(getClass().getResourceAsStream("/img/restaurant-highlight.png"));
//        foodIconImage.setImage(image);
        System.out.println(currentTab);
    }

    @FXML
    void handleReportsBtn(MouseEvent event) {
        currentTab = "report";
//        Image image = new Image(getClass().getResourceAsStream("/img/restaurant-highlight.png"));
//        foodIconImage.setImage(image);
        System.out.println(currentTab);
    }

}
