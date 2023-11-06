package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class representing a student record from a student database.
 */
public class StudentRecord {

    /**
     * Students jmbag.
     */
    private final String jmbag;

    /**
     * Students last name.
     */
    private final String lastName;

    /**
     * Students first name.
     */
    private final String firstName;

    /**
     * Students final grade name.
     */
    private final int finalGrade;

    /**
     * Constructs a student record filled with data.
     * @param jmbag Students jmbag
     * @param lastName Students last name
     * @param firstName Students first name
     * @param finalGrade Students final grade
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    /**
     * Students jmbag getter.
     * @return Students jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Students last name getter.
     * @return Students last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Students first name getter.
     * @return Students first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Students final grade getter.
     * @return Students final grade
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentRecord)) return false;
        StudentRecord that = (StudentRecord) o;
        return Objects.equals(jmbag, that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }

}
