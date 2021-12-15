package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.stage.Stage;
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

    Connection connection = DatabaseConnection.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private AnchorPane nameBar;

    @FXML
    private Circle circleImage;

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
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();
        System.exit(0);
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
