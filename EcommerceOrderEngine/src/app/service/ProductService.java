package app.service;

import app.model.Product;
import java.util.*;

public class ProductService {
    private Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product p) {
        if (products.containsKey(p.getId())) {
            System.out.println("Duplicate product ID!");
            return;
        }
        products.put(p.getId(), p);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public void viewProducts() {
        for (Product p : products.values()) {
            System.out.println(p.getId() + " " + p.getName() +
                    " Stock:" + p.getStock() + " Price:" + p.getPrice());
        }
    }
}
