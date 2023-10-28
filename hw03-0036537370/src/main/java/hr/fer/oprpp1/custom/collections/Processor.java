package hr.fer.oprpp1.custom.collections;

/**
 * Processor interface for processing objects.
 */
public interface Processor<T> {

	/**
	 * Main method for object processing.
	 * @param value Object that is to be processed
	 */
	void process(T value);
	
}
