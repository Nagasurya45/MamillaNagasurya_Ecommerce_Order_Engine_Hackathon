package app.event;

import java.util.concurrent.*;

public class EventQueue {
    public static BlockingQueue<Event> queue = new LinkedBlockingQueue<>();
}
