package controller;

import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.Camera;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.scene.layout.AnchorPane;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
////import it.polito.elite.teaching.cv.utils.Utils;
//import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Optional;
import java.util.ResourceBundle;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;


public class CameraController implements Initializable {

    public String path;

    @FXML
    private AnchorPane aPane;

    @FXML
    private JFXButton captureBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton startBtn;

    private Dimension ds = new Dimension(300,250);
    private Dimension cs = WebcamResolution.VGA.getSize();
    private Webcam webcam = Webcam.getDefault();
    private WebcamPanel webcamPanel = new WebcamPanel(webcam,ds,false);





//    private ScheduledExecutorService timer;
//    private VideoCapture capture = new VideoCapture();
//    private boolean cameraActive = false;
//    private static int cameraId = 0;
//    private VideoCapture capture;
//    private Mat image;
//    private boolean clicked;


    @FXML
    void handleCaptureBtn(ActionEvent event) {
        try {
            File file = new File(String.format("capture-d%.jpg",System.currentTimeMillis()));
            ImageIO.write(webcam.getImage(),"JPG",file);
            path = file.getAbsolutePath();
            System.out.println(path);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleSaveBtn(ActionEvent event) {

    }


    @FXML
    void handleStartBtn(ActionEvent event) {
        Thread t = new Thread(){
            @Override
            public void run() {
                webcamPanel.start();
            }
        };
        t.setDaemon(true);
        t.start();

    }


//    public void startCamera(){
//        capture = new VideoCapture();
//        image = new Mat();
//        byte[] imageData;
//        Image img;
//        String name;
//
//
//
//        while(true){
//            capture.read(image);
//            final MatOfByte buf = new MatOfByte();
//            Imgcodecs.imencode(".jpg",image,buf);
//
//            imageData = buf.toArray();
//            img = new Image(new ByteArrayInputStream(imageData));
//            cameraView.setImage(img);
//
//            if(clicked){
//                TextInputDialog inputDialog = new TextInputDialog();
//                inputDialog.setTitle("Image name");
//                inputDialog.setHeaderText("Enter Image name");
//                inputDialog.setContentText("Name:");
//
//                Optional<String> result = inputDialog.showAndWait();
//                if(result.isPresent()){
//                    name = result.get();
//                }else {
//                    name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
//                }
//                Imgcodecs.imwrite("img/"+name+".jpg",image);
//                clicked = false;
//            }
//
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        startCamera();
        webcam.setViewSize(cs);
        webcamPanel.setFillArea(true);

        aPane.getChildren().add(swingNode);

    }

    private void createSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingNode.setContent(webcamPanel);
            }
        });
    }


}