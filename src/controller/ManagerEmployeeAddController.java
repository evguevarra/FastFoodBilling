package controller;

import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import repositories.DatabaseConnection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerEmployeeAddController implements Initializable {

    public static boolean isDone = false;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    public TextField fnameField;

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
    private Icons525View captureBtn;

    @FXML
    private FontAwesomeIconView resetBtn;

    @FXML
    public ImageView employeeImage;

    @FXML
    private StackPane imageContainer;

    @FXML
    private ComboBox<String> genderCBox;

    @FXML
    private ComboBox<String> positionCBox;

    PreparedStatement preparedStatement = null;
    FileChooser fileChooser = new FileChooser();
    public File file;
    FileInputStream fis;

    public static Webcam webcam;
    public static boolean isCapture = false;
    private String path;

    @FXML
    void handleBrowseButton(ActionEvent event) {
        isCapture = false;

        fileChooser.setTitle("Image Chooser");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));

        file = fileChooser.showOpenDialog(null);

        if(file != null){
            employeeImage.setImage(new Image(file.toURI().toString()));
            webcam.close();
        }else{
            System.out.println("A file is invalid");
        }
    }

    @FXML
    void handleCameraBtn(ActionEvent event) {
        employeeImage.imageProperty().set(null);
        employeeImage.fitWidthProperty().bind(imageContainer.widthProperty());
        employeeImage.fitHeightProperty().bind(imageContainer.heightProperty());

        webcam = Webcam.getDefault();

        if(webcam == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "No Camera Found!");
            errorAlert.showAndWait();
            //System.exit(-1);
        }else{
            if(webcam.isOpen()){
                webcam.close();
                closeWebcam();
            }else{
                webcam.setViewSize(new Dimension(640, 480));
                webcam.open();
                new VideoTacker().start();
            }
        }



        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images (*.png)", "*.png"));
        fileChooser.setTitle("Save Image");
    }

    @FXML
    void handleCapture(MouseEvent event) {
        isCapture = true;

        file = fileChooser.showSaveDialog(employeeImage.getScene().getWindow());
        if (file != null)
            try { // Save picture with .png extension
                ImageIO.write(SwingFXUtils.fromFXImage(employeeImage.getImage(), null), "PNG", file);
            } catch (IOException ex) {
                ex.printStackTrace(); // Can't save picture
            }

    }

    @FXML
    void handleReset(MouseEvent event) {
        isCapture = false;
        new VideoTacker().start();
    }


    public void empCreation(){
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

    public void closeWebcam(){
        System.out.println("closed");
        isCapture = false;
        employeeImage.setImage(null);
        if(webcam != null){
            webcam.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderCBox.setItems(FXCollections.observableArrayList("male","female"));
        positionCBox.setItems(FXCollections.observableArrayList("manager","cashier"));
    }
    class VideoTacker extends Thread {
        @Override
        public void run() {
            while (!isCapture) { // For each 30 millisecond take picture and set it in image view
                try {
                    if(webcam.getImage() != null) {
                        employeeImage.setImage(SwingFXUtils.toFXImage(webcam.getImage(), null));
                        sleep(30);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ManagerEmployeeAddController.class.getName()).log(Level.SEVERE, null, ex);
                    isCapture = false;
                }
            }
            webcam.close();
        }
    }

}

