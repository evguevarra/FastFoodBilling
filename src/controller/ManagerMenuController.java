package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {

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
    private TableColumn<?, ?> menuCategory;

    @FXML
    private TableColumn<?, ?> menuId;

    @FXML
    private TableColumn<?, ?> menuName;

    @FXML
    private TableColumn<?, ?> menuPrice;

    @FXML
    private TableView<?> menuTable;

    @FXML
    private VBox orderContainer;

    @FXML
    private JFXButton refreshBtn;

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
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.ORANGE);
    }

    @FXML
    void handleBeverage(MouseEvent event) {
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.ORANGE);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);
    }

    @FXML
    void handleDessert(MouseEvent event) {
        foodBtn.setTextFill(Color.GREY);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.ORANGE);
        addOnsBtn.setTextFill(Color.GREY);
    }

    @FXML
    void handleFood(MouseEvent event) {
        foodBtn.setTextFill(Color.ORANGE);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);

    }

    @FXML
    void handleRefreshBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodBtn.setTextFill(Color.ORANGE);
        beverageBtn.setTextFill(Color.GREY);
        dessertBtn.setTextFill(Color.GREY);
        addOnsBtn.setTextFill(Color.GREY);
    }
}
