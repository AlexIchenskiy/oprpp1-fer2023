package hr.fer.oprpp1.hw04.db;

/**
 * Interface representing a filter for accepting records from a student database.
 */
public interface IFilter {

    /**
     * Method that accepts student records by some criteria.
     * @param record Record to be checked
     * @return Boolean representing whether the record was accepted
     */
    boolean accepts(StudentRecord record);

}
