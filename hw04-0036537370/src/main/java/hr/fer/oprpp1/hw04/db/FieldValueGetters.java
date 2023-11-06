package hr.fer.oprpp1.hw04.db;

/**
 * Class for obtaining a requested field from a single record.
 */
public class FieldValueGetters {

    /**
     * Returns a first name from a record.
     */
    public static final IFieldValueGetter FIRST_NAME = (StudentRecord::getFirstName);

    /**
     * Returns a last name from a record.
     */
    public static final IFieldValueGetter LAST_NAME = (StudentRecord::getLastName);

    /**
     * Returns a jmbag from a record.
     */
    public static final IFieldValueGetter JMBAG = (StudentRecord::getJmbag);

}
