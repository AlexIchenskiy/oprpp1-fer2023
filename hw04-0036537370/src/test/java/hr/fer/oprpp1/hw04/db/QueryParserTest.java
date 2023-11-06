package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueryParserTest {

    private QueryParser qp;

    @Test
    public void testDirectQuery() {
        qp = new QueryParser(" jmbag       =\"0123456789\"    ");

        assertTrue(qp.isDirectQuery());
        assertEquals("0123456789", qp.getQueriedJMBAG());
        assertEquals(1, qp.getQuery().size());
    }

    @Test
    public void testIndirectQuery() {
        qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");

        assertFalse(qp.isDirectQuery());
        assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
        assertEquals(2, qp.getQuery().size());
    }

}
