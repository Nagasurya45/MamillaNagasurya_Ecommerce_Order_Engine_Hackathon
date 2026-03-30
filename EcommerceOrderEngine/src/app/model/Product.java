package app.model;

public class Product {
    private int id;
    private String name;
    private int stock;
    private double price;

    public Product(int id, String name, int stock, double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public synchronized boolean reduceStock(int qty) {
        if (stock >= qty) {
            stock -= qty;
            return true;
        }
        return false;
    }

    public synchronized void increaseStock(int qty) {
        stock += qty;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public double getPrice() { return price; }
}
