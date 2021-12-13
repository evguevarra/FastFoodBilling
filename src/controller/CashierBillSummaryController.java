package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void setOrderText(String text,String sTotal, String total,int cash){

        double change = cash - Double.parseDouble(total);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        receiptText.setStyle("-fx-text-alignment: center; ");
        receiptText.appendText("\n\t\t\t\t\t\tWelcome to FastFood\n" +
                "\t===============================================\n"+
                "\t\tCashier:\n"+
                "\t\tDate: "+formattedDateTime+"\n"+
                "\t===============================================\n\n"+
                "\t\t"+text+"\n"+
                "\t===============================================\n\n"+
                "\t\tSub-Total: "+sTotal+"\n"+
                "\t\tVat: 12%\n"+
                "\t\tTotal: "+total+"\n\n"+
                "\t\tCash Payment: " + cash + "\n"+
                "\t\tChange: "+ String.format("%.2f", change)+"\n\n"+
                "\n\t\t\t\t\t\t\tThank You!\n"+
                "\t\t\t\t\t\tPlease Come Again\n"
        );
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
