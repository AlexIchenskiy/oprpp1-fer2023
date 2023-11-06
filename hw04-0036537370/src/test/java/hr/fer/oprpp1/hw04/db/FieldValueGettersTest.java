package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldValueGettersTest {

    private StudentRecord studentRecord;

    @BeforeEach
    public void beforeEach() {
        studentRecord = new StudentRecord("0000000001", "Prezime", "Ime", 4);
    }

    @Test
    public void testGetLastName() {
        assertEquals("Prezime", FieldValueGetters.LAST_NAME.get(studentRecord));
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Ime", FieldValueGetters.FIRST_NAME.get(studentRecord));
    }

    @Test
    public void testGetJmbag() {
        assertEquals("0000000001", FieldValueGetters.JMBAG.get(studentRecord));
    }

}
