package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

    /**
     * Constant representing hashtable initial capacity.
     */
    private static final int INITIAL_CAPACITY = 16;

    /**
     * Maximal possible occupancy of the hashtable.
     */
    private static final double OCCUPANCY_FACTOR = 0.75;

    /**
     * Memory multiplier factor used when occupancy is too high.
     */
    private static final int MEMORY_MULTIPLIER = 2;

    /**
     * A key-value pair representing a hashtable entry.
     * @param <K> Type of the key
     * @param <V> Type of the value
     */
    public static class TableEntry<K, V> {

        /**
         * Key of the table entry.
         */
        private K key;

        /**
         * Value of the table entry.
         */
        private V value;

        /**
         * Next table entry in a hashtable slot.
         */
        private TableEntry<K, V> next;

        public TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        /**
         * A key getter.
         * @return Pair key
         */
        public K getKey() {
            return key;
        }

        /**
         * A value getter.
         * @return Pair value
         */
        public V getValue() {
            return value;
        }

        /**
         * Value setter.
         * @param value Value to be set
         */
        public void setValue(V value) {
            this.value = value;
        }

    }

    /**
     * List of all table entries.
     */
    private TableEntry<K, V>[] table;

    /**
     * Size of the hashtable list.
     */
    private int size;

    /**
     * Number of applied modifications on the table.
     */
    private int modificationCount = 0;

    /**
     * Constructs a hashtable with initial capacity.
     */
    public SimpleHashtable() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a hashtable with a provided capacity.
     * @param capacity Capacity of the constructed hashtable
     */
    public SimpleHashtable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity cant be below 1!");
        }

        this.size = 0;
        this.table = (TableEntry<K, V>[]) new TableEntry[generateCapacity(capacity)];
    }

    /**
     * Puts a key-value pair in the hashtable.
     * @param key Key of the pair
     * @param value Value of the pair
     * @return Value of the replaced pair, if any, null otherwise
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (!this.containsKey(key)) {
            this.extendCapacityOnPreoccupied();
        }
        int pos = this.generateSlot(key);

        if (this.table[pos] == null) {
            this.table[pos] = new TableEntry<>(key, value);
            this.size++;
            return null;
        }

        TableEntry<K, V> current = this.table[pos];

        while (current != null) {
            if (current.getKey().equals(key)) {
                V val = current.value;
                current.value = value;
                return val;
            }

            if (current.next == null) {
                current.next = new TableEntry<>(key, value);
                this.size++;
                this.modificationCount++;
                return null;
            }

            current = current.next;
        }

        return null;
    }

    /**
     * Get value of the pair from the hashtable by the key.
     * @param key Key of the desired pair
     * @return Value in the pair defined by the provided key
     */
    public V get(Object key) {
        if (key == null) {
            return null;
        }

        TableEntry<K, V> current = this.table[generateSlot(key)];

        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }

            current = current.next;
        }

        return null;
    }

    /**
     * Returns size of the hashtable.
     * @return Size of the hashtable
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks whether hashtable contains a key.
     * @param key Key to be checked
     * @return Boolean representing whether the key is present in the hashtable
     */
    public boolean containsKey(Object key) {
        return this.get(key) != null;
    }

    /**
     * Checks whether hashtable contains a value.
     * @param value Value to be checked
     * @return Boolean representing whether the value is present in the hashtable
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> tableEntry : this.table) {
            if (tableEntry == null) {
                continue;
            }

            TableEntry<K, V> current = tableEntry;

            while (current != null) {
                if (current.getValue() == value) {
                    return true;
                }

                current = current.next;
            }
        }

        return false;
    }

    /**
     * Remove a pair from the hashtable by key.
     * @param key Key of the pair to be removed
     * @return Value of the removed pair, if any, null otherwise
     */
    public V remove(Object key) {
        if (key == null || !this.containsKey(key)) {
            return null;
        }

        int pos = this.generateSlot(key);

        TableEntry<K, V> current = this.table[pos];

        if (current.getKey().equals(key)) {
            V val = current.getValue();
            this.table[pos] = this.table[pos].next;
            this.size -= 1;
            this.modificationCount++;

            return val;
        }

        while (current != null) {
            if (current.next == null) {
                break;
            }

            if (current.next.getKey().equals(key)) {
                if (current.next.next == null) {
                    current.next = null;
                    this.size -= 1;
                    this.modificationCount++;
                    return null;
                }

                V val = current.next.getValue();
                current.next = current.next.next;
                this.size -= 1;
                this.modificationCount++;
                return val;
            }

            current = current.next;
        }

        return null;
    }

    /**
     * Checks whether the hashtable is empty.
     * @return Boolean representing whether the hashtable is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns a textual representation of the hashtable.
     * @return String representation of the hashtable
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int counter = 0;

        for (int i = 0; i < this.table.length; i++) {
            TableEntry<K, V> current = this.table[i];

            while (current != null) {
                sb.append(current.getKey());
                sb.append("=");
                sb.append(current.getValue());
                counter++;
                if (counter < this.size()) {
                    sb.append(", ");
                }
                current = current.next;
            }
        }

        sb.append("]");

        return sb.toString();
    }

    /**
     * Converts a hashtable to the array.
     * @return Array representation of a hashtable
     */
    public TableEntry<K,V>[] toArray() {
        TableEntry<K, V>[] arr = (TableEntry<K, V>[]) new TableEntry[this.size];
        int index = 0;

        for (TableEntry<K, V> tableEntry : this.table) {
            if (tableEntry != null) {
                TableEntry<K, V> current = tableEntry;

                while (current != null) {
                    arr[index] = current;
                    index++;

                    current = current.next;
                }
            }
        }

        return arr;
    }

    /**
     * Clears the hashtable.
     */
    public void clear() {
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != null) {
                this.table[i] = null;
            }
        }
        this.size = 0;
    }

    /**
     * Method to create an iterator on a hashtable.
     * @return Created iterator
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Iterator implementation for the hashtable.
     */
    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        /**
         * Current iterator element.
         */
        private TableEntry<K, V> currentEntry;

        /**
         * Next iterator element.
         */
        private TableEntry<K, V> nextEntry;

        /**
         * Current iterator hashtable slot.
         */
        private int currentSlot;

        /**
         * Modification count at the moment of the iterator creation.
         */
        private int savedModificationCount = SimpleHashtable.this.modificationCount;

        /**
         * Constructs an iterator for the current hashtable.
         */
        public IteratorImpl() {
            this.currentEntry = null;
            this.nextEntry = null;
            this.currentSlot = 0;

            this.setNextEntry();
        }

        /**
         * Checks whether the iterator has a next element.
         * @return Boolean representing whether the iterator has a next element
         */
        public boolean hasNext() {
            if (this.savedModificationCount != SimpleHashtable.this.modificationCount) {
                throw new ConcurrentModificationException();
            }

            return this.nextEntry != null;
        }

        /**
         * Returns the next element of the iterator.
         * @return The next element of the iterator
         */
        public SimpleHashtable.TableEntry<K, V> next() {
            if (this.savedModificationCount != SimpleHashtable.this.modificationCount) {
                throw new ConcurrentModificationException();
            }

            if (!this.hasNext()) {
                throw new NoSuchElementException("No more elements!");
            }

            this.currentEntry = this.nextEntry;
            this.setNextEntry();

            return this.currentEntry;
        }

        /**
         * Removes current element from the hashtable.
         */
        public void remove() {
            if (this.savedModificationCount != SimpleHashtable.this.modificationCount) {
                throw new ConcurrentModificationException();
            }

            if (this.currentEntry == null) {
                throw new IllegalStateException("Cant remove before next() call or an already deleted value!");
            }

            SimpleHashtable.this.remove(this.currentEntry.key);
            this.savedModificationCount = SimpleHashtable.this.modificationCount;

            this.currentEntry = null;
        }

        /**
         * Sets the next table entry for the iterator.
         */
        private void setNextEntry() {
            if (this.nextEntry != null) {
                this.nextEntry = this.nextEntry.next;
            }

            while (this.nextEntry == null) {
                if (this.currentSlot + 1 >= SimpleHashtable.this.table.length) {
                    break;
                }

                this.currentSlot++;
                this.nextEntry = SimpleHashtable.this.table[this.currentSlot];
            }
        }
    }

    /**
     * Generate an integer slot from a key.
     * @param key Key to be used for slot calculation
     * @return Hashtable slot for the provided key
     */
    private int generateSlot(Object key) {
        int hashCode = key.hashCode();

        if (key.hashCode() < 0) {
            hashCode *= -1;
        }

        return hashCode % this.table.length;
    }

    /**
     * Generate a hashtable capacity that is a power of 2.
     * @param capacity Provided capacity.
     * @return First bigger capacity that is a power of 2
     */
    private int generateCapacity(int capacity) {
        int newCapacity = 1;

        while (newCapacity < capacity || newCapacity < 2) {
            newCapacity <<= 1;
        }

        return newCapacity;
    }

    /**
     * Checks whether the hashtable is preoccupied.
     * @return True if calculated occupancy is greater than the occupancy factor
     */
    private boolean isPreoccupied() {
        return 1.0 * this.size / this.table.length > OCCUPANCY_FACTOR;
    }

    /**
     * Extends the capacity of the hashtable by the memory multiplier.
     */
    private void extendCapacityOnPreoccupied() {
        if (!isPreoccupied()) {
            return;
        }

        TableEntry<K, V>[] temp = this.toArray();
        this.size = 0;
        this.table = (TableEntry<K, V>[]) new TableEntry[this.table.length * MEMORY_MULTIPLIER];
        this.modificationCount++;

        for (TableEntry<K, V> tableEntry : temp) {
            this.put(tableEntry.key, tableEntry.value);
        }
    }

}
