package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        if (Double.compare(e1.getTime(), e2.getTime()) == 0) {
            return Integer.compare(e1.getCustomer().getID(), e2.getCustomer().getID());
        }
        return Double.compare(e1.getTime(), e2.getTime());
    }
}
