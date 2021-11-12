package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private double xOffset, yOffset;

    public void setEmployeePosition(String position){
        empPosition = position;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                        stage.initStyle(StageStyle.TRANSPARENT);



                        stage.setScene(scene);
                        //stage.setMaximized(true);


                        stage.show();
                        stage.centerOnScreen();
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
