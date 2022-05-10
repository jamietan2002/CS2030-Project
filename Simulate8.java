package cs2030.simulator;

import java.util.List;
import cs2030.util.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulate8 {
    private final Shop shop;
    private final PQ<Event> queue;
 
    private Simulate8(Shop shop, PQ<Event> queue) {
        this.shop = shop;
        this.queue = queue;
    }

    public Simulate8(int numServers, int numSelfCheck, List<Pair<Double, Supplier<Double>>> inputTimes, int qmax, Supplier<Double> restTimes) {

        ImList<Server> serverList = ImList.<Server>of();  
        for (int i = 1; i < numServers + 1; i++) {
            serverList = serverList.add(new Server(i, qmax));
        }
        
        for (int i = numServers + 1; i < numServers + numSelfCheck + 1; i++) {
            if (i == numServers + 1) {
                serverList = serverList.add(new SelfCheck(i, qmax));
            } else {
                // can only queue at first self checkout
                serverList = serverList.add(new SelfCheck(i, 0));
            }
        }

        this.shop = new Shop(serverList);
        Comparator<Event> cmp = new EventComparator();
        PQ<Event> queue = new PQ<Event>(cmp);

        for (int i = 1; i < inputTimes.size() + 1; i++) {
            Lazy<Double> serviceTime = Lazy.<Double>of(inputTimes.get(i - 1).second());
            Lazy<Double> restTime = Lazy.<Double>of(restTimes);
            Event event = new Arrive(new Customer(i, inputTimes.get(i - 1).first(),
                        serviceTime, restTime), inputTimes.get(i - 1).first());
            queue = queue.add(event);
                        
        }
        this.queue = queue;
    }

    public String run() {
        if (this.queue.size() == 0) {
            return (this.shop.getStatistic().toString());
        }

        String toAddString;
        //check for Serve event/ get next event
        Event currentEvent = this.queue.poll().first();
        Optional<Event> nextEvent;
        Shop updatedShop = this.shop;
        if (currentEvent.isServe()) {
            int serverIndex = currentEvent.getServer().getServer() - 1;
            Server newServer = this.shop.getList().get(serverIndex);
                
                //Handle SC servers
                if (currentEvent.getServer().isSelfCheck()) {
                    Server firstSelfCheck = shop.getFirstSelfCheck();
       
                    //if no available SC, remove currentEvent, add new Serve event
                    if (this.shop.SCcanServe() == -1) {
                        toAddString = "";
                  
                        nextEvent = Optional.<Event>of(new Serve(currentEvent.getCustomer(), 
                                    this.shop.SCNext(), newServer));
                    } else { 
                        //Serve by available SC
                        newServer = this.shop.getList().get(this.shop.SCcanServe());
                        
                        //update current event to the correct SC server
                        currentEvent = new Serve(currentEvent.getCustomer(), currentEvent.getTime(), newServer);
                        Pair<Optional<Event>, Shop> temp = currentEvent.execute(this.shop);
                        nextEvent = temp.first();
                        updatedShop = temp.second();
                        toAddString = String.format("%s", currentEvent.toString()); 
                    }
                
                } else {
                    //Handle Normal Servers
                    //Server is free and not resting
                    if (newServer.getNextAvailable() <= currentEvent.getTime()) {
                        Pair<Optional<Event>, Shop> temp = currentEvent.execute(this.shop);
                        nextEvent = temp.first();
                        updatedShop = temp.second();
                        toAddString = String.format("%s", currentEvent.toString());
                    } else {
                        //Server is currently occupied/ resting
                        toAddString = "";
                        nextEvent = Optional.<Event>of(new Serve(currentEvent.getCustomer(), 
                                    newServer.getNextAvailable(), newServer));
                    }
                }
                
        //not Serve event            
        } else {
            Pair<Optional<Event>, Shop> temp = currentEvent.execute(this.shop);
            nextEvent = temp.first();
            updatedShop = temp.second();
            toAddString = String.format("%s", currentEvent.toString());
        }
                    
        
        PQ<Event> newPQ = this.queue.poll().second();


        if (nextEvent != Optional.<Event>empty()) {
            newPQ = newPQ.add(nextEvent.orElseThrow());
        }

        if (newPQ.size() == 0) {
            return (toAddString + this.shop.getStatistic().toString());
        }


        Simulate8 newSimulate = new Simulate8(updatedShop, newPQ); 
        return toAddString + newSimulate.run();
    }
           
    @Override 
    public String toString() {
        return String.format("Queue: %s; Shop: %s", this.queue.toString(),
                this.shop.toString());
    }
}
