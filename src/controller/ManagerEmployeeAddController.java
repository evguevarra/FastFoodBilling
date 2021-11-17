package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repositories.DatabaseConnection;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerEmployeeAddController implements Initializable {

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField contactField;

    @FXML
    private Label errorMessage;

    @FXML
    private JFXButton browseBtn;

    @FXML
    private JFXButton webcamBtn;

    @FXML
    private JFXButton createEmpBtn;

    @FXML
    private ImageView employeeImage;

    @FXML
    private ComboBox<String> genderCBox;

    @FXML
    private ComboBox<String> positionCBox;

    PreparedStatement preparedStatement = null;
    FileChooser fileChooser = new FileChooser();
    File file;
    FileInputStream fis;

    @FXML
    void handleBrowseButton(ActionEvent event) {
        fileChooser.setTitle("Image Chooser");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));

        file = fileChooser.showOpenDialog(null);

        if(file != null){
            employeeImage.setImage(new Image(file.toURI().toString()));
        }else{
            System.out.println("A file is invalid");
        }
    }

    @FXML
    void handleCameraBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/Webcam.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Take Picture");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleEmpCreation(ActionEvent event) {
        if(fnameField.getText().isEmpty() || lnameField.getText().isEmpty() || genderCBox.getValue().isEmpty()|| birthdatePicker.getValue().toString().isEmpty()
         || contactField.getText().isEmpty() || positionCBox.getValue().isEmpty()){
            errorMessage.setText("Please Fillup all Fields");
        }else{
            errorMessage.setText("");
            addToDB();
        }
    }


    public void addToDB(){
        try {
            Connection connection = DatabaseConnection.connect();
            String query = "INSERT INTO employee(lastname,firstname,gender,birthdate,contactNumber,position,employeeImage,password) VALUES(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lnameField.getText());
            preparedStatement.setString(2, fnameField.getText());
            preparedStatement.setString(3,genderCBox.getValue());
            preparedStatement.setString(4,birthdatePicker.getValue().toString());
            preparedStatement.setString(5, contactField.getText());
            preparedStatement.setString(6,positionCBox.getValue());

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

            preparedStatement.setBytes(7, byteImage);
            preparedStatement.setString(8, fnameField.getText().replace(" ","").toLowerCase(Locale.ROOT));
            preparedStatement.executeUpdate();

            try{
                String sql = "SELECT * FROM employee ORDER BY employeeID DESC LIMIT 1 ";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while(rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Employee Login Details");
                    alert.setContentText("Employee Account Created!\n Here are the login details\n"+"username: "+ rs.getString("employeeID")+"\npassword: "+rs.getString("password")+"\n(This is just a default password, Don't forget to change it)");
                    alert.showAndWait();
//                    JOptionPane.showMessageDialog(null,"Employee Account Created!\n Here are the login details\n"+"username: "+ rs.getString("employeeID")+"\npassword: "+rs.getString("password")+"\n(This is just a default password, Don't forget to change it)");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderCBox.setItems(FXCollections.observableArrayList("male","female"));
        positionCBox.setItems(FXCollections.observableArrayList("manager","cashier"));
    }
}
