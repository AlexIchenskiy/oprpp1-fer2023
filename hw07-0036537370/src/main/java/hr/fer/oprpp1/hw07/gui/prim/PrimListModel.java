package hr.fer.oprpp1.hw07.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Prime list data model implementation.
 */
public class PrimListModel implements ListModel<Integer> {

    /**
     * List of the model prime numbers.
     */
    private final List<Integer> prims;

    /**
     * List of the mode listeners.
     */
    private final List<ListDataListener> listeners;

    /**
     * Creates a prime list model.
     */
    public PrimListModel() {
        this.prims = new ArrayList<>();
        this.prims.add(1);
        this.listeners = new ArrayList<>();
    }

    /**
     * Generates a next prime number.
     */
    public void next() {
        this.prims.add(this.getNextPrim());

        this.listeners.forEach(listDataListener -> listDataListener.intervalAdded(
                new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED,
                        this.getSize() - 1, this.getSize() - 1)
        ));
    }

    /**
     * Returns the next prime number.
     * @return The next prime number
     */
    private int getNextPrim() {
        int temp = this.prims.get(this.getSize() - 1) + 1;

        while (!isPrim(temp)) temp++;

        return temp;
    }

    /**
     * Returns boolean representing whether the number is prime.
     * @param num Number to be checked
     * @return Boolean representing whether the number is prime
     */
    private boolean isPrim(int num) {
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }

        return true;
    }

    /**
     * Returns the length of the list.
     *
     * @return the length of the list
     */
    @Override
    public int getSize() {
        return this.prims.size();
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public Integer getElementAt(int index) {
        return this.prims.get(index);
    }

    /**
     * Adds a listener to the list that's notified each time a change
     * to the data model occurs.
     *
     * @param l the <code>ListDataListener</code> to be added
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        Objects.requireNonNull(l, "Listener cant be null!");
        this.listeners.add(l);
    }

    /**
     * Removes a listener from the list that's notified each time a
     * change to the data model occurs.
     *
     * @param l the <code>ListDataListener</code> to be removed
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        Objects.requireNonNull(l, "Listener cant be null!");
        this.listeners.remove(l);
    }

}
