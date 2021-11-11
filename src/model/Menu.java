package model;

import javafx.scene.image.Image;

public class Menu {


    private int id;
    private String name;
    private Image imageSrc;
    private double price;
    private String category;

    public Menu(int id,String name,Image imageSrc, double price,String category){
        this.id = id;
        this.name = name;
        this.imageSrc = imageSrc;
        this.price = price;
        this.category =category;
    }

    public Menu(int id,String name, double price,String category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.category =category;
    }
    public Menu(String name, double price,String category,Image imageSrc){
        this.name = name;
        this.price = price;
        this.category =category;
        this.imageSrc = imageSrc;
    }
    public Menu(){
        this.name = name;
        this.price = price;
        this.category =category;
        this.imageSrc = imageSrc;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Image getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(Image imageSrc) {
        this.imageSrc = imageSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
