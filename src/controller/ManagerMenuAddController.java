package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Menu;
import repositories.DatabaseConnection;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerMenuAddController implements Initializable {


    @FXML
    private DialogPane dialog;

    @FXML
    private ComboBox<String> categoryCBox;

    @FXML
    private JFXButton browseBtn;

    @FXML
    private TextField menuNameField;

    @FXML
    public Label errorMessage;

    @FXML
    private TextField priceField;

    @FXML
    private ImageView menuImage;

    @FXML
    private Button addBtn;

    PreparedStatement preparedStatement = null;

    FileChooser fileChooser = new FileChooser();
    File file;
    FileInputStream fis;

    @FXML
    void handleBrowseBtn(ActionEvent event) {
        fileChooser.setTitle("Image Chooser");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));

        file = fileChooser.showOpenDialog(null);


        if(file != null){
            menuImage.setImage(new Image(file.toURI().toString()));
        }else{
            System.out.println("A file is invalid");
        }
    }


    @FXML
    void handleAddBtn(ActionEvent event) {
        if(menuNameField.getText().isEmpty() || priceField.getText().isEmpty() || categoryCBox.getValue().isEmpty()){
            errorMessage.setText("Please Fillup all Fields");
        }else{
            errorMessage.setText("");
            addToDB();
        }

    }

    public void addToDB(){
        try {
            Connection connection = DatabaseConnection.connect();
            String query = "INSERT INTO menu(menuName,menuPrice,menuCategory,menuImage) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, menuNameField.getText());
            preparedStatement.setString(2, priceField.getText());
            preparedStatement.setString(3,categoryCBox.getValue());

            byte[] byteImage = null;
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            try{
                for(int readNum; (readNum = fis.read(buf)) != -1;){
                    bos.write(buf,0,readNum);
                    System.out.println("read"+ readNum + "bytes,");
                }
            }catch (IOException exception){
                exception.printStackTrace();
            }
            byteImage = bos.toByteArray();

            preparedStatement.setBytes(4, byteImage);
            preparedStatement.executeUpdate();


            preparedStatement.close();

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void printAdd(){
        System.out.println(menuNameField.getText());
        System.out.println(priceField.getText());
        System.out.println(categoryCBox.getValue());
        System.out.println(file.toURI().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryCBox.setItems(FXCollections.observableArrayList("Food","Beverages","Desserts","Add ons"));


    }
}
