package model;


import javafx.scene.control.Button;
import javafx.scene.control.Spinner;

public class Order {
    private int id;
    private String name;
    private int qty;
    private double price;
    private double computedPrice;
   // private Button button;

    public Order(int id, String name,int qty, double price,double computedPrice) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.computedPrice = computedPrice;
//        this.button = new Button("Remove");
//        this.button.setStyle("-fx-background-color: transparent; ");
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getComputedPrice() {
        return computedPrice;
    }

    public void setComputedPrice(double computedPrice) {
        this.computedPrice = computedPrice;
    }

//    public Button getButton() {
//        return button;
//    }
//
//    public void setButton(Button button) {
//        this.button = button;
//    }
}
