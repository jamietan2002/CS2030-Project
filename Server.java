package cs2030.simulator;
import cs2030.util.ImList;

public class Server {
    private final int serverID;
    private final boolean occupied;
    private final ImList<Customer> waiting;
    private final int qmax;
    private final boolean resting;
    private final double restTime;

    public Server(int serverID) {
        this.serverID = serverID;
        this.occupied = false;
        this.waiting = ImList.<Customer>of();
        this.qmax = 1;
        this.resting = false;
        this.restTime = 0.0;
    }

    public Server(int serverID, int qmax) {
        this.serverID = serverID;
        this.occupied = false;
        this.waiting = ImList.<Customer>of();
        this.qmax = qmax;
        this.resting = false;
        this.restTime = 0.0;
    }



    public Server(int serverID, boolean occupied, ImList<Customer> waiting, int qmax, boolean resting, double restTime) {
        this.serverID = serverID;
        this.occupied = occupied;
        this.waiting = waiting;
        this.qmax = qmax;
        this.resting = resting;
        this.restTime = restTime;
    }


    public Server addWaiting(Customer customer) {
        ImList<Customer> newList = this.waiting.add(customer);
        return new Server(this.serverID, this.occupied, newList, this.qmax, this.resting, this.restTime);
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public int getServer() {
        return this.serverID;
    }

    ImList<Customer> getWaiting() {
        return this.waiting;
    }

    //whether there are customers waiting
    public boolean noWaiting() {
        return this.waiting.size() == 0;
    }

    //whether can Wait at server
    public boolean hasWaiting() {
        return this.waiting.size() < this.qmax; 
    }

    //remove first customer in waiting
    public Server removeWaiting() {
        ImList<Customer> newList = this.waiting.remove(0).second();
        return new Server(this.serverID, this.occupied, newList, this.qmax, this.resting, this.restTime);
    }

    //get first customer in waiting
    public Customer getNextCustomer() {
        if (this.waiting.size() == 0) {
            return new Customer(0, 0.0);
        } 
        return this.waiting.remove(0).first();
    }

    //make server occupied
    public Server addServing(double time) {
       return new Server(this.serverID, true, this.waiting, this.qmax, false, time);
    }

    public int getMax() {
        return this.qmax;
    }

    public Server freeServer() {
        return new Server(this.serverID, false, this.waiting, this.qmax, false, this.restTime);
    }
    
    public Server rest(boolean rest, double restTime) {
        return new Server(this.serverID, this.occupied, this.waiting, this.qmax, rest, restTime);
    }

    //check if Server is resting
    public boolean isResting() {
        return this.resting;
    }

    //get restTime
    public double getNextAvailable() {
        return this.restTime;
    }

    public boolean isSelfCheck() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("%d", this.serverID);
    }

}

