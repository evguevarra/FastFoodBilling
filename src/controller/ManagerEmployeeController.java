package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class ManagerEmployeeController {

    @FXML
    private JFXButton addEmployeeBtn;

    @FXML
    private TableView<?> empTable;

    @FXML
    private TableColumn<?, ?> menuCategory;

    @FXML
    private TableColumn<?, ?> menuId;

    @FXML
    private TableColumn<?, ?> menuName;

    @FXML
    private TableColumn<?, ?> menuPrice;

    @FXML
    private VBox orderContainer;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    void handleAddEmpBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/ManagerEmployeeAdd.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add New Employee");

            Optional<ButtonType> clickedBtn = dialog.showAndWait();
            if (clickedBtn.get() == ButtonType.OK){
                System.out.println("Added");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
