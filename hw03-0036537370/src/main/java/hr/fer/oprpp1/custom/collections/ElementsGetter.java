package hr.fer.oprpp1.custom.collections;

/**
 * Class representing getter for collection elements.
 * @param <T> Type of the collection elements
 */
interface ElementsGetter<T> {

    /**
     * Returns whether there are elements left in the collection.
     * @return Boolean representing if there are any more elements
     */
    boolean hasNextElement();

    /**
     * Returns the next element in the collection.
     * @return The next element from collection
     */
    T getNextElement();

    /**
     * Applies the provided processors process method to all remaining elements in the collection.
     * @param p Processor to be used in elements processing
     */
    default void processRemaining(Processor<T> p) {
        if (p == null) {
            throw new NullPointerException("Processor cant be null!");
        }

        while (this.hasNextElement()) {
            p.process(this.getNextElement());
        }
    }

}
