package hr.fer.oprpp1.custom.collections;

/**
 * Class representing an indexed array collection.
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Constant representing arrays initial capacity.
	 */
	private static final int INITIAL_CAPACITY = 16;

	/**
	 * Constant representing the memory multiplier factor when used memory is exceeded.
	 */
	private static final int MEMORY_MULTIPLIER = 2;

	/**
	 * Number of the elements in the array.
	 */
	private int size;

	/**
	 * Internal array for storing elements.
	 */
	private Object[] elements;

	/**
	 * Constructs an array with initial capacity of 16.
	 */
	public ArrayIndexedCollection() {
		this(INITIAL_CAPACITY);
	}

	/**
	 * Constructs an array with given initial capacity.
	 * @param initialCapacity Initial capacity for an array
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(new Collection(), initialCapacity);
	}

	/**
	 * Constructs an array filled with the elements from the other collection.
	 * @param other Other collection which elements are copied into the array
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 1);
	}

	/**
	 * Constructs an array with initial capacity filled with the elements from the other collection. If the initial
	 * capacity is lower than the size of other collection, it is set to the size of the other collection.
	 * @param other Other collection which elements are copied into the array
	 * @param initialCapacity Initial capacity for an array
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null) {
			throw new NullPointerException("Collection cant be null!");
		}

		if (other.size() < 1 && initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity cant be lower than 1!");
		}

		this.elements = new Object[Math.max(initialCapacity, other.size())];
		this.addAll(other);
	}

	/**
	 * Returns the current number of elements in the array.
	 * @return Current number of elements
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Adds an element to the array.
	 * @param value Value to be added
	 */
	@Override
	public void add (Object value) {
		if (value == null) {
			throw new NullPointerException("Value cant be null!");
		}

		if (this.size == this.elements.length) {
			Object[] temp = this.elements;
			this.elements = new Object[this.elements.length * MEMORY_MULTIPLIER];

			for (int i = 0; i < this.size; i++) {
				this.elements[i] = temp[i];
			}

			this.elements[this.size] = value;
			this.size++;

			return;
		}

		this.elements[this.size] = value;
		this.size++;
	}

	/**
	 * Checks whether the provided value is present in the array.
	 * @param value Value to be looked for
	 * @return Boolean representing whether the provided value is in the array
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i] == value) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Removes value from the array.
	 * @param value Value to be removed
	 * @return Boolean representing whether the removing operation was successful
	 */
	@Override
	public boolean remove(Object value) {
		boolean isPassed = false;

		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value) && !isPassed) {
				isPassed = true;
				this.size--;
			}

			if (isPassed) {
				if (i == this.size) {
					this.elements[i] = null;
					continue;
				}

				this.elements[i] = this.elements[i + 1];
			}
		}

		return isPassed;
	}

	/**
	 * Returns an array of objects constructed from the array collection.
	 * @return Array of objects
	 */
	@Override
	public Object[] toArray() {
		Object[] temp = new Object[this.size];

		for (int i = 0; i < this.size; i++) {
			temp[i] = this.elements[i];
		}

		return temp;
	}

	/**
	 * Performs an operation specified in the processor process method on every element from the array.
	 * @param processor A processor class to be used in processing the array
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}

	/**
	 * Clears the array
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}

		this.size = 0;
	}

	/**
	 * Returns object from the array by its index.
	 * @param index Index of the object to be looked for
	 * @return Object on the specified index
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Index cant be out of range!");
		}

		return elements[index];
	}

	/**
	 * Inserts value at the specified position.
	 * @param value Value to be inserted
	 * @param position Position for the inserted value
	 */
	public void insert (Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Index cant be out of range!");
		}

		if (value == null) {
			throw new NullPointerException("Value cant be null");
		}
		
		if (position == this.size) {
			this.add(value);
			return;
		}

		Object[] temp = new Object[this.elements.length];
		for (int i = 0; i < this.elements.length; i++) {
			temp[i] = this.elements[i];
		}
		this.elements = new Object[this.elements.length];
		int count = this.size;
		this.size = 0;

		for (int i = 0; i < count + 1; i++) {
			if (i < position) {
				this.add(temp[i]);
				continue;
			}

			if (i == position) {
				this.add(value);
				continue;
			}

			this.add(temp[i - 1]);
		}
	}

	/**
	 * Returns index of the first appearance of the value in the array.
	 * @param value Value to be looked for
	 * @return Index of the desired value
	 */
	public int indexOf (Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes element from the array by its index.
	 * @param index Position of the element that is to be removed
	 */
	void remove (int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Index cant be out of range!");
		}

		for (int i = 0; i < this.size; i++) {
			if (i >= index) {
				if (i == this.size - 1) {
					this.elements[i] = null;
					continue;
				}

				this.elements[i] = this.elements[i + 1];
			}
		}

		this.size--;
	}

}
