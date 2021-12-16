package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.App;
import repositories.DatabaseConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerMainController implements Initializable {

    private String currentTab = "menu";
    double xOffset, yOffset;

    Connection connection = DatabaseConnection.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private AnchorPane nameBar;

    @FXML
    private Circle circleImage;


    @FXML
    private Pane employeeIndicator;

    @FXML
    private Label employeeName;


    @FXML
    private Pane menuIndicator;

    @FXML
    private Pane reportIndicator;

    @FXML
    private AnchorPane uiPane;

    @FXML
    private Label logoutBtn;



    @FXML
    void handleEmployeeBtn(MouseEvent event) {
        currentTab = "employee";
        loadIndicator();
        loadUI("ManagerEmployeeUI.fxml");
    }

    @FXML
    void handleMenuBtn(MouseEvent event) {
        currentTab = "menu";
        loadIndicator();
        loadUI("ManagerMenuUi.fxml");
    }

    @FXML
    void handleReportsBtn(MouseEvent event) {
        currentTab = "report";
        loadIndicator();
        loadUI("ManagerReportsUI.fxml");
    }

    @FXML
    void handleLogoutBtn(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Logout?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.YES){
            Parent root;
            try {
                root = FXMLLoader.load(App.class.getResource("/views/LoginUI.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        xOffset = mouseEvent.getSceneX();
                        yOffset = mouseEvent.getSceneY();
                    }
                });
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stage.setX(mouseEvent.getScreenX() - xOffset);
                        stage.setY(mouseEvent.getScreenY() - yOffset);
                    }
                });

                Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
                currentStage.close();
                //System.exit(0);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }

    }
    @FXML
    void handleSettings(MouseEvent event) {
        Parent root = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/SettingsMain.fxml"));
            root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Settings");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void loadUI(String ui){
        Node node = null;
        try{
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

    private void displayNameBar(){
        try{
            String query = "SELECT * FROM employee WHERE employeeID = '"+LoginController.currentUserID+"' ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                employeeName.setText(resultSet.getString("firstname")+" "+resultSet.getString("lastname"));
                InputStream input = resultSet.getBinaryStream("employeeImage");

                if(input != null && input.available() > 1){
                    Image img = new Image(input);
                    circleImage.setFill(new ImagePattern(img));
                }
            }

            preparedStatement.close();

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadIndicator();
        displayNameBar();
        loadUI("ManagerMenuUi.fxml");
        nameBar.setEffect(new DropShadow(5, Color.GREY));
    }
}
