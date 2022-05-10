package cs2030.simulator;

import java.util.Optional;
import cs2030.util.*;

public class Serve extends Event {
    private final Server server;

    public Serve(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        Server newServer = shop.getServer(this.server);
        Double serviceTime = super.getCustomer().getServiceTime().get();
        Shop updatedShop = shop;
        //Handle SC
        if (this.server.isSelfCheck()) {
            Server firstSC = shop.getFirstSelfCheck();
            //remove current customer from firstSC Queue if he was in queue
            if (firstSC.getNextCustomer().getID() == super.getCustomer().getID()) {
                firstSC = firstSC.removeWaiting();
                updatedShop = shop.updateShop(shop.getIndexSC(), firstSC, shop.getStatistic());            
            }

        } else if (newServer.getNextCustomer().getID() == super.getCustomer().getID()) {
            //remove current customer from waiting if he was in queue
            newServer = newServer.removeWaiting();
        }
        //update nextAvailable
        newServer = newServer.addServing(serviceTime + super.getTime());
        Statistic newStatistic = shop.getStatistic().addServed().addWaitingTime(super.getTime() -
                super.getCustomer().getArrival());
        updatedShop = updatedShop.updateShop(newServer.getServer() - 1, newServer, newStatistic);

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
                    new Done(super.getCustomer(), super.getTime() + serviceTime, newServer)), updatedShop);
        
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public boolean isServe() {
        return true;
    }

    @Override
    public String toString() {
         return String.format("%.3f %d serves by %s\n", super.getTime(), super.getCustomer().getID(), this.server.toString());
    }
}

