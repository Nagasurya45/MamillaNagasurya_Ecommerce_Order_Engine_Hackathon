package app.service;

import app.model.*;
import app.event.*;
import app.util.*;

import java.util.*;

public class OrderService {

    private PaymentService paymentService = new PaymentService();
    private CouponService couponService = new CouponService();
    private FraudService fraudService = new FraudService();

    private List<Order> orders = new ArrayList<>();
    private Set<String> processedRequests = new HashSet<>();

    public void placeOrder(User user, String coupon, String requestId) {

        if (processedRequests.contains(requestId)) {
            System.out.println("Duplicate request ignored");
            return;
        }
        processedRequests.add(requestId);

        Map<Integer, CartItem> cart = user.getCart();

        if (cart.isEmpty()) {
            System.out.println("Cart empty");
            return;
        }

        double total = cart.values().stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        total = couponService.applyCoupon(total, coupon, cart.size());

        Order order = new Order(IdGenerator.generate(), cart, total);
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        if (fraudService.isFraud(user)) {
            order.setStatus(OrderStatus.FAILED);
            System.out.println("Fraud detected!");
            return;
        }

        if (!paymentService.processPayment()) {
            rollback(cart);
            order.setStatus(OrderStatus.FAILED);
            System.out.println("Payment failed!");
            return;
        }

        order.setStatus(OrderStatus.PAID);
        orders.add(order);
        cart.clear();

        EventQueue.queue.add(new Event("ORDER_CREATED", "Order " + order.getOrderId()));
        Logger.log("Order placed: " + order.getOrderId());
    }

    private void rollback(Map<Integer, CartItem> cart) {
        cart.values().forEach(i ->
                i.getProduct().increaseStock(i.getQuantity()));
    }

    public void viewOrders() {
        for (Order o : orders) {
            System.out.println("OrderId: " + o.getOrderId() +
                    " Status: " + o.getStatus());
        }
    }
}
