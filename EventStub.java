package cs2030.simulator;

import java.util.Optional;
import cs2030.util.*;

public class EventStub extends Event {

    public EventStub(Customer customer, double time) {
        super(customer, time);
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), shop);
    }

    @Override
    public boolean isDummy() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%.3f", super.getTime());
    }

}
