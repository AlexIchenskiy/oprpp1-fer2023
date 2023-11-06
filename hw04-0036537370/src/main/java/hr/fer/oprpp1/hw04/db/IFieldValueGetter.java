package hr.fer.oprpp1.hw04.db;

/**
 * Interface for obtaining a requested field from a record.
 */
public interface IFieldValueGetter {

    /**
     * Method for retrieving a requested field from a record.
     * @param record Record from a database
     * @return Requested field
     */
    String get(StudentRecord record);

}
