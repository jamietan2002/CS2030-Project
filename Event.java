package cs2030.simulator;

import java.util.Optional;
import cs2030.util.*;

public abstract class Event {
    private final Customer customer;
    private final double time;

    public Event(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }
    public boolean isDummy() {
        return false;
    }

    public boolean isServe() {
        return false;
    }

    public boolean isDone() {
        return false;
    }

    abstract public Pair<Optional<Event>, Shop> execute(Shop shop);
    public Server getServer() {
        return new Server(0);
    }
    
}
