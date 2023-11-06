package hr.fer.oprpp1.hw04.db;

/**
 * Interface representing a single comparison operator.
 */
public interface IComparisonOperator {

    /**
     * Checks whether the provided values are satisfying the comparison.
     * @param value1 First value
     * @param value2 Second value
     * @return Boolean representing whether the comparison of the provided values is satisfied
     */
    boolean satisfied(String value1, String value2);

}
