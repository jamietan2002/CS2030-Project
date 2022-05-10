package cs2030.simulator;

import java.util.Optional;
import cs2030.util.*;

public class Arrive extends Event {
    public Arrive(Customer customer, double time) {
        super(customer, time);
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        int nextServer = -1;
        if (shop.restingServer() > -1 && shop.getList().get(shop.restingServer()).
                    getNextAvailable() <= super.getTime() && 
                    shop.getList().get(shop.restingServer()).noWaiting()) {
            nextServer = shop.restingServer();
                    }
                    
        if (shop.canServe() > -1) {
            if (nextServer == -1 || shop.canServe() < nextServer) {
                nextServer = shop.canServe();
            }
        }

        if (nextServer > -1) {
            //return Serve event if Server is done resting 
            Server newServer = shop.getList().get(nextServer).rest(false, super.getTime());
            Shop updatedShop = shop.updateShop(newServer.getServer() - 1, newServer, shop.getStatistic());
            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(new Serve(super.getCustomer(), 
                            super.getTime(), newServer)), updatedShop);
             //return Wait event
        } else if(shop.canWait() > -1) {
            Server newServer = shop.getList().get(shop.canWait());
            newServer = newServer.addWaiting(super.getCustomer()); 
            Shop updatedShop = shop.updateShop(newServer.getServer() - 1, newServer, shop.getStatistic());
            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(new Wait(super.getCustomer(),
                        super.getTime(), newServer)), updatedShop);
        } else {
            //return leave event
            return Pair.<Optional<Event>, Shop>of((Optional.<Event>of(new Leaves
                            (super.getCustomer(), super.getTime()))), shop);
        }
    }

    @Override
    public String toString() {
        return String.format("%.3f %d arrives\n", super.getTime(), super.getCustomer().getID());
    }
}

        
