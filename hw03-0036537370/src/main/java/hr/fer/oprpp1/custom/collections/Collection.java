package hr.fer.oprpp1.custom.collections;

/**
 * Interface representing a generic collection.
 * @param <T> Type of the collection elements
 */
public interface Collection<T> {

	/**
	 * Returns whether the collection is empty.
	 * @return Boolean value representing whether the collection is empty
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns the current number of elements in the collection.
	 * @return The current number of elements
	 */
	int size();

	/**
	 * Adds element into the collection.
	 * @param value Value to be added
	 */
	void add(T value);

	/**
	 * Checks whether the value is present in the collection.
	 * @param value Value to be searched for
	 * @return Boolean representing whether the value is present
	 */
	boolean contains(T value);

	/**
	 * Removes value from the collection.
	 * @param value Value to be removed
	 * @return Boolean representing whether the operation was successful
	 */
	boolean remove(T value);

	/**
	 * Returns an array of objects from the collection.
	 * @return Array of objects from the collection
	 */
	T[] toArray();

	/**
	 * Performs an operation specified in the processor process method on every element from the collection.
	 * @param processor A processor class to be used in processing the collection
	 */
	default void forEach(Processor<T> processor) {
		ElementsGetter<T> eg = this.createElementsGetter();

		while (eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	}

	/**
	 * Adds all elements from the other into this collection.
	 * @param other Other collection which elements are to be added
	 */
	default void addAll(Collection<T> other) {

		/**
		 * Class representing a processor for adding elements.
		 */
		class AddProcessor implements Processor<T> {

			/**
			 * Add given value to the collection.
			 * @param value Object that is to be added
			 */
			@Override
			public void process(T value) {
				add(value);
			}
			
		}

		other.forEach(new AddProcessor());
	}

	/**
	 * Clears all elements from the collection.
	 */
	void clear();

	/**
	 * Creates instance of ElementsGetter for this collection.
	 * @return Created instance of ElementsGetter
	 */
	ElementsGetter<T> createElementsGetter();

	/**
	 * Adds all elements that pass the test from the tester from the provided collection to the current collection.
	 * @param col Collection to be checked and added
	 * @param tester Tester to be used
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> eg = col.createElementsGetter();

		while (eg.hasNextElement()) {
			T val = eg.getNextElement();

			if (tester.test(val)) {
				this.add(val);
			}
		}
	}
	
}