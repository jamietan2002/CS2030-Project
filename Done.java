package cs2030.simulator;

import java.util.Optional;
import cs2030.util.*;

public class Done extends Event {
    private final Server server; 

    public Done(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        double rest;
        if (this.server.isSelfCheck()) {
            rest = 0.0;
        } else {
            rest = super.getCustomer().getRestTime().get();
        }
        Server newServer = shop.getServer(this.server).freeServer();
        //free server and add rest time
        if (rest > 0.0) {
            newServer = newServer.rest(true, rest + super.getTime());

        } else {
            newServer = newServer.rest(false, super.getTime());
        }
        Shop updatedShop = shop.updateShop(newServer.getServer() - 1, newServer, shop.getStatistic());
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), updatedShop);
    }

    public Server getServer() {
        return this.server;
    }
   
    public boolean isDone() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d done serving by %s\n", super.getTime(), super.getCustomer().getID(), this.server.toString());
    }
}

