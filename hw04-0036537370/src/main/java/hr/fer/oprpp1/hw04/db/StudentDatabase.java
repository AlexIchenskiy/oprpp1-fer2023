package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a student database.
 */
public class StudentDatabase {

    /**
     * List of all student records in a database.
     */
    private final List<StudentRecord> studentRecords = new ArrayList<>();

    /**
     * Indexed map used for retrieving student records by jmbag in O(1).
     */
    private final Map<String, StudentRecord> studentRecordMap = new HashMap<>();

    /**
     * Constructs a student database from a list of rows.
     * @param rows One row representing a student record values (jmbag, last name, first name, final grade
     *             separated by tabs
     */
    public StudentDatabase(List<String> rows) {
        for (String row : rows) {
            StudentRecord temp = this.parseRow(row);

            StudentRecord check = this.studentRecordMap.putIfAbsent(temp.getJmbag(), temp);
            if (check != null) {
                throw new IllegalStateException("Cant have duplicate JMBAGs!");
            }
            studentRecords.add(temp);
        }
    }

    /**
     * Returns a student record by theirs jmbag in O(1).
     * @param jmbag Jmbag of a student to be retrieved
     * @return Student for that jmbag, null otherwise
     */
    public StudentRecord forJMBAG(String jmbag) {
        return this.studentRecordMap.get(jmbag);
    }

    /**
     * Returns list of all student records satisfying the provided filter.
     * @param filter Filter that will be used for record checking
     * @return List of all student records satisfying the provided filter
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> temp = new ArrayList<>();

        for (StudentRecord studentRecord : studentRecords) {
            if (filter.accepts(studentRecord)) {
                temp.add(studentRecord);
            }
        }

        return temp;
    }

    /**
     * Parse a single string row into a student record
     * @param row Tab-separated row consisting of jmbag, last name, first name and a final grade
     * @return Student record parsed from a row
     */
    private StudentRecord parseRow(String row) {
        String[] data = row.split("\t");

        String jmbag = data[0];

        String lastName = data[1];

        String firstName = data[2];

        int finalGrade = Integer.parseInt(data[data.length - 1]);

        if (finalGrade < 1 || finalGrade > 5) {
            throw new IllegalArgumentException("Final grade must be between 1 and 5!");
        }

        return new StudentRecord(jmbag, lastName, firstName, finalGrade);
    }

}
