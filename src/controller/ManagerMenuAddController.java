package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMenuAddController implements Initializable {

    @FXML
    private ComboBox<String> categoryCBox;

    @FXML
    private JFXButton browseBtn;

    @FXML
    private ImageView menuImage;

    FileChooser fileChooser = new FileChooser();

    @FXML
    void handleBrowseBtn(ActionEvent event) {
        fileChooser.setTitle("Image Chooser");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));

        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            menuImage.setImage(new Image(file.toURI().toString()));
        }else{
            System.out.println("A file is invalid");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryCBox.setItems(FXCollections.observableArrayList("Food","Beverage","Dessert","Add-on"));


    }
}
