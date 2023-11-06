package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StudentDatabaseTest {

    private StudentDatabase studentDatabase;
    private int numberOfRows;

    private static class FilterTrueImpl implements IFilter {

        @Override
        public boolean accepts(StudentRecord record) {
            return true;
        }

    }

    private static class FilterFalseImpl implements IFilter {

        @Override
        public boolean accepts(StudentRecord record) {
            return false;
        }

    }

    @BeforeEach
    public void beforeEach() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./database.txt"));
        this.studentDatabase = new StudentDatabase(lines);
        this.numberOfRows = lines.size();
    }

    @Test
    public void testForJmbag() {
        assertEquals("0000000001", this.studentDatabase.forJMBAG("0000000001").getJmbag());
        assertEquals("Akšamović", this.studentDatabase.forJMBAG("0000000001").getLastName());
        assertEquals("Marin", this.studentDatabase.forJMBAG("0000000001").getFirstName());
        assertEquals(2, this.studentDatabase.forJMBAG("0000000001").getFinalGrade());
    }

    @Test
    public void testForJmbagDoubleSurname() {
        assertEquals("0000000015", this.studentDatabase.forJMBAG("0000000015").getJmbag());
        assertEquals("Glavinić Pecotić", this.studentDatabase.forJMBAG("0000000015").getLastName());
        assertEquals("Kristijan", this.studentDatabase.forJMBAG("0000000015").getFirstName());
        assertEquals(4, this.studentDatabase.forJMBAG("0000000015").getFinalGrade());
    }

    @Test
    public void testForJmbagNonexistent() {
        assertNull(this.studentDatabase.forJMBAG("-1"));
    }

    @Test
    public void testFilterNone() {
        assertEquals(0, this.studentDatabase.filter(new FilterFalseImpl()).size());
    }

    @Test
    public void testFilterAll() {
        assertEquals(this.numberOfRows, this.studentDatabase.filter(new FilterTrueImpl()).size());
    }

}
