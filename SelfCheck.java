package cs2030.simulator;
import cs2030.util.ImList;

public class SelfCheck extends Server {
    public SelfCheck(int serverID) {
       super(serverID);
    }
    public SelfCheck(int serverID, int qmax) {
       super(serverID, qmax);
    }

    private SelfCheck(int serverID, boolean occupied, ImList<Customer> waiting, int qmax, boolean resting, double restTime) {
        super(serverID, occupied, waiting, qmax, resting, restTime);
    }

    @Override
    public SelfCheck addWaiting(Customer customer) {
        ImList<Customer> newList = super.getWaiting().add(customer);
        return new SelfCheck(super.getServer(), super.isOccupied(), newList, super.getMax(), super.isResting(), super.getNextAvailable());
    }

    @Override
    //remove first customer in waiting
    public SelfCheck removeWaiting() {
        ImList<Customer> newList = super.getWaiting().remove(0).second();
        return new SelfCheck(super.getServer(), super.isOccupied(), newList, super.getMax(), super.isResting(), super.getNextAvailable());
    }

    @Override
    //make server occupied
    public SelfCheck addServing(double time) {
       return new SelfCheck(super.getServer(), true, super.getWaiting(), super.getMax(), super.isResting(), time);
    }

    @Override
    public SelfCheck freeServer() {
        return new SelfCheck(super.getServer(), false, super.getWaiting(), super.getMax(), super.isResting(), super.getNextAvailable());

    }

    @Override
    public SelfCheck rest(boolean rest, double restTime) {
        return new SelfCheck(super.getServer(), super.isOccupied(), super.getWaiting(), super.getMax(), false, super.getNextAvailable());
    }


    @Override
    // identify selfCheckout
    public boolean isSelfCheck() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("self-check %d", super.getServer());
    }

}

