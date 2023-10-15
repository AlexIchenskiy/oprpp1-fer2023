package hr.fer.oprpp1.custom.collections;

/**
 * Class representing a linked list collection.
 */
public class LinkedListIndexedCollection extends Collection {

    /**
     * Local class representing a list internal node.
     */
    private static class ListNode {

        /**
         * Previous node.
         */
        ListNode previous;

        /**
         * Next node.
         */
        ListNode next;

        /**
         * Value of this node.
         */
        Object value;

        /**
         * Constructs a node with a provided value.
         * @param value Object to be used as a node value
         */
        public ListNode(Object value) {
            this.value = value;
        }

        /**
         * Converts node to string.
         * @return String representation of the node
         */
        @Override
        public String toString() {
            return this.value.toString();
        }
    }

    /**
     * Number of the elements stored in the linked list.
     */
    private int size;

    /**
     * First node in the linked list.
     */
    private ListNode first;

    /**
     * Last node in the linked list.
     */
    private ListNode last;

    /**
     * Constructs an empty linked list.
     */
    public LinkedListIndexedCollection() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Constructs a linked list with all elements from provided collection.
     * @param other Other collection which values are used to fill linked list
     */
    public LinkedListIndexedCollection(Collection other) {
        this.addAll(other);
    }

    /**
     * Returns the number of the elements in the linked list.
     * @return The size of the linked list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Adds element into the linked list.
     * @param value Object to be added
     */
    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Value cant be null!");
        }

        ListNode newNode = new ListNode(value);

        if (size == 0) {
            this.first = newNode;
        } else {
            this.last.next = newNode;
            newNode.previous = this.last;
        }

        this.last = newNode;
        this.size++;
    }

    /**
     * Checks if element is present in the linked list.
     * @param value Object to be searched for
     * @return Boolean representing if the object is in the linked list
     */
    @Override
    public boolean contains(Object value) {
        ListNode current = this.first;

        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Removes element from the linked list.
     * @param value Object to be removed
     * @return Boolean representing if the object was removed
     */
    @Override
    public boolean remove(Object value) {
        ListNode current = this.first;

        while (current != null) {
            if (current.value.equals(value)) {
                if (current.previous != null) {
                    current.previous.next = current.next;
                } else {
                    first = current.next;
                }

                if (current.next != null) {
                    current.next.previous = current.previous;
                } else {
                    last = current.previous;
                }

                this.size--;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Transforms linked list into an array of objects.
     * @return List of objects
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[this.size];

        ListNode current = this.first;
        int index = 0;

        while (current != null) {
            arr[index] = current.value;
            current = current.next;
            index++;
        }

        return arr;
    }

    /**
     * Applies process function from the processor to the every element in the linked list.
     * @param processor Processor to be used
     */
    @Override
    public void forEach(Processor processor) {
        ListNode current = first;

        while (current != null) {
            processor.process(current.value);
            current = current.next;
        }
    }

    /**
     * Clears all elements from the linked list.
     */
    @Override
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Returns object from the linked list by index.
     * @param index Index that will be looked up
     * @return Object on the specified index
     */
    public Object get(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index cant be out of range!");
        }

        ListNode current = this.first;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.value;
    }

    /**
     * Inserts value at a provided position.
     * @param value Object to be added
     * @param position Position to be used
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > this.size) {
            throw new IndexOutOfBoundsException("Index cant be out of range!");
        }

        ListNode newNode = new ListNode(value);

        if (this.size == 0) {
            this.first = newNode;
            this.last = newNode;
            this.size++;
            return;
        }

        ListNode current = this.first;

        for (int i = 0; i < position; i++) {
            current = current.next;
        }

        if (position == 0) {
            newNode.next = this.first;
            this.first.previous = newNode;
            this.first = newNode;
            this.size++;
            return;
        }

        if (position == this.size) {
            newNode.previous = this.last;
            this.last.next = newNode;
            this.last = newNode;
            this.size++;
            return;
        }

        newNode.next = current;
        newNode.previous = current.previous;
        current.previous.next = newNode;
        current.previous = newNode;

        this.size++;
    }

    /**
     * Returns the first index of the provided value from the linked list.
     * @param value Value to be searched for
     * @return Index of the provided element
     */
    public int indexOf(Object value) {
        ListNode current = first;
        int index = 0;

        while (current != null) {
            if (current.value == value) {
                return index;
            }

            current = current.next;
            index++;
        }

        return -1;
    }

    /**
     * Removes element from the linked list by its index.
     * @param index Index of the element that is to be removed
     */
    public void remove(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index cant be out of range!");
        }

        ListNode current = first;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        if (current.previous != null) {
            current.previous.next = current.next;
        } else {
            first = current.next;
        }

        if (current.next != null) {
            current.next.previous = current.previous;
        } else {
            last = current.previous;
        }

        this.size--;
    }

}
