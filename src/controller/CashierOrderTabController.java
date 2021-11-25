package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierOrderTabController implements Initializable {

    @FXML
    private Label orderItemName;

    @FXML
    private Spinner<Integer> qtySpinner;

    @FXML
    private FontAwesomeIconView removeButton;

    public void setOrderItemName(String itemName){
        orderItemName.setText(itemName);
    }

    public int getQty(){
        return qtySpinner.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);

        valueFactory.setValue(1);
        qtySpinner.setValueFactory(valueFactory);
    }




//    CashierOrderTabController(String itemName){
//        orderItemName.setText(itemName);
//    }
    //public void setData

}
