package cs2030.simulator;

class Statistic {
    private final double totalWaitingTime;
    private final int numServed;
    private final int numLeft;

    Statistic(double totalWaitingTime, int numServed, int numLeft) {
        this.totalWaitingTime = totalWaitingTime;
        this.numServed = numServed;
        this.numLeft = numLeft;
    }

    Statistic addWaitingTime(double time) {
        return new Statistic(this.totalWaitingTime + time,this.numServed,
                this.numLeft);
    }

    Statistic addServed() {
        return new Statistic(this.totalWaitingTime, this.numServed + 1,
                this.numLeft);
    }

    Statistic addLeft() {
        return new Statistic(this.totalWaitingTime, this.numServed,
                this.numLeft + 1);
    }

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", this.totalWaitingTime/ this.numServed,
                this.numServed, this.numLeft);
    }
}

