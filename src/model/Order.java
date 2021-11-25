package model;


import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class Order {
    private int id;
    private String name;
    private Spinner qty;
    private double price;
    private Button button;

    public Order(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.qty = new Spinner<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10));
        this.price = price;
        this.button = new Button("X");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spinner getQty() {
        return qty;
    }

    public void setQty(Spinner qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
