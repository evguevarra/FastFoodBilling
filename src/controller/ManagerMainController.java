package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMainController implements Initializable {

    private String currentTab = "menu";



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
    private AnchorPane uiPane;

    @FXML
    void handleEmployeeBtn(MouseEvent event) {
        currentTab = "employee";
        loadIndicator();
        loadUI("ManagerEmployeeUI.fxml");
    }

    @FXML
    void handleMenuBtn(MouseEvent event) {
        currentTab = "menu";
//        Image image = new Image(getClass().getResourceAsStream("/img/restaurant-highlight.png"));
//        foodIconImage.setImage(image);
        loadIndicator();
        loadUI("ManagerMenuUi.fxml");
    }

    @FXML
    void handleReportsBtn(MouseEvent event) {
        currentTab = "report";
//        Image image = new Image(getClass().getResourceAsStream("/img/restaurant-highlight.png"));
//        foodIconImage.setImage(image);
        loadIndicator();
        loadUI("ManagerReportsUI.fxml");
    }

    public void loadUI(String ui){
        //Parent root = null;
        Node node = null;
        try{
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("/views/"+ui));
//            AnchorPane aPane = fxmlLoader.load();
            //uiPane.getChildren().add(aPane);
            node = FXMLLoader.load(getClass().getResource("/views/" + ui));
        }catch (IOException e){
            e.printStackTrace();
        }
        uiPane.getChildren().clear();
        uiPane.getChildren().setAll(node);
    }

    public void loadIndicator(){
        if(currentTab.equals("menu")){
            menuIndicator.setStyle("-fx-background-color: #FFA500; ");
            reportIndicator.setStyle("-fx-background-color: transparent; ");
            employeeIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("report")){
            menuIndicator.setStyle("-fx-background-color: transparent; ");
            reportIndicator.setStyle("-fx-background-color: #FFA500; ");
            employeeIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("employee")){
            menuIndicator.setStyle("-fx-background-color: transparent; ");
            reportIndicator.setStyle("-fx-background-color: transparent; ");
            employeeIndicator.setStyle("-fx-background-color: #FFA500; ");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadIndicator();
    }
}
