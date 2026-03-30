package app;

import app.model.*;
import app.service.*;
import app.event.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        new Thread(new EventProcessor()).start();

        Scanner sc = new Scanner(System.in);

        ProductService ps = new ProductService();
        CartService cs = new CartService();
        OrderService os = new OrderService();

        User user = new User(1);

        while (true) {
            System.out.println("\n1.Add Product\n2.View Products\n3.Add to Cart\n4.View Cart\n5.Place Order\n6.View Orders\n0.Exit");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.println("Enter id name stock price");
                    ps.addProduct(new Product(sc.nextInt(), sc.next(), sc.nextInt(), sc.nextDouble()));
                    break;

                case 2:
                    ps.viewProducts();
                    break;

                case 3:
                    System.out.println("Enter productId qty");
                    Product p = ps.getProduct(sc.nextInt());
                    int qty = sc.nextInt();
                    cs.addToCart(user, p, qty);
                    break;

                case 4:
                    cs.viewCart(user);
                    break;

                case 5:
                    System.out.println("Enter coupon code (or NONE)");
                    String coupon = sc.next();
                    os.placeOrder(user, coupon, UUID.randomUUID().toString());
                    break;

                case 6:
                    os.viewOrders();
                    break;

                case 0:
                    return;
            }
        }
    }
}
