package app.service;

public class CouponService {

    public double applyCoupon(double total, String code, int quantity) {

        if (total > 1000) total *= 0.9;
        if (quantity > 3) total *= 0.95;

        if ("SAVE10".equals(code)) total *= 0.9;
        else if ("FLAT200".equals(code)) total -= 200;

        return Math.max(total, 0);
    }
}
