package model;

public class Menu {


    private int id;
    private String name;
    private String imageSrc;
    private double price;
    private String category;

    public Menu(int id,String name,String imageSrc, double price,String category){
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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
