package app.model;

import java.util.*;

public class User {
    private int id;
    private Map<Integer, CartItem> cart = new HashMap<>();

    public User(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public Map<Integer, CartItem> getCart() { return cart; }
}
