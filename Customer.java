package cs2030.simulator;

import cs2030.util.Lazy;

import java.util.Optional;

public class Customer {
    private final int id;
    private final double arrival;
    private final Lazy<Double> serviceTime;
    private final Lazy<Double> restTime;

    public Customer(int id, double arrival) {
        this.id = id;
        this.arrival = arrival;
        this.serviceTime = Lazy.<Double>of(() -> 1.0);
        this.restTime = Lazy.<Double>of(() -> 0.0);
    }
   
    public Customer(int id, double arrival, Lazy<Double> serviceTime) {
        this.id = id;
        this.arrival = arrival;
        this.serviceTime = serviceTime;
        this.restTime = Lazy.<Double>of(() -> 0.0);
    }

    public Customer(int id, double arrival, Lazy<Double> serviceTime, Lazy<Double> restTime) {
        this.id = id;
        this.arrival = arrival;
        this.serviceTime = serviceTime;
        this.restTime = restTime;
    }

    public double getArrival() {
        return this.arrival;
    }

    public int getID() {
        return this.id;
    }

    public Lazy<Double> getServiceTime() {
        return this.serviceTime;
    }

    public Lazy<Double> getRestTime() {
        return this.restTime;
    }
    
    @Override
    public String toString() {
        return String.format("%d",this.id);
    }
}


