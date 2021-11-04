package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class LoginLoadingController implements Initializable {
    private String empPosition;
    @FXML
    private Label loadMessage;

    @FXML
    private AnchorPane anchorPane;

    public void setEmployeePosition(String position){
        empPosition = position;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
//            loadMainScreen();
//        }));
//        timeline.setCycleCount(1);
//        timeline.play();
        new LoadScreen().start();


    }
    class LoadScreen extends Thread{

        @Override
        public void run(){
            try{
                Thread.sleep(5000);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            if(empPosition.equals("cashier")) {
                                root = FXMLLoader.load(LoginLoadingController.class.getResource("/views/CashierMainUI.fxml"));
                            }else if(empPosition.equals("manager")){
                                root = FXMLLoader.load(LoginLoadingController.class.getResource("/views/ManagerMainUI.fxml"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Welcome to FastFood Billing System");
                        stage.setScene(scene);
                        stage.initModality(Modality.NONE);
                        stage.show();
                        stage.centerOnScreen();

                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

                        anchorPane.getScene().getWindow().hide();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




}
