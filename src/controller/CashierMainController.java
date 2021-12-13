package controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.MyListener;
import model.Menu;
import model.Order;
import model.User;
import repositories.DatabaseConnection;

import java.beans.Visibility;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CashierMainController implements Initializable {

    private String currentTab = "Food";

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private AnchorPane nameBar;

    @FXML
    private Label categoryLabel;

    @FXML
    private Pane addBtn;

    @FXML
    private Pane addIndicator;

    @FXML
    private ImageView addOnsIconImage;

    @FXML
    private ImageView beverageIconImage;

    @FXML
    private Pane dessertIndicator;

    @FXML
    private Pane dessertBtn;

    @FXML
    private ImageView dessertIconImage;

    @FXML
    private Pane drinkBtn;

    @FXML
    private Pane drinkIndicator;

    @FXML
    private Label employeeName;

    @FXML
    private Pane foodBtn;

    @FXML
    private ImageView foodIconImage;

    @FXML
    private Pane foodIndicator;

    @FXML
    private GridPane gridPane;

    @FXML
    private JFXButton payBtn;

    @FXML
    private Label subtotalValue;

    @FXML
    private Label totalValue;

    @FXML
    private Label logoutBtn;

    @FXML
    private FontAwesomeIconView deleteOrderBtn;

    @FXML
    private TableColumn<Order, Integer> orderMID;

    @FXML
    private TableColumn<Order, String> orderMName;

    @FXML
    private TableColumn<Order, Double> orderPrice;

    @FXML
    private TableColumn<Order, Double> orderTotal;

    @FXML
    private TableColumn<Order, Spinner> orderQty;

    @FXML
    public TableView<Order> orderTable;

    private double subTotal;
    private double totalAmount;

    private MyListener myListener;

    private ObservableList<Menu> menu = FXCollections.observableArrayList();
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    Menu menuModel;
    Order orderModel;

    @FXML
    void handleAddonBtn(MouseEvent event) {
        currentTab = "Add ons";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();

    }

    @FXML
    void handleDessertBtn(MouseEvent event) {
        currentTab = "Desserts";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();

    }

    @FXML
    void handleDrinkBtn(MouseEvent event) {
        currentTab = "Beverages";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();
    }

    @FXML
    void handleFoodBtn(MouseEvent event) {
        currentTab = "Food";
        loadIndicator();
        gridPane.getChildren().clear();
        loadMenuData();
    }

    @FXML
    void handleLogoutBtn(MouseEvent event) {
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleOrderTable(MouseEvent event) {

    }

    @FXML
    void deleteOrder(MouseEvent event) {
//        System.out.println("delete");
        Order selectedItem = orderTable.getSelectionModel().getSelectedItem();
        double toDeduct = orderTable.getSelectionModel().getSelectedItem().getComputedPrice();
        calculate(toDeduct,false);
        orderTable.getItems().remove(selectedItem);


    }

    @FXML
    void handleBillPay(ActionEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Enter Cash Tethered");
        inputDialog.setHeaderText("");
        inputDialog.setContentText("Cash Tethered:");


        Optional<String> result = inputDialog.showAndWait();

        if(result.isPresent()){
            int cashTethered = Integer.parseInt(result.get());
            System.out.println(cashTethered);
            Parent root = null;
            String orderText = "";
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CashierBillSummary.fxml"));
                root = fxmlLoader.load();
                //root = fxmlLoader.load(LoginLoadingController.class.getResource("/views/CashierBillSummary.fxml"));
                CashierBillSummaryController cController = fxmlLoader.getController();

                List<List<String>> list = new ArrayList<>();

                for (int i =0; i<orderTable.getItems().size();i++){
                    orderModel= orderTable.getItems().get(i);
                    list.add(new ArrayList<>());
                    if(i>0) {
                        list.get(i).add("\t\t"+String.valueOf(orderModel.getQty()) + " x ");
                        list.get(i).add(orderModel.getName() + "\t");
                        list.get(i).add(String.valueOf(orderModel.getComputedPrice()) + "\n");
                    }else{
                        list.get(i).add(String.valueOf(orderModel.getQty()) + " x ");
                        list.get(i).add(orderModel.getName() + "\t");
                        list.get(i).add(String.valueOf(orderModel.getComputedPrice()) + "\n");
                    }
                }
                for(int i =0; i<list.size();i++) {
                    for (int j = 0; j < list.get(i).size(); j++) {
                        orderText += list.get(i).get(j);
                    }
                }
                cController.setOrderText(orderText);
                System.out.println(orderText);


                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);
                //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//                controller.date =  formattedDateTime;
//                controller.cashier = "Edison Guevarra";

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                stage.centerOnScreen();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void loadIndicator(){
        categoryLabel.setText(currentTab);
        if(currentTab.equals("Food")){
            foodIndicator.setStyle("-fx-background-color: #FFA500; ");
            drinkIndicator.setStyle("-fx-background-color: transparent; ");
            dessertIndicator.setStyle("-fx-background-color: transparent; ");
            addIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("Beverages")){
            foodIndicator.setStyle("-fx-background-color: transparent; ");
            drinkIndicator.setStyle("-fx-background-color: #FFA500; ");
            dessertIndicator.setStyle("-fx-background-color: transparent; ");
            addIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("Desserts")){
            foodIndicator.setStyle("-fx-background-color: transparent; ");
            drinkIndicator.setStyle("-fx-background-color: transparent; ");
            dessertIndicator.setStyle("-fx-background-color: #FFA500; ");
            addIndicator.setStyle("-fx-background-color: transparent; ");
        }else if(currentTab.equals("Add ons")){
            foodIndicator.setStyle("-fx-background-color: transparent; ");
            drinkIndicator.setStyle("-fx-background-color: transparent; ");
            dessertIndicator.setStyle("-fx-background-color: transparent; ");
            addIndicator.setStyle( "-fx-background-color: #FFA500; ");
        }
    }

    private ObservableList<Menu> getData(){
        menu.clear();
        ObservableList<Menu> menu = FXCollections.observableArrayList();



        try {
            Connection connection = DatabaseConnection.connect();
            String query = "SELECT menuName,menuPrice,menuCategory,menuImage  FROM menu WHERE menuCategory = '"+currentTab+"'";
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

            }
            preparedStatement.close();

        } catch (SQLException | IOException e ) {
            e.printStackTrace();
        }

        return menu;
    }

    public void loadMenuData(){

        int column = 0;
        int row = 1;

        menu.addAll(getData());
        if(menu.size()>0){
            myListener = new MyListener() {
                @Override
                public void onClickListener(Menu menu) {

                    TextInputDialog inputDialog = new TextInputDialog();
                    inputDialog.setTitle("QTY");
                    inputDialog.setContentText("Enter Qty:");

                    Optional<String> result = inputDialog.showAndWait();

                    if(result.isPresent()){

                        int resValue = Integer.parseInt(result.get());
                        double computedPrice =  menu.getPrice() * resValue;
                        try {
                            int mID = 0;


                            Connection connection = DatabaseConnection.connect();
                            String query = "SELECT menuID  FROM menu WHERE menuName = '"+menu.getName()+"'";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            ResultSet resultSet = preparedStatement.executeQuery();

                            while(resultSet.next()){
                                mID = resultSet.getInt("menuID");
                            }
                            orderModel = new Order(mID,menu.getName(),resValue, menu.getPrice(),computedPrice);
                            orders.add(orderModel);
                            populateOrderTable();
                            calculate(computedPrice,true);

                            preparedStatement.close();

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
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
                GridPane.setMargin(anchorPane,new Insets(15));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculate(double itemTotal,boolean isAdd){
        if(isAdd == true){
            subTotal += itemTotal;
        }else {
            subTotal -= itemTotal;
        }

        totalAmount = subTotal + (subTotal * 0.12);

        subtotalValue.setText(String.valueOf(subTotal));
        totalValue.setText(String.valueOf(totalAmount));
    }

    public void populateOrderTable(){
        orderMID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        orderMName.setCellValueFactory(new PropertyValueFactory<Order, String>("name"));
        orderQty.setCellValueFactory(new PropertyValueFactory<Order, Spinner>("qty"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
        orderTotal.setCellValueFactory(new PropertyValueFactory<Order, Double>("computedPrice"));



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadIndicator();
        //orderScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        loadMenuData();

        nameBar.setEffect(new DropShadow(5, Color.GREY));
        populateOrderTable();
        orderTable.setItems(orders);

    }


}
