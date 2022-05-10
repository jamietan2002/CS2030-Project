package cs2030.simulator;

import java.util.Optional;
import cs2030.util.*;

public class Leaves extends Event {

    public Leaves(Customer customer, double time) {
        super(customer, time);
      }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        Statistic newStatistic = shop.getStatistic().addLeft();
        Shop newShop = new Shop(shop.getList(), newStatistic);
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), newShop);        
    }

    @Override
    public String toString() {
        return String.format("%.3f %d leaves\n", super.getTime(), super.getCustomer().getID());
    }
}

