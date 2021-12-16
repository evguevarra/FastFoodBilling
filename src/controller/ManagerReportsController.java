package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Menu;
import model.Sales;
import repositories.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerReportsController implements Initializable {

    Connection connection = DatabaseConnection.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private TableColumn<Sales, String> dateColumn;

    @FXML
    private LineChart lineChart;

    @FXML
    private TableView<Sales> salesTable;

    @FXML
    private TableColumn<Sales, Double> totalColumn;

    ObservableList<Sales> observableList = FXCollections.observableArrayList();

    private void displayTable(){
        try {
            String query = "SELECT * FROM sales";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String salesDate = resultSet.getString("date");
                double salesTotal = Double.parseDouble(resultSet.getString("totalSales"));


                observableList.add(new Sales(salesDate,salesTotal));
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));


        salesTable.setItems(observableList);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayTable();

        XYChart.Series series = new XYChart.Series();
        series.setName("Sales");

        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<Double> total = new ArrayList<Double>();

        try {
            String query = "SELECT * FROM sales";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                dates.add(resultSet.getString("date"));
                total.add(Double.parseDouble(resultSet.getString("totalSales")));
            }
            preparedStatement.close();

            for(int i = 0; i < dates.size();i++){
                series.getData().add(new XYChart.Data(dates.get(i),total.get(i)));
            }

            lineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
