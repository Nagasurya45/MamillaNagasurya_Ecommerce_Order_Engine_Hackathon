package app.service;

import app.model.*;
import app.concurrency.LockManager;
import app.util.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class CartService {

    public void addToCart(User user, Product product, int qty) {

        ReentrantLock lock = LockManager.getLock(product.getId());
        lock.lock();

        try {
            if (!product.reduceStock(qty)) {
                System.out.println("Not enough stock!");
                return;
            }

            user.getCart().put(product.getId(), new CartItem(product, qty));
            Logger.log("User " + user.getId() + " added product " + product.getId());

        } finally {
            lock.unlock();
        }
    }

    public void removeFromCart(User user, int productId) {
        CartItem item = user.getCart().remove(productId);
        if (item != null) {
            item.getProduct().increaseStock(item.getQuantity());
        }
    }

    public void viewCart(User user) {
        user.getCart().values().forEach(i ->
                System.out.println(i.getProduct().getName() + " x " + i.getQuantity()));
    }
}
