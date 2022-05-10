package cs2030.util;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.function.Predicate;

public class PQ<T> {
    private final PriorityQueue<T> elems;

    public PQ(PriorityQueue<? extends T> pq) {
        this.elems = new PriorityQueue<T>(pq);
    }

    public PQ(Comparator<? super T> cmp) {
        this.elems = new PriorityQueue<T>(cmp);
    }

    public PQ<T> add(T elem) {
        PQ<T> newPQ = new PQ<T>(this.elems);
        newPQ.elems.add(elem);
        return newPQ;
    }

    public Pair<T, PQ<T>> poll() {
        PriorityQueue<T> newElems = new PriorityQueue<T>(this.elems);
        T toRemove = newElems.poll();
        return Pair.<T, PQ<T>>of(toRemove, new PQ<T>(newElems));
    }

    public boolean isEmpty() {
        return this.elems.isEmpty();
    }

    public int size() {
        return this.elems.size();
    }

    public PQ<T> removeIf(Predicate<? super T> predicate) {
        PQ<T> newPQ = new PQ<T>(this.elems);
        newPQ.elems.removeIf(predicate);
        return newPQ;
    }

    @Override
    public String toString() {
        return this.elems.toString();     
    }


}
