package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Collection representing a list of key-value pairs.
 * @param <K> Type of the key
 * @param <V> Type of the value
 */
public class Dictionary<K, V> {

    /**
     * Array for the dictionary data storage.
     */
    ArrayIndexedCollection<Pair<K, V>> aic = new ArrayIndexedCollection<>();

    /**
     * Inner class representing a key-value pair.
     * @param <K> Type of the key
     * @param <V> Type of the value
     */
    private static class Pair<K, V> {

        /**
         * Pair key.
         */
        private final K key;

        /**
         * Pair value.
         */
        private final V value;

        /**
         * Constructs a pair with a non-null key and a value.
         * @param key A non-null pair key
         * @param value A pair value
         */
        public Pair(K key, V value) {
            if (key == null) {
                throw new NullPointerException("Key cant be null!");
            }

            this.key = key;
            this.value = value;
        }

        /**
         * Getter for the pair key.
         * @return Pair key
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter for the pair value.
         * @return Pair value
         */
        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /**
     * Returns whether the dictionary is empty.
     * @return Boolean value representing whether the dictionary is empty
     */
    public boolean isEmpty() {
        return this.aic.isEmpty();
    }

    /**
     * Returns the current number of elements in the dictionary.
     * @return The current number of elements
     */
    public int size() {
        return this.aic.size();
    }

    /**
     * Clears all elements from the dictionary.
     */
    public void clear() {
        this.aic.clear();
    }

    /**
     * Puts a key-value pair in the dictionary. If key exists, replaces its value by the provided one.
     * @param key Key of the pair to be added
     * @param value Value of the pair to be added
     * @return Value that was replaced, if any, null otherwise
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cant be null!");
        }

        V val = this.get(key);

        this.remove(key);
        this.aic.add(new Pair<>(key, value));

        return val;
    }

    /**
     * Returns the value by its key, if it exists, null otherwise.
     * @param key Key of the desired value
     * @return Value of the provided key
     */
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException("Key cant be null!");
        }

        ElementsGetter<Pair<K, V>> eg = aic.createElementsGetter();

        while (eg.hasNextElement()) {
            Pair<K, V> current = eg.getNextElement();
            if (current.getKey() == key) {
                return current.getValue();
            }
        }

        return null;
    }

    /**
     * Removes pair from the dictionary by its key, if it exists.
     * @param key Key of the pair to be removed
     * @return Value that was removed, null otherwise
     */
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("Key cant be null!");
        }

        ElementsGetter<Pair<K, V>> eg = aic.createElementsGetter();

        while (eg.hasNextElement()) {
            Pair<K, V> current = eg.getNextElement();
            if (current.getKey() == key) {
                aic.remove(new Pair<>(current.getKey(), current.getValue()));
                return current.getValue();
            }
        }

        return null;
    }

}
