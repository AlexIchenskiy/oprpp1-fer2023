package hr.fer.oprpp1.custom.collections;

/**
 * Class representing generic collection of objects.
 */
public class Collection {

	/**
	 * A default constructor.
	 */
	protected Collection() {}

	/**
	 * Returns whether the collection is empty.
	 * @return Boolean value representing whether the collection is empty
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns the current number of elements in the collection.
	 * @return The current number of elements
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds element into the collection.
	 * @param value Value to be added
	 */
	public void add(Object value) {}

	/**
	 * Checks whether the value is present in the collection.
	 * @param value Value to be searched for
	 * @return Boolean representing whether the value is present
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes value from the collection.
	 * @param value Value to be removed
	 * @return Boolean representing whether the operation was successful
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Returns an array of objects from the collection.
	 * @return Array of objects from the collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Performs an operation specified in the processor process method on every element from the collection.
	 * @param processor A processor class to be used in processing the collection
	 */
	public void forEach(Processor processor) {}

	/**
	 * Adds all elements from the other into this collection.
	 * @param other Other collection which elements are to be added
	 */
	public void addAll(Collection other) {

		/**
		 * Class representing a processor for adding elements.
		 */
		class AddProcessor extends Processor {

			/**
			 * Add given value to the collection.
			 * @param value Object that is to be added
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
			
		}

		other.forEach(new AddProcessor());
	}

	/**
	 * Clears all elements from the collection.
	 */
	public void clear() {}
	
}
