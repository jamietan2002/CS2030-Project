package cs2030.simulator;

import java.util.Optional;
import cs2030.util.*;

public class Wait extends Event {
    private final Server server; 

    public Wait(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        double restTime = shop.getServer(this.server).getNextAvailable();
        if (this.server.isSelfCheck()) {
            restTime = shop.SCNext();
        }
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(new Serve(super.getCustomer(),
                            restTime, this.server)), shop);
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at %s\n", super.getTime(), super.getCustomer().getID(), this.server.toString());

    }
}

