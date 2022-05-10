package cs2030.simulator;

import java.util.List;
import cs2030.util.*;
import java.util.Comparator;
import java.util.Optional;

public class Simulate2 {
    private final Shop shop;
    private final PQ<Event> queue;
    
    private Simulate2(Shop shop, PQ<Event> queue) {
        this.shop = shop;
        this.queue = queue; 
    }

    public Simulate2(int numServers, List<Double> arrivalTimes) {
        ImList<Server> serverList = ImList.<Server>of();  
        for (int i = 1; i < numServers + 1; i++) {
            serverList = serverList.add(new Server(i));
        }
        this.shop = new Shop(serverList);
        Comparator<Event> cmp = new EventComparator();
        PQ<Event> queue = new PQ<Event>(cmp);

        for (int i = 1; i < arrivalTimes.size() + 1; i++) {
            EventStub event = new EventStub(new Customer(i, arrivalTimes.get(i - 1)), 
                    arrivalTimes.get(i - 1));
            queue = queue.add(event);
        }
        this.queue = queue;
    }

    public String run() {
        if (this.queue.size() == 1) {
            String toAdd = String.format("%s\n", this.queue.poll().first().toString());
            return toAdd +  "-- End of Simulation --";
        } 
      
        String toAddString = String.format("%s\n", this.queue.poll().first().toString());
        PQ<Event> newPQ = this.queue.poll().second();
 
        Simulate2 newSimulate = new Simulate2(this.shop, newPQ);
        return toAddString + newSimulate.run();
    }

    @Override 
    public String toString() {
        return String.format("Queue: %s; Shop: %s", this.queue.toString(),
                this.shop.toString());
    }
}


