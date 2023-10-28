package hr.fer.oprpp1.custom.collections;

/**
 * Interface representing a tester.
 * @param <T> Type of checked value
 */
public interface Tester<T> {

    /**
     * Tests whether the provided object passes a test.
     * @param obj Object to be tested
     * @return Boolean representing whether the object passed a test
     */
    boolean test(T obj);

}
