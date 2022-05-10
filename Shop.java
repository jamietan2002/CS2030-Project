package cs2030.simulator;

import cs2030.util.*;
import java.util.List;

public class Shop {
    private final ImList<Server> serverList;
    private final Statistic statistic;

    public Shop(List<? extends Server> list) {
        this.serverList = ImList.<Server>of(list);
        this.statistic = new Statistic(0.0, 0, 0);
    }
    
    public Shop(ImList<Server> list) {
        this.serverList = list;
        this.statistic = new Statistic(0.0, 0, 0);
    }


    public Shop(ImList<Server> list, Statistic statistic) {
        this.serverList = list;
        this.statistic = statistic;
    }

    //returns index of server that is available for serving
    public int canServe() {
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).isOccupied() == false && serverList.get(i).isResting() == false) {
                if (serverList.get(i).isSelfCheck()) {
                    if (this.getFirstSelfCheck().noWaiting()) {
                        return i;
                    } else {
                        return -1;
                    }
                } else {
                    return i;
                }
            }
        }
        return -1;
    }

    //returns index of server that is available for waiting
    public int canWait() {
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).hasWaiting()) {
                return i;
            }
        }
        return -1;
    }

    //returns index of server that is resting and has earliest nextAvailable
    public int restingServer() {
        double nextAvailable = 0.0;
        int result = -1;
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).isResting()) {
                if (serverList.get(i).getNextAvailable() < nextAvailable || nextAvailable == 0.0) {
                    result = i;
                }
            }
        }
        return result;
    }

    //returns index of SC that is available for serving
    public int SCcanServe() {
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).isOccupied() == false && serverList.get(i).isSelfCheck()) { 
                return i;
            }
        }
        return -1;
    }

    //return earliest NextAvailable of all SCs
    public double SCNext() {
        double nextAvailable = 0.0;
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).isSelfCheck()) {
                if (serverList.get(i).getNextAvailable() < nextAvailable || nextAvailable == 0.0) {
                    nextAvailable = serverList.get(i).getNextAvailable();
                }
            }
        }
        return nextAvailable;
    }

    public Shop updateShop(int index, Server newServer, Statistic statistic) {
        ImList<Server> newList = this.serverList.set(index, newServer);
        return new Shop(newList, statistic);
    }

    public ImList<Server> getList() {
        return this.serverList;
    }

    Statistic getStatistic() {
        return this.statistic;
    }

    //return Server in shop
    public Server getServer(Server server) {
        return this.serverList.get(server.getServer() - 1);
    }

    //return Server first selfcheck
    public Server getFirstSelfCheck() {
        for (int i = 0; i < this.serverList.size(); i++) {
            if (this.serverList.get(i).isSelfCheck()) {
                return this.serverList.get(i);
            }
        }
        return new Server(0);
    }

    //return index of first SelfCheck
    public int getIndexSC() {
        for (int i = 0; i < this.serverList.size(); i++) {
            if (this.serverList.get(i).isSelfCheck()) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.serverList.toString();
    }
}

