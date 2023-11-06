package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryFilterTest {

    private StudentRecord record1;
    private StudentRecord record2;

    private QueryFilter filter1;
    private QueryFilter filter2;

    @BeforeEach
    public void beforeEach() {
        record1 = new StudentRecord("0123456789", "Prezime", "Ime", 5);
        record2 = new StudentRecord("9876543210", "Neprezime", "Neime", 2);

        List<ConditionalExpression> expressions1 = new ArrayList<>();
        List<ConditionalExpression> expressions2 = new ArrayList<>();

        expressions1.add(new ConditionalExpression(
                FieldValueGetters.LAST_NAME, "Pre*", ComparisonOperators.LIKE));
        filter1 = new QueryFilter(expressions1);

        expressions2.add(new ConditionalExpression(
                FieldValueGetters.JMBAG, "0123456789", ComparisonOperators.EQUALS));

        expressions2.add(new ConditionalExpression(
                FieldValueGetters.FIRST_NAME, "Ime+nesto", ComparisonOperators.LESS));

        filter2 = new QueryFilter(expressions2);
    }

    @Test
    public void testExpressionTrue() {
        assertTrue(filter1.accepts(record1));
    }

    @Test
    public void testExpressionFalse() {
        assertFalse(filter1.accepts(record2));
    }

    @Test
    public void testExpressionsTrue() {
        assertTrue(filter2.accepts(record1));
    }

    @Test
    public void testExpressionsFalse() {
        assertFalse(filter2.accepts(record2));
    }

}
