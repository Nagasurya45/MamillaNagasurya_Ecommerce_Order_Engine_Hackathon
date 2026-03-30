package app.event;


public class EventProcessor implements Runnable {

    public void run() {
        while (true) {
            try {
                Event e = EventQueue.queue.take();
                System.out.println("Processing event: " + e.type + " -> " + e.data);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
