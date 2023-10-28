package hr.fer.oprpp1.custom.collections;

/**
 * Adaptor class that adapts ArrayIndexedCollection to be a stack-like collection.
 */
public class ObjectStack<T> {

    /**
     * Array used for stack data storage.
     */
    private final ArrayIndexedCollection<T> array = new ArrayIndexedCollection<>();

    /**
     * Returns whether the stack is empty.
     * @return Boolean value representing whether the stack is empty
     */
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * Returns the current number of elements in the stack.
     * @return Current number of elements
     */
    public int size() {
        return array.size();
    }

    /**
     * Adds value to the top of the stack.
     * @param value Value to be added
     */
    public void push(T value) {
        if (value == null) {
            throw new NullPointerException("Value cant be null!");
        }

        array.add(value);
    }

    /**
     * Returns and returns element from the top of the stack.
     * @return Element on the top of the stack
     */
    public T pop() {
        if (array.size() == 0) {
            throw new EmptyStackException();
        }

        T temp = array.get(array.size() - 1);
        array.remove(array.size() - 1);

        return temp;
    }

    /**
     * Returns the top stack element.
     * @return Element on the top of the stack
     */
    public T peek() {
        if (array.size() == 0) {
            throw new EmptyStackException();
        }

        return array.get(array.size() - 1);
    }

    /**
     * Clears all elements from the stack.
     */
    public void clear() {
        array.clear();
    }

}
