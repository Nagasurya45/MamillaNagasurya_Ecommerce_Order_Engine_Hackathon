package app.service;

import app.model.User;
import java.util.*;

public class FraudService {

    private Map<Integer, List<Long>> userOrders = new HashMap<>();

    public boolean isFraud(User user) {

        long now = System.currentTimeMillis();

        userOrders.putIfAbsent(user.getId(), new ArrayList<>());
        List<Long> times = userOrders.get(user.getId());

        times.add(now);
        times.removeIf(t -> now - t > 60000);

        return times.size() > 3;
    }
}
