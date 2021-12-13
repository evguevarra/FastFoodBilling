package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierBillSummaryController implements Initializable  {

    public String orderText;
    public String date;
    public String cashier;
    public TableView tableView;

    @FXML
    public TextArea receiptText;

    @FXML
    void handlePrint(ActionEvent event) {

    }

    public void setOrderText(String text){
        receiptText.setStyle("-fx-text-alignment: center; ");
        receiptText.appendText("\n\t\t\t\t\t\tWelcome to FastFood\n" +
                "\t===============================================\n"+
                "\t\tCashier:\n"+
                "\t\tDate:\n"+
                "\t===============================================\n\n"+
                "\t\t"+text+"\n"+
                "\t===============================================\n");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setOrderText(orderText);
//        receiptText.setStyle("-fx-text-alignment: center; ");
//        receiptText.appendText("\n\t\t\t\t\t\tWelcome to FastFood\n" +
//                "\t===============================================\n"+
//                "\t\tCashier:\n"+
//                "\t\tDate:\n"+
//                "\t===============================================\n"+
//                "\t\t"+orderText+"\n"+
//                "\t===============================================\n");
    }


}
