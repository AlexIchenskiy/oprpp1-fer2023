package hr.fer.oprpp1.custom.collections;

/**
 * Interface representing a list-like collection.
 */
public interface List<T> extends Collection<T> {

    /**
     * Get element by the provided index.
     * @param index Index of the desired element
     * @return Element on the provided index
     */
    T get(int index);

    /**
     * Insert a value into collection.
     * @param value Value to be inserted
     * @param position Position of the inserted element
     */
    void insert(T value, int position);

    /**
     * Index of the first appearance of the value in the collection.
     * @param value Value to be searched for.
     * @return Position of the element.
     */
    int indexOf(T value);

    /**
     * Removes element from the collection by its index.
     * @param index Index of element to be removed
     */
    void remove(int index);

}
