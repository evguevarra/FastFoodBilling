package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.MyListener;
import model.Menu;
import model.User;
import repositories.DatabaseConnection;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CashierMainController implements Initializable {

    @FXML
    private ImageView addOnsIconImage;

    @FXML
    private ImageView beverageIconImage;

    @FXML
    private ImageView dessertIconImage;

    @FXML
    private Label employeeName;

    @FXML
    private ImageView foodIconImage;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox orderContainer;

    @FXML
    private ScrollPane orderScroll;

    //private List<Menu> menu = new ArrayList<>();
    private MyListener myListener;
    private ObservableList<Menu> menu = FXCollections.observableArrayList();



    private ObservableList<Menu> getData(){
        ObservableList<Menu> menu = FXCollections.observableArrayList();
        Menu menuModel;


        try {
            Connection connection = DatabaseConnection.connect();
            String query = "SELECT menuName,menuPrice,menuCategory,menuImage  FROM menu ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                menuModel = new Menu();
                menuModel.setName(resultSet.getString("menuName"));
                menuModel.setPrice(resultSet.getDouble("menuPrice"));

                InputStream input = resultSet.getBinaryStream("menuImage");
                if(input != null) {
                    InputStreamReader inputReader = new InputStreamReader(input);
                    if (inputReader.ready()) {
                        File tempFile = new File(menuModel.getName() + ".jpg");

                        FileOutputStream fos = new FileOutputStream(tempFile);
                        byte[] buffer = new byte[1024];
                        while (input.read(buffer) > 0) {
                            fos.write(buffer);
                        }
                        Image image = new Image(tempFile.toURI().toURL().toString());
                        menuModel.setImageSrc(image);
                    }


                }

                menu.add(menuModel);
//               String mName = resultSet.getString("menuName");
//               double mPrice = resultSet.getDouble("menuPrice");
//               String mCategory = resultSet.getString("menuCategory");
//
//
//               InputStream image = resultSet.getBinaryStream("menuImage");
//               OutputStream outImg = new FileOutputStream(new File(mName+".jpg"));
//               byte[] buffer = new byte[1024];
//               int size =0;
//               while(image.read(buffer) > 0){
//                        outImg.write(buffer);
//               }
//               image.close();
//               outImg.close();

//               String mImage = mName+".jpg";
//

//               InputStreamReader inputReader = new InputStreamReader(image);

//               if(inputReader.ready())
//                {
//                    File tempFile = new File(mName+".jpg");
//
//                    FileOutputStream fos = new FileOutputStream(tempFile);
//                    byte[] buffer = new byte[1024];
//                    while(image.read(buffer) > 0){
//                        fos.write(buffer);
//                    }
//                    mImage =tempFile.toURI().toURL().toString();
//
//                    //right here is where you want to set your imageView with the image.
//                }
                //Image mImage = new Image(new ByteArrayInputStream(image));


                //menu.add(new Menu(mName,mPrice,mCategory,image));
            }

        } catch (SQLException | IOException e ) {
            e.printStackTrace();
        }



//        menuModel = new Menu();
//        menuModel.setName("Deluxe Burger");
//        menuModel.setPrice(120.00);
//        menuModel.setImageSrc("/img/deluxe-burger.jpg");
//        menu.add(menuModel);
//
//        menuModel = new Menu();
//        menuModel.setName("Cheese Burger");
//        menuModel.setPrice(50.00);
//        menuModel.setImageSrc("/img/cheese-burger.jpg");
//        menu.add(menuModel);
//
//        menuModel = new Menu();
//        menuModel.setName("Double Cheese Burger");
//        menuModel.setPrice(120.00);
//        menuModel.setImageSrc("/img/double-cheeseburger.jpg");
//        menu.add(menuModel);

        return menu;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int column = 0;
        int row = 1;

        orderScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");


        menu.addAll(getData());
        if(menu.size()>0){
            myListener = new MyListener() {
                @Override
                public void onClickListener(Menu menu) {
                      try {
                          FXMLLoader fxmlLoader = new FXMLLoader();
                          fxmlLoader.setLocation(getClass().getResource("/views/CashierOrderTabCard.fxml"));
                          AnchorPane aPane = fxmlLoader.load();

                          CashierOrderTabController orderController = fxmlLoader.getController();
                          orderController.setOrderItemName(menu.getName());

                          orderContainer.getChildren().add(aPane);
                          orderContainer.setSpacing(10);
                      }catch (IOException e) {
                          e.printStackTrace();
                      }
                }
            };
        }

        try {
            for (int i=0; i<menu.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/CashierMenuItemsCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CashierMenuItemsController itemController = fxmlLoader.getController();
                itemController.setData(menu.get(i),myListener);

                if(column == 3){
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane,column++,row);

//                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
//                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
//
//                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
//                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane,new Insets(15));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
